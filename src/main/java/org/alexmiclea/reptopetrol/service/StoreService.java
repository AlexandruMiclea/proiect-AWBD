package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.StoreDto;
import org.alexmiclea.reptopetrol.mapper.StoreMapper;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.repository.StoreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    public Store getStoreById(UUID uuid) {
        return storeRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addStore(StoreDto storeDto) {
        Store store = storeMapper.toStore(storeDto);
        storeRepository.save(store);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddStores(List<StoreDto> storeDtos) {
        List<Store> stores = storeMapper.toStores(storeDtos);
        storeRepository.saveAll(stores);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateStore(StoreDto storeDto, UUID uuid) {
        Store currentStore = storeRepository.getReferenceById(uuid);
        currentStore.setStation(storeMapper.toStore(storeDto).getStation());
        storeRepository.save(currentStore);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteStore(UUID uuid) {
        Store store = storeRepository.findById(uuid).orElseThrow();
        storeRepository.delete(store);
        return ResponseEntity.ok().build();
    }
}
