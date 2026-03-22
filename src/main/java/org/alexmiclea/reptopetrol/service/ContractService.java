package org.alexmiclea.reptopetrol.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ContractCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ContractRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Contract;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractCreationMapper contractCreationMapper;
    private final ContractRetrievalMapper contractRetrievalMapper;

    public List<ContractRetrievalDto> getAll() {
        return contractRetrievalMapper.toContractDtos(contractRepository.findAll());
    }

    public Optional<ContractRetrievalDto> getContractById(UUID uuid) {
        if (contractRepository.existsById(uuid)) {
            return Optional.of(contractRetrievalMapper.toContractDto(contractRepository.findById(uuid).orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addContract(ContractCreationDto contractDto) {
        Contract contract = contractCreationMapper.toContract(contractDto);
        contractRepository.save(contract);
    }

    public void bulkAddContracts(List<ContractCreationDto> contractDtos) {
        List<Contract> contracts = contractCreationMapper.toContracts(contractDtos);
        contractRepository.saveAll(contracts);
    }

    @Transactional
    public void updateContract(ContractCreationDto contractDto, UUID uuid) {
        Contract currentContract = contractRepository.getReferenceById(uuid);
        currentContract.setSupplier(contractCreationMapper.toContract(contractDto).getSupplier());
        currentContract.setFuels(contractCreationMapper.toContract(contractDto).getFuels());
        currentContract.setBeginDate(contractDto.getBeginDate());
        currentContract.setEndDate(contractDto.getEndDate());
        contractRepository.save(currentContract);
    }

    public Optional<UUID> deleteContract(UUID uuid) {
        if (contractRepository.existsById(uuid)) {
            contractRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }
}