package org.alexmiclea.reptopetrol.repository;

import org.alexmiclea.reptopetrol.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID> {}