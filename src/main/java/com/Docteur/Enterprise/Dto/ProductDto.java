package com.Docteur.Enterprise.Dto;

import org.springframework.web.multipart.MultipartFile;

import com.Docteur.Enterprise.Enum.ProductType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {

 @NotBlank(message = "Brand cannot be blank")
    @Schema(description = "Brand of the product", example = "Apple")
    private String brand;
 
    @NotBlank(message = "Serie cannot be blank")
    @Schema(description = "Series/Model of the product", example = "iPhone 14 Pro")
    private String serie;
 
    @NotBlank(message = "IMEI cannot be blank")
    @Schema(description = "Unique IMEI identifier", example = "353439411234567")
    private String imei;
 
    @NotNull(message = "Product type cannot be null")
    @Schema(description = "Type of product", example = "SMARTPHONE")
    private ProductType productType;
 
    @Schema(description = "Product image file")
    private MultipartFile image; // ✅ Image file for upload
 
    @NotNull(message = "Client ID cannot be null")
    @Schema(description = "Client ID who owns the product", example = "1")
    private Long clientId; // ✅ Reference to Client


}
