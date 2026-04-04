package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    public String getEmployees(Model model) {
        log.info("GET /all called");
        model.addAttribute("employees", employeeService.getAll());
        return "employees/index";
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeRetrievalDto> getEmployee(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<EmployeeRetrievalDto> employee = employeeService.getEmployeeById(uuid);
        log.debug("Database response for GET: {}", employee);

        return employee.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(@RequestBody @Validated EmployeeCreationDto employeeDto) {
        log.info("POST /add called with payload {}", employeeDto);
        employeeService.addEmployee(employeeDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddEmployees(@RequestBody @Validated List<EmployeeCreationDto> employeeDtos) {
        log.info("POST /bulkAdd called with payload {}", employeeDtos);
        employeeService.bulkAddEmployees(employeeDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateEmployee(@RequestBody @Validated EmployeeCreationDto employeeDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", employeeDto, uuid);
        employeeService.updateEmployee(employeeDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteEmployee(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = employeeService.deleteEmployee(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}