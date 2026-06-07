package org.alexmiclea.reptopetrol.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class FuelPurchase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID fuelId;
    private String fuelName;
    private String fuelType;
    private Integer pumpNo;
    private Float quantity;
    private Float unitPrice;
}
