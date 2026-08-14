package com.Docteur.Enterprise.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Docteur.Enterprise.Dto.ProductDto;
import com.Docteur.Enterprise.Dto.ResponseDto;
import com.Docteur.Enterprise.Entities.Product;
import com.Docteur.Enterprise.Enum.ProductType;
import com.Docteur.Enterprise.Repositories.ProductRepository;
import com.Docteur.Enterprise.Services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(
  name = "Product Controller",
  description="Controller REST Api for  details"
)
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;





    /********************************************************************************************************************
     *                                          PRODUCT REGISTRATION WITH IMAGE
     ********************************************************************************************************************/
 
    @Operation(
        summary = "Register a new product with image",
        description = "Register a new smartphone product with optional image. Use multipart/form-data to upload."
    )
    @PostMapping(
        value = "/register",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto> registerProduct(
                                                        @RequestParam("brand") String brand,
                                                        @RequestParam("serie") String serie,
                                                        @RequestParam("imei") String imei,
                                                        @RequestParam("productType") ProductType productType,
                                                        @RequestParam("clientId") Long clientId,
                                                        @RequestParam(value = "image", required = false) MultipartFile image)
                                                         {
 
        try {
            log.info("Registering product - Brand: {}, IMEI: {}, ClientId: {}", brand, imei, clientId);
 
            // ✅ Create ProductDto from request parameters
            ProductDto productDto = ProductDto.builder()
                    .brand(brand)
                    .serie(serie)
                    .imei(imei)
                    .productType(productType)
                    .clientId(clientId)
                    .image(image)
                    .build();
 
            // ✅ Register product (service handles image saving)
            Product savedProduct = productService.registerProduct(productDto);
 
            log.info("Product registered successfully with ID: {}", savedProduct.getId());
 
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ResponseDto(
                            201,
                            "Product registered successfully",
                            "Product ID: " + savedProduct.getId()
                    ));
 
        } catch (IllegalArgumentException e) {
            log.warn("Validation error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "Validation Failed", e.getMessage()));
 
        } catch (Exception e) {
            log.error("Error registering product: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(500, "Server Error", "Failed to register product"));
        }
    }
 
    

}
