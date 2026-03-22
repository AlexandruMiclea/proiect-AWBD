package org.alexmiclea.reptopetrol.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.StoreCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.StoreRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
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
    }

    public void bulkAddStores(List<StoreCreationDto> storeDtos) {
        List<Store> stores = storeCreationMapper.toStores(storeDtos);
        storeRepository.saveAll(stores);
    }

    @Transactional
    public void updateStore(StoreCreationDto storeDto, UUID uuid) {
        Store currentStore = storeRepository.getReferenceById(uuid);
        currentStore.setStation(storeCreationMapper.toStore(storeDto).getStation());
        storeRepository.save(currentStore);
    }

    public Optional<UUID> deleteStore(UUID uuid) {
        if (storeRepository.existsById(uuid)) {
            storeRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }
}