package com.Docteur.Enterprise.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

}
