package com.leisure.entity.dto.Jwt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginResource {
    @NotBlank
    private String dni;
    @NotBlank
    private String password;
}
