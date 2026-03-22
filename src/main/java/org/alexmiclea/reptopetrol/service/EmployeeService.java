package org.alexmiclea.reptopetrol.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.EmployeeCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.EmployeeRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Employee;
import org.alexmiclea.reptopetrol.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<EmployeeRetrievalDto> getEmployeeById(UUID uuid) {
        if (employeeRepository.existsById(uuid)) {
            return Optional.of(employeeRetrievalMapper.toEmployeeDto(employeeRepository.findById(uuid).orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addEmployee(EmployeeCreationDto employeeDto) {
        Employee employee = employeeCreationMapper.toEmployee(employeeDto);
        employeeRepository.save(employee);
    }

    public void bulkAddEmployees(List<EmployeeCreationDto> employeeDtos) {
        List<Employee> employees = employeeCreationMapper.toEmployees(employeeDtos);
        employeeRepository.saveAll(employees);
    }

    @Transactional
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

    public Optional<UUID> deleteEmployee(UUID uuid) {
        if (employeeRepository.existsById(uuid)) {
            employeeRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }
}