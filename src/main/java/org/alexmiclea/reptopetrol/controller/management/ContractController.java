package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ROLE_OPERATIONAL'))
    public String getContracts(Model model) {
        log.info("GET /all called");
        model.addAttribute("contracts", contractService.getAll());
        return "contracts/index";
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContractRetrievalDto> getContract(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<ContractRetrievalDto> contract = contractService.getContractById(uuid);
        log.debug("Database response for GET: {}", contract);

        return contract.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addContract(@RequestBody @Validated ContractCreationDto contractDto) {
        log.info("POST /add called with payload {}", contractDto);
        contractService.addContract(contractDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateContract(@RequestBody @Validated ContractCreationDto contractDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", contractDto, uuid);
        contractService.updateContract(contractDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteContract(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = contractService.deleteContract(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}