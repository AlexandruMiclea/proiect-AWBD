package org.alexmiclea.reptopetrol.repository;

import org.alexmiclea.reptopetrol.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
