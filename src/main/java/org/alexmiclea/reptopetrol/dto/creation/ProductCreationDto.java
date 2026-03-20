package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreationDto {

    private UUID id;
    private String name;
    private Float price;
}
