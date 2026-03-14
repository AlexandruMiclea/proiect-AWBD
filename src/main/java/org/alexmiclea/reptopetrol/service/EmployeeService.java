package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.EmployeeDto;
import org.alexmiclea.reptopetrol.mapper.EmployeeMapper;
import org.alexmiclea.reptopetrol.model.Employee;
import org.alexmiclea.reptopetrol.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(UUID uuid) {
        return employeeRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployee(employeeDto);
        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddEmployees(List<EmployeeDto> employeeDtos) {
        List<Employee> employees = employeeMapper.toEmployees(employeeDtos);
        employeeRepository.saveAll(employees);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateEmployee(EmployeeDto employeeDto, UUID uuid) {
        Employee currentEmployee = employeeRepository.getReferenceById(uuid);
        currentEmployee.setFirstName(employeeDto.getFirstName());
        currentEmployee.setLastName(employeeDto.getLastName());
        currentEmployee.setDateOfHire(employeeDto.getDateOfHire());
        currentEmployee.setIdentificationNumber(employeeDto.getIdentificationNumber());
        currentEmployee.setWage(employeeDto.getWage());
        currentEmployee.setRole(employeeDto.getRole());
        employeeRepository.save(currentEmployee);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteEmployee(UUID uuid) {
        Employee employee = employeeRepository.findById(uuid).orElseThrow();
        employeeRepository.delete(employee);
        return ResponseEntity.ok().build();
    }
}
