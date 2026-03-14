package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.ProductDto;
import org.alexmiclea.reptopetrol.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toProduct(ProductDto productDto);

    List<ProductDto> toProductDtos(List<Product> products);
    List<Product> toProducts(List<ProductDto> productDtos);
}