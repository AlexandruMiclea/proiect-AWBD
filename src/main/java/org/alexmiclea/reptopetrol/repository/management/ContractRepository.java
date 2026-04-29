package org.alexmiclea.reptopetrol.repository.management;

import org.alexmiclea.reptopetrol.model.management.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {}