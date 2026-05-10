package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.TransportCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.TransportRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Station;
import org.alexmiclea.reptopetrol.model.management.Transport;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
import org.alexmiclea.reptopetrol.repository.management.TransportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-unit")
class TransportServiceTest {

    @Mock private TransportRepository transportRepository;
    @Mock private StationRepository stationRepository;
    @Mock private TransportCreationMapper transportCreationMapper;
    @Mock private TransportRetrievalMapper transportRetrievalMapper;

    @InjectMocks private TransportService transportService;

    @Test
    void getAll_returnsMappedList() {
        Transport transport = new Transport();
        TransportRetrievalDto dto = TransportRetrievalDto.builder().companyName("FastFreight").build();
        when(transportRepository.findAll()).thenReturn(List.of(transport));
        when(transportRetrievalMapper.toTransportDtos(List.of(transport))).thenReturn(List.of(dto));

        List<TransportRetrievalDto> result = transportService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getTransportById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Transport transport = new Transport();
        TransportRetrievalDto dto = TransportRetrievalDto.builder().id(id).companyName("FastFreight").build();
        when(transportRepository.existsById(id)).thenReturn(true);
        when(transportRepository.findById(id)).thenReturn(Optional.of(transport));
        when(transportRetrievalMapper.toTransportDto(transport)).thenReturn(dto);

        Optional<TransportRetrievalDto> result = transportService.getTransportById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getTransportById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(transportRepository.existsById(id)).thenReturn(false);

        Optional<TransportRetrievalDto> result = transportService.getTransportById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addTransport_savesTransportAndLinksToStations() {
        UUID stationId = UUID.randomUUID();
        TransportCreationDto dto = TransportCreationDto.builder()
                .contractId(UUID.randomUUID())
                .stationIds(List.of(stationId))
                .creationDate(Instant.now())
                .companyName("FastFreight")
                .build();
        Transport transport = new Transport();
        Station station = new Station();
        station.setTransports(new ArrayList<>());

        when(transportCreationMapper.toTransport(dto)).thenReturn(transport);
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));

        transportService.addTransport(dto);

        verify(transportRepository).save(transport);
        verify(stationRepository).save(station);
        assertThat(station.getTransports()).contains(transport);
    }

    @Test
    void updateStationTransportID_addsTransportToStationAndSaves() {
        UUID stationId = UUID.randomUUID();
        Transport transport = new Transport();
        Station station = new Station();
        station.setTransports(new ArrayList<>());
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));

        transportService.updateStationTransportID(stationId, transport);

        assertThat(station.getTransports()).contains(transport);
        verify(stationRepository).save(station);
    }

    @Test
    void updateTransport_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        Instant completion = Instant.now().plusSeconds(3600);
        TransportCreationDto dto = TransportCreationDto.builder()
                .contractId(UUID.randomUUID())
                .stationIds(List.of(UUID.randomUUID()))
                .creationDate(Instant.now())
                .completionDate(completion)
                .companyName("QuickHaul")
                .build();
        Transport existing = new Transport();
        when(transportRepository.getReferenceById(id)).thenReturn(existing);

        transportService.updateTransport(dto, id);

        assertThat(existing.getCompanyName()).isEqualTo("QuickHaul");
        assertThat(existing.getCompletionDate()).isEqualTo(completion);
        verify(transportRepository).save(existing);
    }

    @Test
    void deleteTransport_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(transportRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = transportService.deleteTransport(id);

        assertThat(result).contains(id);
        verify(transportRepository).deleteById(id);
    }

    @Test
    void deleteTransport_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(transportRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = transportService.deleteTransport(id);

        assertThat(result).isEmpty();
        verify(transportRepository, never()).deleteById(any());
    }
}
