package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.model.Contract;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractRetrievalMapper {
    ContractRetrievalDto toContractDto(Contract contract);
    Contract toContract(ContractRetrievalDto contractDto);

    List<ContractRetrievalDto> toContractDtos(List<Contract> contracts);
    List<Contract> toContracts(List<ContractRetrievalDto> contractDtos);
}
