package com.Docteur.Enterprise.Dto;

import com.Docteur.Enterprise.Enum.TypeRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupDto {

  @NotBlank
  private String username;

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private TypeRole typeRole;

}
