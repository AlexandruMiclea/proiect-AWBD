package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.model.management.Product;
import org.mapstruct.Mapper;

import java.util.List;

// TODO - like the fuel - station pair, add mappers to extract linking ids

@Mapper(componentModel = "spring")
public abstract class ProductRetrievalMapper {
    public abstract ProductRetrievalDto toProductDto(Product product);

    public abstract List<ProductRetrievalDto> toProductDtos(List<Product> products);
}