package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.ContractDto;
import org.alexmiclea.reptopetrol.mapper.ContractMapper;
import org.alexmiclea.reptopetrol.model.Contract;
import org.alexmiclea.reptopetrol.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getContracts() {
        return ResponseEntity.ok(contractService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Contract> getContract(@RequestParam UUID uuid) {
        return ResponseEntity.ok(contractService.getContractById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addContract(@RequestBody ContractDto contractDto) {
        contractService.addContract(contractDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddContracts(@RequestBody List<ContractDto> contractDtos) {
        contractService.bulkAddContracts(contractDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateContract(@RequestBody ContractDto contractDto) {
        contractService.updateContract(contractDto, contractDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteContract(@RequestParam UUID uuid) {
        contractService.deleteContract(uuid);
        return ResponseEntity.ok().build();
    }
}
