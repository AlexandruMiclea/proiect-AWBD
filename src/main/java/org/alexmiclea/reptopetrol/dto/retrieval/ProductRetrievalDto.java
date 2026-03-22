package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductRetrievalDto {

    private UUID id;
    private String name;
    private Float price;
}
