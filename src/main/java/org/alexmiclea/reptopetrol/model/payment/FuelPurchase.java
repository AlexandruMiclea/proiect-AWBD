package org.alexmiclea.reptopetrol.model.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FuelPurchase {

    private UUID fuelId;
    private String fuelName;
    private String fuelType;
    private Integer pumpNo;
    private Float quantity;
    private Float unitPrice;
}
