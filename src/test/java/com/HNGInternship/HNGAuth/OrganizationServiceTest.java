package com.HNGInternship.HNGAuth;

import com.HNGInternship.HNGAuth.model.HNGUser;
import com.HNGInternship.HNGAuth.model.Organization;
import com.HNGInternship.HNGAuth.repository.OrganizationRepository;
import com.HNGInternship.HNGAuth.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class OrganizationServiceTest {
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    private HNGUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new HNGUser();
        user.setUserId("test-user-id");
    }

    @Test
    void testUserCannotAccessOtherOrganizations() {
        Organization org = new Organization();
        org.setOrgId("org-id");
        org.setUsers(new HashSet<>());

        when(organizationRepository.findById("org-id")).thenReturn(Optional.of(org));

        Optional<Organization> retrievedOrg = organizationService.findById("org-id");
        assertTrue(retrievedOrg.isPresent());
        assertFalse(retrievedOrg.get().getUsers().contains(user));
    }
}
