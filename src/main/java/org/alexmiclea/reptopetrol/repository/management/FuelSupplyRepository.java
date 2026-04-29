package org.alexmiclea.reptopetrol.repository.management;

import org.alexmiclea.reptopetrol.model.management.Fuel;
import org.alexmiclea.reptopetrol.model.management.composites.FuelSupply;
import org.alexmiclea.reptopetrol.model.management.keys.FuelSupplyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelSupplyRepository extends JpaRepository<FuelSupply, FuelSupplyKey> {
    FuelSupplyKey fuel(Fuel fuel);
}
