package com.Docteur.Enterprise.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponseDto {
    private String token;
    private String refresh;

}
