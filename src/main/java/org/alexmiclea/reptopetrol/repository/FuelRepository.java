package org.alexmiclea.reptopetrol.repository;

import org.alexmiclea.reptopetrol.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, UUID> {}