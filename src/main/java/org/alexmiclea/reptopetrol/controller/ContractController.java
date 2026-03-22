package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/all")
    public ResponseEntity<List<ContractRetrievalDto>> getContracts() {
        return ResponseEntity.ok(contractService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContractRetrievalDto> getContract(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contractService.getContractById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addContract(@RequestBody @Validated ContractCreationDto contractDto) {
        contractService.addContract(contractDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddContracts(@RequestBody @Validated List<ContractCreationDto> contractDtos) {
        contractService.bulkAddContracts(contractDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateContract(@RequestBody @Validated ContractCreationDto contractDto, @PathVariable UUID uuid) {
        contractService.updateContract(contractDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteContract(@PathVariable UUID uuid) {
        contractService.deleteContract(uuid);
        return ResponseEntity.ok().build();
    }
}
