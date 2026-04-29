package org.alexmiclea.reptopetrol.model.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ItemPurchase {

    private UUID productId;
    private String productName;
    private String productType;
    private Integer quantity;
    private Float unitPrice;
}
