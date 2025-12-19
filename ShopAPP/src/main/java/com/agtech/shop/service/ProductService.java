package com.agtech.shop.service;

import com.agtech.shop.entity.Product;
import com.agtech.shop.mapper.ProductMapper;
import com.agtech.shop.model.dto.ProductDTO;
import com.agtech.shop.model.formDto.ProductFormDTO;
import com.agtech.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MinioService minioService;

    @Transactional
    public ProductDTO createProduct(ProductFormDTO productFormDTO , MultipartFile image){

        // Upload de l'image vers MinIO
        String imageUrl = minioService.uploadImage(image);
        Product product = new Product();
        product.setName(productFormDTO.name());
        product.setDescription(productFormDTO.description());
        product.setPrice(productFormDTO.price());
        product.setImageUrl(imageUrl);
        productRepository.save(product);

        System.out.println(" repo : name=" + product.getName() + ", price=" + product.getPrice() + ", description=" + product.getDescription());


        return ProductMapper.INSTANCE.toDTO(product);
    }
}
