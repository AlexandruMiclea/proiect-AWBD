package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCreationDto {

    @Nullable
    private UUID id;

    @Nullable
    private List<UUID> contractIds;

    @NotBlank
    @Size(max = 64)
    private String name;

    @NotBlank
    @Size(max = 128)
    private String address;

    @NotBlank
    @Size(max = 128)
    private String homeCountry;
}