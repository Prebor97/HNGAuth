package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetResponseDto {
    private String status;
    private String message;
    private GetDataDto data;
}
