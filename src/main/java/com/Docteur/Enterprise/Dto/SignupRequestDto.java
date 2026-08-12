package com.Docteur.Enterprise.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequestDto {
  @NotBlank
  private String name;

  @NotBlank
  private String surname;


  @NotBlank
  private String email;

  @NotBlank
  private String phone;

  @NotBlank
  private String password;

  

}
