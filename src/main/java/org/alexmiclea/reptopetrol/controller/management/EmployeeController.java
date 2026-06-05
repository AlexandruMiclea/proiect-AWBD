package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.EmployeeService;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
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
    private final StationService stationService;

    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/all")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getEmployees(Model model) {
        log.debug("GET /all called");

        // add call to history service
        crudHistoryService.add("GET /all", EmployeeRetrievalDto.class.getName(), "");

        model.addAttribute("employees", employeeService.getAll());

        return "management/employees/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getEmployeeCreatePage(Model model) {
        log.debug("GET /add called");

        // add call to history service
        crudHistoryService.add("GET /add", EmployeeCreationDto.class.getName(), "");

        // creation Dto
        EmployeeCreationDto employeeCreationDto = new EmployeeCreationDto();

        model.addAttribute("stations", stationService.getAll());
        model.addAttribute("employeeCreationDto", employeeCreationDto);

        return "management/employees/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getEmployeeUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("GET /update", EmployeeCreationDto.class.getName(), uuid.toString());

        Optional<EmployeeRetrievalDto> employeeRetrievalDto = employeeService.getEmployeeById(uuid);

        if (employeeRetrievalDto.isPresent()) {

            EmployeeRetrievalDto retrieved = employeeRetrievalDto.get();

            EmployeeCreationDto employeeCreationDto = EmployeeCreationDto.builder()
                    .id(uuid)
                    .role(retrieved.getRole())
                    .stationId(retrieved.getStationId())
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

        // add call to history service
        crudHistoryService.add("POST /add", EmployeeCreationDto.class.getName(), employeeDto.toString());

        employeeService.addEmployee(employeeDto);

        return "redirect:/api/employee/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String updateEmployee(@RequestBody @Validated EmployeeCreationDto employeeDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", employeeDto, uuid);

        // add call to history service
        crudHistoryService.add("PUT /update", EmployeeCreationDto.class.getName(), employeeDto.toString());

        employeeService.updateEmployee(employeeDto, uuid);

        return "redirect:/api/employee/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String deleteEmployee(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /delete", EmployeeRetrievalDto.class.getName(), uuid.toString());

        Optional<UUID> response = employeeService.deleteEmployee(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/employee/all";
    }
}