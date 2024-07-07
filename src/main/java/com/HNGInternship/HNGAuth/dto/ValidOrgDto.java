package com.HNGInternship.HNGAuth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ValidOrgDto {
    @NotBlank(message = "First name is mandatory")
    private String name;
    private String description;
}
