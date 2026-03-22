package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreationDto {

    private UUID id;
    private UUID stationId;
}
