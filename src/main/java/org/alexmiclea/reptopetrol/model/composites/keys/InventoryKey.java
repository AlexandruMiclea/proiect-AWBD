package org.alexmiclea.reptopetrol.model.composites.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class InventoryKey implements Serializable {
    @Column(name = "store_id")
    UUID storeId;

    @Column(name = "product_id")
    UUID productId;
}