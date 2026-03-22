package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.EmployeeRetrievalDto;
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

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeRetrievalDto>> getEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeRetrievalDto> getEmployee(@PathVariable UUID uuid) {
        return ResponseEntity.ok(employeeService.getEmployeeById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeCreationDto employeeDto) {
        employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddEmployees(@RequestBody List<EmployeeCreationDto> employeeDtos) {
        employeeService.bulkAddEmployees(employeeDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeCreationDto employeeDto, @PathVariable UUID uuid) {
        employeeService.updateEmployee(employeeDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID uuid) {
        employeeService.deleteEmployee(uuid);
        return ResponseEntity.ok().build();
    }
}
