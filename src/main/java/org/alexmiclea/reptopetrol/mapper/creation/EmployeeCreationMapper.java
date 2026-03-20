package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeCreationMapper {
    EmployeeCreationDto toEmployeeDto(Employee employee);
    Employee toEmployee(EmployeeCreationDto employeeDto);

    List<EmployeeCreationDto> toEmployeeDtos(List<Employee> employees);
    List<Employee> toEmployees(List<EmployeeCreationDto> employeeDtos);
}

