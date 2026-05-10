package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.StoreCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.StoreRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Station;
import org.alexmiclea.reptopetrol.model.management.Store;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
import org.alexmiclea.reptopetrol.repository.management.StoreRepository;
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
class StoreServiceTest {

    @Mock private StoreRepository storeRepository;
    @Mock private StationRepository stationRepository;
    @Mock private StoreCreationMapper storeCreationMapper;
    @Mock private StoreRetrievalMapper storeRetrievalMapper;

    @InjectMocks private StoreService storeService;

    @Test
    void getAll_returnsMappedList() {
        Store store = new Store();
        StoreRetrievalDto dto = StoreRetrievalDto.builder().build();
        when(storeRepository.findAll()).thenReturn(List.of(store));
        when(storeRetrievalMapper.toStoreDtos(List.of(store))).thenReturn(List.of(dto));

        List<StoreRetrievalDto> result = storeService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getStoreById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Store store = new Store();
        StoreRetrievalDto dto = StoreRetrievalDto.builder().id(id).build();
        when(storeRepository.existsById(id)).thenReturn(true);
        when(storeRepository.findById(id)).thenReturn(Optional.of(store));
        when(storeRetrievalMapper.toStoreDto(store)).thenReturn(dto);

        Optional<StoreRetrievalDto> result = storeService.getStoreById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getStoreById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(storeRepository.existsById(id)).thenReturn(false);

        Optional<StoreRetrievalDto> result = storeService.getStoreById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addStore_savesStoreAndLinksToStation() {
        UUID stationId = UUID.randomUUID();
        StoreCreationDto dto = StoreCreationDto.builder().stationId(stationId).build();
        Store store = new Store();
        Station station = new Station();

        when(storeCreationMapper.toStore(dto)).thenReturn(store);
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));

        storeService.addStore(dto);

        verify(storeRepository).save(store);
        verify(stationRepository).save(station);
        assertThat(station.getStore()).isEqualTo(store);
    }

    @Test
    void updateStationStoreID_setsStoreOnStationAndSaves() {
        UUID stationId = UUID.randomUUID();
        Station station = new Station();
        Store store = new Store();
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));

        storeService.updateStationStoreID(stationId, store);

        assertThat(station.getStore()).isEqualTo(store);
        verify(stationRepository).save(station);
    }

    @Test
    void updateStore_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();
        StoreCreationDto dto = StoreCreationDto.builder().stationId(stationId).build();
        Store existing = new Store();
        Store mapped = new Store();
        when(storeRepository.getReferenceById(id)).thenReturn(existing);
        when(storeCreationMapper.toStore(dto)).thenReturn(mapped);

        storeService.updateStore(dto, id);

        verify(storeRepository).save(existing);
    }

    @Test
    void deleteStore_whenExistsWithStation_clearsStationAndDeletes() {
        UUID id = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();
        Station station = new Station();
        station.setId(stationId);
        station.setStore(new Store());

        when(storeRepository.existsById(id)).thenReturn(true);
        when(stationRepository.findByStoreId(id)).thenReturn(List.of(station));
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));

        Optional<UUID> result = storeService.deleteStore(id);

        assertThat(result).contains(id);
        assertThat(station.getStore()).isNull();
        verify(stationRepository).save(station);
        verify(storeRepository).deleteById(id);
    }

    @Test
    void deleteStore_whenExistsWithoutStation_deletesDirectly() {
        UUID id = UUID.randomUUID();
        when(storeRepository.existsById(id)).thenReturn(true);
        when(stationRepository.findByStoreId(id)).thenReturn(List.of());

        Optional<UUID> result = storeService.deleteStore(id);

        assertThat(result).contains(id);
        verify(stationRepository, never()).save(any());
        verify(storeRepository).deleteById(id);
    }

    @Test
    void deleteStore_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(storeRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = storeService.deleteStore(id);

        assertThat(result).isEmpty();
        verify(storeRepository, never()).deleteById(any());
    }
}
