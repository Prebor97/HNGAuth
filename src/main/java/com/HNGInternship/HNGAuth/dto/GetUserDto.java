package com.HNGInternship.HNGAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetUserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}

