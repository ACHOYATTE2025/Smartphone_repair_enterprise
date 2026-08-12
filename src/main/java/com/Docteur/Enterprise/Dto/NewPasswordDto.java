package com.Docteur.Enterprise.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewPasswordDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password1;

    
    @NotBlank
    private String password2;



}
