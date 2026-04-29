package org.alexmiclea.reptopetrol.model.management.composites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.alexmiclea.reptopetrol.model.management.Product;
import org.alexmiclea.reptopetrol.model.management.Store;
import org.alexmiclea.reptopetrol.model.management.keys.InventoryKey;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "inventory")
@ToString
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

    // TODO de verificat, daca faci PUT, se modifica valoarea? (ea vine prima oara completata din creationDto)
    @LastModifiedDate
    private Instant priceChange;
}
