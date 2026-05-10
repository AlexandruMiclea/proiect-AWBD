package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ContractCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ContractRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Contract;
import org.alexmiclea.reptopetrol.model.management.Fuel;
import org.alexmiclea.reptopetrol.repository.management.ContractRepository;
import org.alexmiclea.reptopetrol.repository.management.FuelRepository;
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
class ContractServiceTest {

    @Mock private ContractRepository contractRepository;
    @Mock private FuelRepository fuelRepository;
    @Mock private ContractCreationMapper contractCreationMapper;
    @Mock private ContractRetrievalMapper contractRetrievalMapper;

    @InjectMocks private ContractService contractService;

    @Test
    void getAll_returnsMappedList() {
        Contract contract = new Contract();
        ContractRetrievalDto dto = ContractRetrievalDto.builder().build();
        when(contractRepository.findAll()).thenReturn(List.of(contract));
        when(contractRetrievalMapper.toContractDtos(List.of(contract))).thenReturn(List.of(dto));

        List<ContractRetrievalDto> result = contractService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getContractById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Contract contract = new Contract();
        ContractRetrievalDto dto = ContractRetrievalDto.builder().id(id).build();
        when(contractRepository.existsById(id)).thenReturn(true);
        when(contractRepository.findById(id)).thenReturn(Optional.of(contract));
        when(contractRetrievalMapper.toContractDto(contract)).thenReturn(dto);

        Optional<ContractRetrievalDto> result = contractService.getContractById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getContractById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(contractRepository.existsById(id)).thenReturn(false);

        Optional<ContractRetrievalDto> result = contractService.getContractById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addContract_savesContractAndLinksToFuels() {
        UUID fuelId = UUID.randomUUID();
        ContractCreationDto dto = ContractCreationDto.builder()
                .supplierId(UUID.randomUUID())
                .fuelIds(List.of(fuelId))
                .beginDate(Instant.now())
                .endDate(Instant.now().plusSeconds(3600))
                .build();
        Contract contract = new Contract();
        Fuel fuel = new Fuel();
        fuel.setContracts(new ArrayList<>());

        when(contractCreationMapper.toContract(dto)).thenReturn(contract);
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));

        contractService.addContract(dto);

        verify(contractRepository).save(contract);
        verify(fuelRepository).save(fuel);
        assertThat(fuel.getContracts()).contains(contract);
    }

    @Test
    void updateFuelContractID_addContractToFuelAndSaves() {
        UUID fuelId = UUID.randomUUID();
        Contract contract = new Contract();
        Fuel fuel = new Fuel();
        fuel.setContracts(new ArrayList<>());
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));

        contractService.updateFuelContractID(fuelId, contract);

        assertThat(fuel.getContracts()).contains(contract);
        verify(fuelRepository).save(fuel);
    }

    @Test
    void updateContract_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        ContractCreationDto dto = ContractCreationDto.builder()
                .supplierId(UUID.randomUUID())
                .fuelIds(List.of(UUID.randomUUID()))
                .beginDate(Instant.now())
                .endDate(Instant.now().plusSeconds(7200))
                .build();
        Contract existing = new Contract();
        Contract mapped = new Contract();
        when(contractRepository.getReferenceById(id)).thenReturn(existing);
        when(contractCreationMapper.toContract(dto)).thenReturn(mapped);

        contractService.updateContract(dto, id);

        verify(contractRepository).save(existing);
    }

    @Test
    void deleteContract_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(contractRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = contractService.deleteContract(id);

        assertThat(result).contains(id);
        verify(contractRepository).deleteById(id);
    }

    @Test
    void deleteContract_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(contractRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = contractService.deleteContract(id);

        assertThat(result).isEmpty();
        verify(contractRepository, never()).deleteById(any());
    }
}
