package org.alexmiclea.reptopetrol.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Purchase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @PastOrPresent
    @NotNull
    private Instant paymentTime;

    private List<FuelPurchase> fuelPurchases;

    private List<ItemPurchase> itemPurchases;
}
