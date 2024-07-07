package com.HNGInternship.HNGAuth.controller;

import com.HNGInternship.HNGAuth.Role;
import com.HNGInternship.HNGAuth.repository.OrganizationRepository;
import com.HNGInternship.HNGAuth.repository.UserRepository;
import com.HNGInternship.HNGAuth.config.JwtService;
import com.HNGInternship.HNGAuth.dto.*;
import com.HNGInternship.HNGAuth.model.HNGUser;
import com.HNGInternship.HNGAuth.model.Organization;
import com.HNGInternship.HNGAuth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    @Autowired
    OrganizationRepository orgRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        List<Map<String, String>> errors = new ArrayList<>();

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(fieldError -> Map.of(
                            "field", fieldError.getField(),
                            "message", fieldError.getDefaultMessage()))
                    .toList());
            return new ResponseEntity<>(Map.of("errors", errors), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            try {
                HNGUser user = new HNGUser();
                String name = userDto.getFirstName();
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setPhone(userDto.getPhone());
                user.setRole(Role.USER);
                // Generate access token
                String accessToken = jwtService.generateToken(user);

                Organization org = new Organization();
                org.setName(name + "'s Organization");
                org.setDescription("This is " + name + "'s organization");
                orgRepository.save(org);
                user.getOrganizations().add(org);
                userService.createUser(user);

                // Create response data
                AuthUserDto responseUserDTO = new AuthUserDto();
                responseUserDTO.setId(user.getUserId());
                responseUserDTO.setEmail(userDto.getEmail());
                responseUserDTO.setFirstName(userDto.getFirstName());
                responseUserDTO.setLastName(userDto.getLastName());
                responseUserDTO.setPhone(userDto.getPhone());

                DataDto data = new DataDto();
                data.setAccessToken(accessToken);
                data.setUser(responseUserDTO);

                responseDto response = new responseDto();
                response.setStatus("success");
                response.setMessage("Registration successful");
                response.setData(data);

                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                Map<String, Object> errorResponse = Map.of(
                        "status", "Bad request",
                        "message", "Registration unsuccessful",
                        "statusCode", 400
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?>authenticateUser(@RequestBody LoginDto loginDto){

        try {
            // Attempt to authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));

            // Retrieve the user from the repository
            var user = repository.findByEmail(loginDto.getEmail())
                    .orElseThrow();

            // Generate token
            var token = jwtService.generateToken(user);

            // Create response data
            AuthUserDto responseUserDTO = new AuthUserDto();
            responseUserDTO.setId(user.getUserId());
            responseUserDTO.setEmail(user.getEmail());
            responseUserDTO.setFirstName(user.getFirstName());
            responseUserDTO.setLastName(user.getLastName());
            responseUserDTO.setPhone(user.getPhone());

            DataDto data = new DataDto();
            data.setAccessToken(token);
            data.setUser(responseUserDTO);

            responseDto response = new responseDto();
            response.setStatus("success");
            response.setMessage("Login successful");
            response.setData(data);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle authentication failure
            Map<String, Object> errorResponse = Map.of(
                    "status", "Bad request",
                    "message", "Authentication failed",
                    "statusCode", 40
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
