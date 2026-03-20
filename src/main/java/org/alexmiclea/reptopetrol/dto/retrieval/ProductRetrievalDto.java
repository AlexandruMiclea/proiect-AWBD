package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductRetrievalDto {

    private UUID id;
    private String name;
    private Float price;
}
