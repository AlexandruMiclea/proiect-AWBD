package org.alexmiclea.reptopetrol.integration;

import org.alexmiclea.reptopetrol.dto.management.creation.*;
import org.alexmiclea.reptopetrol.dto.management.creation.composites.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.management.creation.composites.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.dto.management.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.model.management.EmployeeRole;
import org.alexmiclea.reptopetrol.model.management.FuelType;
import org.alexmiclea.reptopetrol.repository.management.*;
import org.alexmiclea.reptopetrol.service.management.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that one record of each entity can be persisted and retrieved via the
 * service + JPA + H2 stack. The test transaction is rolled back after the method,
 * so the three test classes share a clean H2 schema.
 */
@Transactional
class EntityCreationIntegrationTest extends BaseIntegrationTest {

    @Autowired StationService stationService;
    @Autowired FuelService fuelService;
    @Autowired SupplierService supplierService;
    @Autowired ProductService productService;
    @Autowired StoreService storeService;
    @Autowired ContractService contractService;
    @Autowired EmployeeService employeeService;
    @Autowired TransportService transportService;
    @Autowired FuelSupplyService fuelSupplyService;
    @Autowired InventoryService inventoryService;

    @Autowired StationRepository stationRepository;
    @Autowired FuelRepository fuelRepository;
    @Autowired SupplierRepository supplierRepository;
    @Autowired ProductRepository productRepository;
    @Autowired StoreRepository storeRepository;
    @Autowired ContractRepository contractRepository;
    @Autowired EmployeeRepository employeeRepository;
    @Autowired TransportRepository transportRepository;
    @Autowired FuelSupplyRepository fuelSupplyRepository;
    @Autowired InventoryRepository inventoryRepository;

    @Test
    void createOneOfEachEntityAndVerifyInDatabase() {

        // --- Station (no dependencies) ---
        stationService.addStation(StationCreationDto.builder()
                .name("Integration Station").address("Strada Principala 1").pumpNo(6).build());
        UUID stationId = stationRepository.findAll().get(0).getId();

        // --- Fuel (no dependencies) ---
        fuelService.addFuel(FuelCreationDto.builder()
                .name("Diesel 95").type(FuelType.DIESEL).build());
        UUID fuelId = fuelRepository.findAll().get(0).getId();

        // --- Supplier (no dependencies) ---
        supplierService.addSupplier(SupplierCreationDto.builder()
                .name("Petrom SA").address("Bucharest").homeCountry("Romania").build());
        UUID supplierId = supplierRepository.findAll().get(0).getId();

        // --- Product (no dependencies) ---
        productService.addProduct(ProductCreationDto.builder()
                .name("Motor Oil 5W40").type("Lubricant").build());
        UUID productId = productRepository.findAll().get(0).getId();

        // --- Store (depends on Station via mapper) ---
        storeService.addStore(StoreCreationDto.builder().stationId(stationId).build());
        UUID storeId = storeRepository.findAll().get(0).getId();

        // --- Contract (depends on Supplier + Fuel) ---
        contractService.addContract(ContractCreationDto.builder()
                .supplierId(supplierId)
                .fuelIds(List.of(fuelId))
                .beginDate(Instant.parse("2025-01-01T00:00:00Z"))
                .endDate(Instant.parse("2027-12-31T23:59:59Z"))
                .build());
        UUID contractId = contractRepository.findAll().get(0).getId();

        // --- Employee (depends on Station via mapper) ---
        employeeService.addEmployee(EmployeeCreationDto.builder()
                .firstName("Ion").lastName("Popescu")
                .identificationNumber("AB123456")
                .wage(4000).role(EmployeeRole.CASHIER)
                .dateOfHire(Instant.parse("2024-03-15T08:00:00Z"))
                .stationId(stationId)
                .build());

        // --- Transport (depends on Contract + Station via mapper) ---
        transportService.addTransport(TransportCreationDto.builder()
                .contractId(contractId)
                .stationIds(List.of(stationId))
                .creationDate(Instant.parse("2025-06-01T06:00:00Z"))
                .completionDate(Instant.parse("2027-06-30T18:00:00Z"))
                .companyName("FastFreight SRL")
                .build());

        // --- FuelSupply (depends on Station + Fuel, composite key) ---
        fuelSupplyService.addFuelSupply(FuelSupplyCreationDto.builder()
                .id(new FuelSupplyKeyDto(stationId, fuelId))
                .quantity(new BigDecimal("5000.00"))
                .price(new BigDecimal("1.85"))
                .build());

        // --- Inventory (depends on Store + Product, composite key) ---
        inventoryService.addInventory(InventoryCreationDto.builder()
                .id(new InventoryKeyDto(storeId, productId))
                .quantity(100)
                .price(29.99f)
                .build());

        // --- Verify one record of each entity exists ---
        assertThat(stationRepository.count()).isEqualTo(1);
        assertThat(fuelRepository.count()).isEqualTo(1);
        assertThat(supplierRepository.count()).isEqualTo(1);
        assertThat(productRepository.count()).isEqualTo(1);
        assertThat(storeRepository.count()).isEqualTo(1);
        assertThat(contractRepository.count()).isEqualTo(1);
        assertThat(employeeRepository.count()).isEqualTo(1);
        assertThat(transportRepository.count()).isEqualTo(1);
        assertThat(fuelSupplyRepository.count()).isEqualTo(1);
        assertThat(inventoryRepository.count()).isEqualTo(1);

        // --- Verify field values ---
        assertThat(stationRepository.findById(stationId).orElseThrow().getName()).isEqualTo("Integration Station");
        assertThat(fuelRepository.findById(fuelId).orElseThrow().getType()).isEqualTo(FuelType.DIESEL);
        assertThat(supplierRepository.findById(supplierId).orElseThrow().getHomeCountry()).isEqualTo("Romania");
        assertThat(productRepository.findById(productId).orElseThrow().getName()).isEqualTo("Motor Oil 5W40");

        // --- Verify cross-entity relationships ---
        // Store is linked back to Station
        assertThat(storeRepository.findById(storeId).orElseThrow().getStation().getId()).isEqualTo(stationId);

        // Contract is linked to the Fuel (via fuel_contract join table)
        assertThat(contractRepository.findById(contractId).orElseThrow().getFuels())
                .extracting("id").containsExactly(fuelId);

        // Employee is linked to the Station
        assertThat(employeeRepository.findAll().get(0).getStation().getId()).isEqualTo(stationId);

        // Transport is linked to the Contract
        assertThat(transportRepository.findAll().get(0).getContract().getId()).isEqualTo(contractId);

        // FuelSupply composite key points to the right station and fuel
        var savedFuelSupply = fuelSupplyRepository.findAll().get(0);
        assertThat(savedFuelSupply.getId().getStationId()).isEqualTo(stationId);
        assertThat(savedFuelSupply.getId().getFuelId()).isEqualTo(fuelId);

        // Inventory composite key points to the right store and product
        var savedInventory = inventoryRepository.findAll().get(0);
        assertThat(savedInventory.getId().getStoreId()).isEqualTo(storeId);
        assertThat(savedInventory.getId().getProductId()).isEqualTo(productId);
        assertThat(savedInventory.getPrice()).isEqualTo(29.99f);
    }
}
