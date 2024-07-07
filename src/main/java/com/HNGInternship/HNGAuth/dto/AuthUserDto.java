package com.HNGInternship.HNGAuth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthUserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
