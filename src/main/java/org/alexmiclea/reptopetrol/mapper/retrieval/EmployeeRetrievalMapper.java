package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeRetrievalMapper {
    EmployeeRetrievalDto toEmployeeDto(Employee employee);
    Employee toEmployee(EmployeeRetrievalDto employeeDto);

    List<EmployeeRetrievalDto> toEmployeeDtos(List<Employee> employees);
    List<Employee> toEmployees(List<EmployeeRetrievalDto> employeeDtos);
}

