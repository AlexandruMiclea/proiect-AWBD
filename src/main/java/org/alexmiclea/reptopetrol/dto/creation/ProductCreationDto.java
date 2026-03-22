package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationDto {

    @Nullable
    private UUID id;

    @NotBlank
    @Size(max = 64)
    private String name;

    @NotNull
    @Positive
    private Float price;
}