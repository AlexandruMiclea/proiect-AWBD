package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;

import java.util.UUID;

@Data
public class StoreCreationDto {

    private UUID id;
    private StationCreationDto station;
}
