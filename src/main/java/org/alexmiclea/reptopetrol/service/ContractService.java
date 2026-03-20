package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ContractCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ContractRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Contract;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ContractRetrievalDto getContractById(UUID uuid) {
        return contractRetrievalMapper.toContractDto(contractRepository.findById(uuid).orElseThrow());
    }

    public void addContract(ContractCreationDto contractDto) {
        Contract contract = contractCreationMapper.toContract(contractDto);
        contractRepository.save(contract);
    }

    public void bulkAddContracts(List<ContractCreationDto> contractDtos) {
        List<Contract> contracts = contractCreationMapper.toContracts(contractDtos);
        contractRepository.saveAll(contracts);
    }

    public void updateContract(ContractCreationDto contractDto, UUID uuid) {
        Contract currentContract = contractRepository.getReferenceById(uuid);
        currentContract.setSupplier(contractCreationMapper.toContract(contractDto).getSupplier());
        currentContract.setFuels(contractCreationMapper.toContract(contractDto).getFuels());
        currentContract.setBeginDate(contractDto.getBeginDate());
        currentContract.setEndDate(contractDto.getEndDate());
        contractRepository.save(currentContract);
    }

    public void deleteContract(UUID uuid) {
        Contract contract = contractRepository.findById(uuid).orElseThrow();
        contractRepository.delete(contract);
    }
}
