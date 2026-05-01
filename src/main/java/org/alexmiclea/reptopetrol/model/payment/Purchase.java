package org.alexmiclea.reptopetrol.model.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @PastOrPresent
    @NotNull
    private Instant paymentTime;

    private List<FuelPurchase> fuelPurchases;

    private List<ItemPurchase> itemPurchases;
}
