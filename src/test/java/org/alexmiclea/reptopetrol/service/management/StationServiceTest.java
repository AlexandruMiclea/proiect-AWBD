package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.StationCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.StationRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Station;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
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
class StationServiceTest {

    @Mock private StationRepository stationRepository;
    @Mock private StationCreationMapper stationCreationMapper;
    @Mock private StationRetrievalMapper stationRetrievalMapper;

    @InjectMocks private StationService stationService;

    @Test
    void getAll_returnsMappedList() {
        Station station = new Station();
        StationRetrievalDto dto = StationRetrievalDto.builder().name("Station A").address("123 Main St").pumpNo(4).build();
        when(stationRepository.findAll()).thenReturn(List.of(station));
        when(stationRetrievalMapper.toStationDtos(List.of(station))).thenReturn(List.of(dto));

        List<StationRetrievalDto> result = stationService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getStationById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Station station = new Station();
        StationRetrievalDto dto = StationRetrievalDto.builder().id(id).name("Station A").build();
        when(stationRepository.existsById(id)).thenReturn(true);
        when(stationRepository.findById(id)).thenReturn(Optional.of(station));
        when(stationRetrievalMapper.toStationDto(station)).thenReturn(dto);

        Optional<StationRetrievalDto> result = stationService.getStationById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getStationById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(stationRepository.existsById(id)).thenReturn(false);

        Optional<StationRetrievalDto> result = stationService.getStationById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addStation_mapsAndSaves() {
        StationCreationDto dto = StationCreationDto.builder().name("Station A").address("123 Main St").pumpNo(4).build();
        Station station = new Station();
        when(stationCreationMapper.toStation(dto)).thenReturn(station);

        stationService.addStation(dto);

        verify(stationRepository).save(station);
    }

    @Test
    void updateStation_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        StationCreationDto dto = StationCreationDto.builder().name("Station B").address("456 Oak Ave").pumpNo(6).build();
        Station existing = new Station();
        when(stationRepository.getReferenceById(id)).thenReturn(existing);

        stationService.updateStation(dto, id);

        assertThat(existing.getName()).isEqualTo("Station B");
        assertThat(existing.getAddress()).isEqualTo("456 Oak Ave");
        assertThat(existing.getPumpNo()).isEqualTo(6);
        verify(stationRepository).save(existing);
    }

    @Test
    void deleteStation_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(stationRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = stationService.deleteStation(id);

        assertThat(result).contains(id);
        verify(stationRepository).deleteById(id);
    }

    @Test
    void deleteStation_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(stationRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = stationService.deleteStation(id);

        assertThat(result).isEmpty();
        verify(stationRepository, never()).deleteById(any());
    }
}
