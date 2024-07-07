package com.HNGInternship.HNGAuth.dto;

import com.HNGInternship.HNGAuth.model.Organization;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetOrgData {
    private String orgId;
    private String name;
    private String description;
}
