package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.model.management.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductRetrievalMapper {
    public abstract ProductRetrievalDto toProductDto(Product product);

    public abstract List<ProductRetrievalDto> toProductDtos(List<Product> products);
}