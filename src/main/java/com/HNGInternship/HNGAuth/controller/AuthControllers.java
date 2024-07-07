package com.HNGInternship.HNGAuth.controller;

import com.HNGInternship.HNGAuth.Role;
import com.HNGInternship.HNGAuth.repository.OrganizationRepository;
import com.HNGInternship.HNGAuth.repository.UserRepository;
import com.HNGInternship.HNGAuth.dto.*;
import com.HNGInternship.HNGAuth.model.HNGUser;
import com.HNGInternship.HNGAuth.model.Organization;
import com.HNGInternship.HNGAuth.service.OrganizationService;
import com.HNGInternship.HNGAuth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AuthControllers {

    @Autowired
    UserService userService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationRepository repository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        HNGUser user = userService.findById(id).orElseThrow();
        GetUserDto userDto = new GetUserDto();
        userDto.setId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());

        GetDataDto dataDto = new GetDataDto();
        dataDto.setUser(userDto);

        GetResponseDto responseDto = new GetResponseDto();
        responseDto.setMessage(user.getLastName()+" "+user.getFirstName()+" is currently authorized");
        responseDto.setStatus("success");
        responseDto.setData(dataDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/organisations")
    public ResponseEntity<?> getUserOrganisations(){
        HNGUser user = userService.findById(userService.getCurrentSaverId()).orElseThrow();
        Set<Organization> organizations = user.getOrganizations();

        orgDataDto dataDto = new orgDataDto();
        dataDto.setOrganisations(organizations);
        orgResponseDto responseDto = new orgResponseDto();
        responseDto.setStatus("success");
        responseDto.setMessage("These are the organisations "+user.getLastName()+" "+user.getFirstName()+" belong to");
        responseDto.setData(dataDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/organisations/{id}")
    public ResponseEntity<?> getUserOrganisation(@PathVariable String id){
        Organization org = organizationService.findById(id).orElseThrow();
        GetOrgData userDto = new GetOrgData();
        userDto.setOrgId(org.getOrgId());
        userDto.setName(org.getName());
        userDto.setDescription(org.getDescription());
        GetOrgResponseDto responseDto = new GetOrgResponseDto();
        responseDto.setStatus("success");
        responseDto.setMessage("This is organization you belong to is shown below");
        responseDto.setData(userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/organisations")
    public ResponseEntity<?> createOrganization(@Valid @RequestBody ValidOrgDto orgDto){
        try {
            HNGUser user = userService.findById(userService.getCurrentSaverId()).orElseThrow();
            Organization organization = new Organization();
            organization.setName(orgDto.getName());
            organization.setDescription(orgDto.getDescription());
            organization.getUsers().add(user);
            organizationService.createOrganization(organization);
//            user.getOrganizations().add(organization);

            // Create response data
            GetOrgData userDto = new GetOrgData();
            userDto.setOrgId(organization.getOrgId());
            userDto.setName(organization.getName());
            userDto.setDescription(organization.getDescription());
            GetOrgResponseDto responseDto = new GetOrgResponseDto();
            responseDto.setStatus("success");
            responseDto.setMessage("Organization created successfully");
            responseDto.setData(userDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                    "status", "Bad request",
                    "message", "Client error",
                    "statusCode", 400
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/organisations/{id}/users")
    public ResponseEntity<?> addUserToOrganization(@PathVariable String id, @RequestBody OrgUserDto userDto){
       Organization org = organizationService.findById(id).orElseThrow();
       HNGUser user = userService.findById(userDto.getId()).orElseThrow();
       org.getUsers().add(user);
       repository.save(org);
        Map<String, Object> response = Map.of(
                "status", "success",
                "message", "User added to organization successfully"
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


