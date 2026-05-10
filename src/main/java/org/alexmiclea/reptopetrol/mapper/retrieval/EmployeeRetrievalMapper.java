package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.model.management.Employee;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EmployeeRetrievalMapper {

    @Autowired
    protected StationRepository stationRepository;

    @Mapping(target = "stationId", source = "employee.station.id")
    public abstract EmployeeRetrievalDto toEmployeeDto(Employee employee);

    public abstract List<EmployeeRetrievalDto> toEmployeeDtos(List<Employee> employees);
}

