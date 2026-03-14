package org.alexmiclea.reptopetrol.repository;

import org.alexmiclea.reptopetrol.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FuelRepository extends JpaRepository<Fuel, UUID> {}