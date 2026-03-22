package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductRetrievalMapper {
    public abstract ProductRetrievalDto toProductDto(Product product);
    public abstract Product toProduct(ProductRetrievalDto productDto);

    public abstract List<ProductRetrievalDto> toProductDtos(List<Product> products);
    public abstract List<Product> toProducts(List<ProductRetrievalDto> productDtos);
}