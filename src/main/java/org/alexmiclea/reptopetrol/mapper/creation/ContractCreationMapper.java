package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.model.Contract;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractCreationMapper {
    ContractCreationDto toContractDto(Contract contract);
    Contract toContract(ContractCreationDto contractDto);

    List<ContractCreationDto> toContractDtos(List<Contract> contracts);
    List<Contract> toContracts(List<ContractCreationDto> contractDtos);
}
