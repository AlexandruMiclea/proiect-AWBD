package org.alexmiclea.reptopetrol.repository;

import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelSupplyRepository extends JpaRepository<FuelSupply, FuelSupplyKey> {
    FuelSupplyKey fuel(Fuel fuel);
}
