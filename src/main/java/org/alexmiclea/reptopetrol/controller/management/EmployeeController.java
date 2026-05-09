package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getEmployees(Model model) {
        log.debug("GET /all called");

        model.addAttribute("employees", employeeService.getAll());

        return "management/employees/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getEmployeeCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        EmployeeCreationDto employeeCreationDto = new EmployeeCreationDto();

        // TODO you need to add a list of other elements, so you can have a dropdown and select them

        model.addAttribute("employeeCreationDto", employeeCreationDto);

        return "management/employees/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getEmployeeUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("PUT /update called for UUID {}", uuid);

        Optional<EmployeeRetrievalDto> employeeRetrievalDto = employeeService.getEmployeeById(uuid);

        if (employeeRetrievalDto.isPresent()) {

            EmployeeRetrievalDto retrieved = employeeRetrievalDto.get();

            EmployeeCreationDto employeeCreationDto = EmployeeCreationDto.builder()
                    .id(uuid)
                    .role(retrieved.getRole())
                    .wage(retrieved.getWage())
                    .firstName(retrieved.getFirstName())
                    .lastName(retrieved.getLastName())
                    .dateOfHire(retrieved.getDateOfHire())
                    .identificationNumber(retrieved.getIdentificationNumber())
                    .build();

            model.addAttribute("employeeCreationDto", employeeCreationDto);
            return "management/employees/update";
        } else {
            return "management/employees/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String addEmployee(@RequestBody @Validated EmployeeCreationDto employeeDto) {
        log.debug("POST /add called with payload {}", employeeDto);

        employeeService.addEmployee(employeeDto);

        return "redirect:api/contract/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String updateEmployee(@RequestBody @Validated EmployeeCreationDto employeeDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", employeeDto, uuid);

        employeeService.updateEmployee(employeeDto, uuid);

        return "redirect:api/contract/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String deleteEmployee(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = employeeService.deleteEmployee(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:api/contract/all";
    }
}