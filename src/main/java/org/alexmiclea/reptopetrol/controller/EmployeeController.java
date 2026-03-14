package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.EmployeeDto;
import org.alexmiclea.reptopetrol.mapper.EmployeeMapper;
import org.alexmiclea.reptopetrol.model.Employee;
import org.alexmiclea.reptopetrol.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployee(@RequestParam UUID uuid) {
        return ResponseEntity.ok(employeeService.getEmployeeById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddEmployees(@RequestBody List<EmployeeDto> employeeDtos) {
        employeeService.bulkAddEmployees(employeeDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(employeeDto, employeeDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@RequestParam UUID uuid) {
        employeeService.deleteEmployee(uuid);
        return ResponseEntity.ok().build();
    }
}
