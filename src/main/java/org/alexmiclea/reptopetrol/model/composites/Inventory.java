package org.alexmiclea.reptopetrol.model.composites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Inventory {
    @EmbeddedId
    private InventoryKey id;

    @ManyToOne
    @MapsId("storeId")
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Positive
    @NotNull
    private Integer quantity;

    @Positive
    @NotNull
    private Float price;

    @LastModifiedDate
    private Instant priceChange;
}
