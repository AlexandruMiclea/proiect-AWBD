package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.composites.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.FuelSupplyCreationMapper;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.FuelSupplyRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.composites.FuelSupply;
import org.alexmiclea.reptopetrol.model.management.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.repository.management.FuelSupplyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-unit")
class FuelSupplyServiceTest {

    @Mock private FuelSupplyRepository fuelSupplyRepository;
    @Mock private FuelSupplyCreationMapper fuelSupplyCreationMapper;
    @Mock private FuelSupplyRetrievalMapper fuelSupplyRetrievalMapper;
    @Mock private FuelSupplyKeyMapper fuelSupplyKeyMapper;

    @InjectMocks private FuelSupplyService fuelSupplyService;

    @Test
    void getAll_returnsMappedList() {
        FuelSupply fuelSupply = new FuelSupply();
        FuelSupplyRetrievalDto dto = FuelSupplyRetrievalDto.builder().quantity(BigDecimal.TEN).price(BigDecimal.ONE).build();
        when(fuelSupplyRepository.findAll()).thenReturn(List.of(fuelSupply));
        when(fuelSupplyRetrievalMapper.toFuelSupplyDtos(List.of(fuelSupply))).thenReturn(List.of(dto));

        List<FuelSupplyRetrievalDto> result = fuelSupplyService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getFuelSupplyById_whenExists_returnsDto() {
        FuelSupplyKey key = new FuelSupplyKey();
        key.setStationId(UUID.randomUUID());
        key.setFuelId(UUID.randomUUID());
        FuelSupply fuelSupply = new FuelSupply();
        FuelSupplyRetrievalDto dto = FuelSupplyRetrievalDto.builder().quantity(BigDecimal.TEN).build();
        when(fuelSupplyRepository.existsById(key)).thenReturn(true);
        when(fuelSupplyRepository.findById(key)).thenReturn(Optional.of(fuelSupply));
        when(fuelSupplyRetrievalMapper.toFuelSupplyDto(fuelSupply)).thenReturn(dto);

        Optional<FuelSupplyRetrievalDto> result = fuelSupplyService.getFuelSupplyById(key);

        assertThat(result).contains(dto);
    }

    @Test
    void getFuelSupplyById_whenNotExists_returnsEmpty() {
        FuelSupplyKey key = new FuelSupplyKey();
        when(fuelSupplyRepository.existsById(key)).thenReturn(false);

        Optional<FuelSupplyRetrievalDto> result = fuelSupplyService.getFuelSupplyById(key);

        assertThat(result).isEmpty();
    }

    @Test
    void addFuelSupply_mapsAndSaves() {
        FuelSupplyKeyDto keyDto = new FuelSupplyKeyDto(UUID.randomUUID(), UUID.randomUUID());
        FuelSupplyCreationDto dto = FuelSupplyCreationDto.builder()
                .id(keyDto).quantity(BigDecimal.TEN).price(new BigDecimal("1.85")).build();
        FuelSupply fuelSupply = new FuelSupply();
        when(fuelSupplyCreationMapper.toFuelSupply(dto)).thenReturn(fuelSupply);

        fuelSupplyService.addFuelSupply(dto);

        verify(fuelSupplyRepository).save(fuelSupply);
    }

    @Test
    void updateFuelSupply_mapsKeyAndSaves() {
        UUID stationId = UUID.randomUUID();
        UUID fuelId = UUID.randomUUID();
        FuelSupplyKeyDto keyDto = new FuelSupplyKeyDto(stationId, fuelId);
        FuelSupplyCreationDto dto = FuelSupplyCreationDto.builder()
                .id(keyDto).quantity(new BigDecimal("500")).price(new BigDecimal("1.99")).build();
        FuelSupplyKey fuelSupplyKey = new FuelSupplyKey();
        fuelSupplyKey.setStationId(stationId);
        fuelSupplyKey.setFuelId(fuelId);
        FuelSupply existing = new FuelSupply();

        when(fuelSupplyKeyMapper.toFuelSupplyKey(keyDto)).thenReturn(fuelSupplyKey);
        when(fuelSupplyRepository.getReferenceById(fuelSupplyKey)).thenReturn(existing);

        fuelSupplyService.updateFuelSupply(dto, keyDto);

        assertThat(existing.getQuantity()).isEqualByComparingTo(new BigDecimal("500"));
        assertThat(existing.getPrice()).isEqualByComparingTo(new BigDecimal("1.99"));
        assertThat(existing.getPriceChange()).isNotNull();
        verify(fuelSupplyRepository).save(existing);
    }

    @Test
    void deleteFuelSupply_whenExists_deletesAndReturnsKey() {
        FuelSupplyKey key = new FuelSupplyKey();
        key.setStationId(UUID.randomUUID());
        key.setFuelId(UUID.randomUUID());
        when(fuelSupplyRepository.existsById(key)).thenReturn(true);

        Optional<FuelSupplyKey> result = fuelSupplyService.deleteFuelSupply(key);

        assertThat(result).contains(key);
        verify(fuelSupplyRepository).deleteById(key);
    }

    @Test
    void deleteFuelSupply_whenNotExists_returnsEmpty() {
        FuelSupplyKey key = new FuelSupplyKey();
        when(fuelSupplyRepository.existsById(key)).thenReturn(false);

        Optional<FuelSupplyKey> result = fuelSupplyService.deleteFuelSupply(key);

        assertThat(result).isEmpty();
        verify(fuelSupplyRepository, never()).deleteById(any());
    }
}
