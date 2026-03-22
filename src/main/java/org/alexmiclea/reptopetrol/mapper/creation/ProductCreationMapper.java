package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductCreationMapper {
    public abstract ProductCreationDto toProductDto(Product product);
    public abstract Product toProduct(ProductCreationDto productDto);

    public abstract List<ProductCreationDto> toProductDtos(List<Product> products);
    public abstract List<Product> toProducts(List<ProductCreationDto> productDtos);
}