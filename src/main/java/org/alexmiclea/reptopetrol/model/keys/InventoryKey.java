package org.alexmiclea.reptopetrol.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
public class InventoryKey implements Serializable {
    @Column(name = "store_id")
    UUID storeId;

    @Column(name = "product_id")
    UUID productId;
}