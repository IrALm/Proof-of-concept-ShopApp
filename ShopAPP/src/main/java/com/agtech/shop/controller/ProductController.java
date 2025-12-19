package com.agtech.shop.controller;

import com.agtech.shop.model.dto.ProductDTO;
import com.agtech.shop.model.formDto.ProductFormDTO;
import com.agtech.shop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(
            value = "create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ProductDTO> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("image") MultipartFile image
    ) {
        ProductFormDTO dto = ProductFormDTO.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();

        return ResponseEntity.ok(productService.createProduct(dto, image));
    }

}
