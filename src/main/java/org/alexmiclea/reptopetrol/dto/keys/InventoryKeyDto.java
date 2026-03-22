package org.alexmiclea.reptopetrol.dto.keys;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class InventoryKeyDto implements Serializable {

    @NotNull
    UUID storeId;

    @NotNull
    UUID productId;
}