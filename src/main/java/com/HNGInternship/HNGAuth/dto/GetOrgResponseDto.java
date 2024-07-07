package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetOrgResponseDto {
    private String status;
    private String message;
    private GetOrgData data;
}
