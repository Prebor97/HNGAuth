package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetDataDto {
    private GetUserDto user;
}
