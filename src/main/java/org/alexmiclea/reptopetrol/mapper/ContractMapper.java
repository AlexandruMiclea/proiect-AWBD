package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.ContractDto;
import org.alexmiclea.reptopetrol.model.Contract;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractDto toContractDto(Contract contract);
    Contract toContract(ContractDto contractDto);

    List<ContractDto> toContractDtos(List<Contract> contracts);
    List<Contract> toContracts(List<ContractDto> contractDtos);
}
