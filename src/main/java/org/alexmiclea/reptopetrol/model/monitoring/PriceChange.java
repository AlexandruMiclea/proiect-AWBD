package org.alexmiclea.reptopetrol.model.monitoring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.alexmiclea.reptopetrol.dto.management.keys.FuelSupplyKeyDto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
@ToString
public class PriceChange implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private FuelSupplyKeyDto key;
    private BigDecimal price;
}
