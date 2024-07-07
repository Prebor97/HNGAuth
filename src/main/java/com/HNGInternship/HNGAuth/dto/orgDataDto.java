package com.HNGInternship.HNGAuth.dto;

import com.HNGInternship.HNGAuth.model.Organization;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class orgDataDto {
    private Set<Organization> organisations;
}
