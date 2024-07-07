package com.HNGInternship.HNGAuth.service;

import com.HNGInternship.HNGAuth.model.HNGUser;
import com.HNGInternship.HNGAuth.model.Organization;
import com.HNGInternship.HNGAuth.repository.OrganizationRepository;
import com.HNGInternship.HNGAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository repository;

    public Organization createOrganization(Organization organization) {
        return repository.save(organization);
    }

    public Optional<Organization> findById(String id) {
        return repository.findById(id);
    }
}
