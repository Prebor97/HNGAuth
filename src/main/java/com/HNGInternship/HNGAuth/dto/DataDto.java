package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class DataDto {
    private String accessToken;
    private AuthUserDto user;
}
