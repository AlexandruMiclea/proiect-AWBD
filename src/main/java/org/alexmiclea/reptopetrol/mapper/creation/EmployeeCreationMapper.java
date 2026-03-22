package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.model.Employee;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EmployeeCreationMapper {

    @Autowired
    protected StationRepository stationRepository;

    @Mapping(target = "stationId", source = "employee.station.id")
    public abstract EmployeeCreationDto toEmployeeDto(Employee employee);

    @Mapping(target = "station", expression =
            "java(stationRepository.findById(employeeDto.getStationId()).orElseThrow())")
    @Mapping(target = "id", ignore = true)
    public abstract Employee toEmployee(EmployeeCreationDto employeeDto);

    public abstract List<EmployeeCreationDto> toEmployeeDtos(List<Employee> employees);
    public abstract List<Employee> toEmployees(List<EmployeeCreationDto> employeeDtos);
}

