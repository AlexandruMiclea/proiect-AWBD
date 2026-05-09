package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getProducts(Model model) {
        log.debug("GET /all called");

        model.addAttribute("products", productService.getAll());

        return "management/products/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getProductCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        ProductCreationDto productCreationDto = new ProductCreationDto();

        // TODO you need to add a list of other elements, so you can have a dropdown and select them

        model.addAttribute("productCreationDto", productCreationDto);

        return "management/products/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getProductUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("PUT /update called for UUID {}", uuid);

        Optional<ProductRetrievalDto> productRetrievalDto = productService.getProductById(uuid);

        if (productRetrievalDto.isPresent()) {

            ProductRetrievalDto retrieved = productRetrievalDto.get();

            ProductCreationDto productCreationDto = ProductCreationDto.builder()
                    .id(uuid)
                    .name(retrieved.getName())
                    .type(retrieved.getType())
                    .build();

            model.addAttribute("productCreationDto", productCreationDto);
            return "management/products/update";
        } else {
            return "management/products/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String addProduct(@RequestBody @Validated ProductCreationDto productDto) {
        log.debug("POST /add called with payload {}", productDto);

        productService.addProduct(productDto);

        return "redirect:api/product/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String updateProduct(@RequestBody @Validated ProductCreationDto productDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", productDto, uuid);

        productService.updateProduct(productDto, uuid);

        return "redirect:api/product/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String deleteProduct(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = productService.deleteProduct(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:api/product/all";
    }
}