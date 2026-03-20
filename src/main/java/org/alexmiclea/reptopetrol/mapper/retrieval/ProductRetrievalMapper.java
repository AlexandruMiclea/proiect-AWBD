package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductRetrievalMapper {
    ProductRetrievalDto toProductDto(Product product);
    Product toProduct(ProductRetrievalDto productDto);

    List<ProductRetrievalDto> toProductDtos(List<Product> products);
    List<Product> toProducts(List<ProductRetrievalDto> productDtos);
}