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
public class ItemPurchase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID productId;
    private String productName;
    private String productType;
    private Integer quantity;
    private Float unitPrice;
}
