package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.EmployeeDto;
import org.alexmiclea.reptopetrol.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StationMapper.class})
public interface EmployeeMapper {
    EmployeeDto toEmployeeDto(Employee employee);
    Employee toEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> toEmployeeDtos(List<Employee> employees);
    List<Employee> toEmployees(List<EmployeeDto> employeeDtos);
}

