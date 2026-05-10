package org.alexmiclea.reptopetrol.service.management;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.management.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.StoreCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.StoreRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Station;
import org.alexmiclea.reptopetrol.model.management.Store;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
import org.alexmiclea.reptopetrol.repository.management.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StationRepository stationRepository;
    private final StoreCreationMapper storeCreationMapper;
    private final StoreRetrievalMapper storeRetrievalMapper;

    public List<StoreRetrievalDto> getAll() {
        return storeRetrievalMapper.toStoreDtos(storeRepository.findAll());
    }

    public Optional<StoreRetrievalDto> getStoreById(UUID uuid) {
        if (storeRepository.existsById(uuid)) {
            return Optional.of(storeRetrievalMapper.toStoreDto(storeRepository.findById(uuid).orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addStore(StoreCreationDto storeDto) {
        Store store = storeCreationMapper.toStore(storeDto);
        storeRepository.save(store);
        updateStationStoreID(storeDto.getStationId(), store);
    }

    @Transactional
    public void updateStore(StoreCreationDto storeDto, UUID uuid) {
        Store currentStore = storeRepository.getReferenceById(uuid);
        currentStore.setStation(storeCreationMapper.toStore(storeDto).getStation());
        storeRepository.save(currentStore);
    }

    // Method used to link storeId to the station
    // TODO add exception handling
    public void updateStationStoreID(UUID stationUuid, Store store) {
        Station station = stationRepository.findById(stationUuid).get();
        station.setStore(store);
        stationRepository.save(station);
    }

    public Optional<UUID> deleteStore(UUID uuid) {
        if (storeRepository.existsById(uuid)) {
            // check if the station is associated to any station - if so, update the store to null
            List<Station> stations = stationRepository.findByStoreId(uuid);

            if (!stations.isEmpty()) {
                for (Station station : stations) {
                    updateStationStoreID(station.getId(), null);
                }
            }

            storeRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }
}