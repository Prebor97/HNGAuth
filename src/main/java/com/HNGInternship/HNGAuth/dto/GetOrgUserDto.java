package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetOrgUserDto {
    private String id;
    private String name;
    private String description;
}
