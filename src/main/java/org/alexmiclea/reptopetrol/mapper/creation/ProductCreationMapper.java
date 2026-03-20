package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductCreationMapper {
    ProductCreationDto toProductDto(Product product);
    Product toProduct(ProductCreationDto productDto);

    List<ProductCreationDto> toProductDtos(List<Product> products);
    List<Product> toProducts(List<ProductCreationDto> productDtos);
}