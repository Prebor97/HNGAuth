package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class orgResponseDto {
    private String status;
    private String message;
    private orgDataDto data;
}
