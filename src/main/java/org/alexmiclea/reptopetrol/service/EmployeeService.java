package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.EmployeeCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.EmployeeRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Employee;
import org.alexmiclea.reptopetrol.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCreationMapper employeeCreationMapper;
    private final EmployeeRetrievalMapper employeeRetrievalMapper;

    public List<EmployeeRetrievalDto> getAll() {
        return employeeRetrievalMapper.toEmployeeDtos(employeeRepository.findAll());
    }

    public EmployeeRetrievalDto getEmployeeById(UUID uuid) {
        return employeeRetrievalMapper.toEmployeeDto(employeeRepository.findById(uuid).orElseThrow());
    }

    public void addEmployee(EmployeeCreationDto employeeDto) {
        Employee employee = employeeCreationMapper.toEmployee(employeeDto);
        employeeRepository.save(employee);
    }

    public void bulkAddEmployees(List<EmployeeCreationDto> employeeDtos) {
        List<Employee> employees = employeeCreationMapper.toEmployees(employeeDtos);
        employeeRepository.saveAll(employees);
    }

    public void updateEmployee(EmployeeCreationDto employeeDto, UUID uuid) {
        Employee currentEmployee = employeeRepository.getReferenceById(uuid);
        currentEmployee.setFirstName(employeeDto.getFirstName());
        currentEmployee.setLastName(employeeDto.getLastName());
        currentEmployee.setDateOfHire(employeeDto.getDateOfHire());
        currentEmployee.setIdentificationNumber(employeeDto.getIdentificationNumber());
        currentEmployee.setWage(employeeDto.getWage());
        currentEmployee.setRole(employeeDto.getRole());
        employeeRepository.save(currentEmployee);
    }

    public void deleteEmployee(UUID uuid) {
        Employee employee = employeeRepository.findById(uuid).orElseThrow();
        employeeRepository.delete(employee);
    }
}
