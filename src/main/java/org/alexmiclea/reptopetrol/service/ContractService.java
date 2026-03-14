package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.ContractDto;
import org.alexmiclea.reptopetrol.mapper.ContractMapper;
import org.alexmiclea.reptopetrol.model.Contract;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    public Contract getContractById(UUID uuid) {
        return contractRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addContract(ContractDto contractDto) {
        Contract contract = contractMapper.toContract(contractDto);
        contractRepository.save(contract);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddContracts(List<ContractDto> contractDtos) {
        List<Contract> contracts = contractMapper.toContracts(contractDtos);
        contractRepository.saveAll(contracts);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateContract(ContractDto contractDto, UUID uuid) {
        Contract currentContract = contractRepository.getReferenceById(uuid);
        currentContract.setSupplier(contractMapper.toContract(contractDto).getSupplier());
        currentContract.setFuels(contractMapper.toContract(contractDto).getFuels());
        currentContract.setBeginDate(contractDto.getBeginDate());
        currentContract.setEndDate(contractDto.getEndDate());
        contractRepository.save(currentContract);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteContract(UUID uuid) {
        Contract contract = contractRepository.findById(uuid).orElseThrow();
        contractRepository.delete(contract);
        return ResponseEntity.ok().build();
    }
}
