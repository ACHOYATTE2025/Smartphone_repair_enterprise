package com.Docteur.Enterprise.Services;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Docteur.Enterprise.Dto.ProductDto;
import com.Docteur.Enterprise.Entities.Client;
import com.Docteur.Enterprise.Entities.Image;
import com.Docteur.Enterprise.Entities.Product;
import com.Docteur.Enterprise.Repositories.ClientRepository;
import com.Docteur.Enterprise.Repositories.ImageRepository;
import com.Docteur.Enterprise.Repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ClientRepository clientRepository;
 


    
  
   
    public Product registerProduct(ProductDto productDto) {
        
        // ✅ Verify client exists
        Client client = clientRepository.findById(productDto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Client with ID " + productDto.getClientId() + " not found"));
 
        // ✅ Create and populate Image entity if image is provided
        Image image = null;
        if (productDto.getImage() != null && !productDto.getImage().isEmpty()) {
            image = saveImage(productDto.getImage());
        }
 
        // ✅ Create Product entity
        Product product = Product.builder()
                .brand(productDto.getBrand())
                .serie(productDto.getSerie())
                .imei(productDto.getImei())
                .producType(productDto.getProductType())
                .image(image)
                .build();
 
        // ✅ Save to database
        Product savedProduct = productRepository.save(product);
        log.info("Product registered successfully with ID: {}", savedProduct.getId());
        
        return savedProduct;
    }
 
    /**
     * Save image file to database
     * 
     * @param file MultipartFile containing the image
     * @return Image entity with file data
     * @throws IllegalArgumentException if file processing fails
     */
    private Image saveImage(MultipartFile file) {
        try {
            // ✅ Validate file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Image file is empty");
            }
 
            String contentType = file.getContentType();
            if (contentType == null || !isValidImageType(contentType)) {
                throw new IllegalArgumentException(
                    "Invalid image type. Allowed: JPEG, PNG, GIF, WebP");
            }
 
            // ✅ Create Image entity
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setContentType(contentType);
            image.setData(file.getBytes()); // ✅ Convert file to byte array
            image.setUploaded(LocalDateTime.now());
 
            // ✅ Save to database
            Image savedImage = imageRepository.save(image);
            log.info("Image saved successfully with ID: {}", savedImage.getId());
            
            return savedImage;
 
        } catch (IOException e) {
            log.error("Error processing image file: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to process image file: " + e.getMessage());
        }
    }
 
    /**
     * Validate if content type is a supported image format
     */
    private boolean isValidImageType(String contentType) {
        return contentType.equals("image/jpeg") || 
               contentType.equals("image/png") ||
               contentType.equals("image/gif") ||
               contentType.equals("image/webp");
    }
 
    /**
     * Get product by ID with image
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }

  }



