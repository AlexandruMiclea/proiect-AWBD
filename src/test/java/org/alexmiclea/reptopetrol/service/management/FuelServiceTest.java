package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.FuelCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.FuelRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Fuel;
import org.alexmiclea.reptopetrol.model.management.FuelType;
import org.alexmiclea.reptopetrol.repository.management.FuelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-unit")
class FuelServiceTest {

    @Mock private FuelRepository fuelRepository;
    @Mock private FuelCreationMapper fuelCreationMapper;
    @Mock private FuelRetrievalMapper fuelRetrievalMapper;

    @InjectMocks private FuelService fuelService;

    @Test
    void getAll_returnsMappedList() {
        Fuel fuel = new Fuel();
        FuelRetrievalDto dto = FuelRetrievalDto.builder().name("Diesel").type(FuelType.DIESEL).build();
        when(fuelRepository.findAll()).thenReturn(List.of(fuel));
        when(fuelRetrievalMapper.toFuelDtos(List.of(fuel))).thenReturn(List.of(dto));

        List<FuelRetrievalDto> result = fuelService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getFuelById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Fuel fuel = new Fuel();
        FuelRetrievalDto dto = FuelRetrievalDto.builder().id(id).name("Diesel").type(FuelType.DIESEL).build();
        when(fuelRepository.existsById(id)).thenReturn(true);
        when(fuelRepository.findById(id)).thenReturn(Optional.of(fuel));
        when(fuelRetrievalMapper.toFuelDto(fuel)).thenReturn(dto);

        Optional<FuelRetrievalDto> result = fuelService.getFuelById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getFuelById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(fuelRepository.existsById(id)).thenReturn(false);

        Optional<FuelRetrievalDto> result = fuelService.getFuelById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addFuel_mapsAndSaves() {
        FuelCreationDto dto = FuelCreationDto.builder().name("Diesel").type(FuelType.DIESEL).build();
        Fuel fuel = new Fuel();
        when(fuelCreationMapper.toFuel(dto)).thenReturn(fuel);

        fuelService.addFuel(dto);

        verify(fuelRepository).save(fuel);
    }

    @Test
    void updateFuel_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        FuelCreationDto dto = FuelCreationDto.builder().name("Premium 98").type(FuelType.GAS).build();
        Fuel existing = new Fuel();
        when(fuelRepository.getReferenceById(id)).thenReturn(existing);

        fuelService.updateFuel(dto, id);

        assertThat(existing.getName()).isEqualTo("Premium 98");
        assertThat(existing.getType()).isEqualTo(FuelType.GAS);
        verify(fuelRepository).save(existing);
    }

    @Test
    void deleteFuel_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(fuelRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = fuelService.deleteFuel(id);

        assertThat(result).contains(id);
        verify(fuelRepository).deleteById(id);
    }

    @Test
    void deleteFuel_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(fuelRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = fuelService.deleteFuel(id);

        assertThat(result).isEmpty();
        verify(fuelRepository, never()).deleteById(any());
    }
}
