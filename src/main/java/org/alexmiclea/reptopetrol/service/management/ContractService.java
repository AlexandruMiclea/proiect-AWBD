package org.alexmiclea.reptopetrol.service.management;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.management.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ContractCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ContractRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Contract;
import org.alexmiclea.reptopetrol.model.management.Fuel;
import org.alexmiclea.reptopetrol.repository.management.ContractRepository;
import org.alexmiclea.reptopetrol.repository.management.FuelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final FuelRepository fuelRepository;
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
        for (UUID fuelId : contractDto.getFuelIds()) {
            updateFuelContractID(fuelId, contract);
        }
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

    // Method used to link contractIds to the fuel
    // TODO add exception handling
    // TODO test that transports get added and not replaced!
    public void updateFuelContractID(UUID fuelUuid, Contract contract) {
        Fuel fuel = fuelRepository.findById(fuelUuid).get();
        List<Contract> contracts = fuel.getContracts();
        contracts.add(contract);
        fuel.setContracts(contracts);
        fuelRepository.save(fuel);
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