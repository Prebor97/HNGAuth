package com.HNGInternship.HNGAuth.service;

import com.HNGInternship.HNGAuth.repository.UserRepository;
import com.HNGInternship.HNGAuth.model.HNGUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public HNGUser createUser(HNGUser user) {
        return userRepository.save(user);
    }

    public Optional<HNGUser> findById(String id) {
        return userRepository.findById(id);
    }

    public String getCurrentSaverId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HNGUser user = (HNGUser) authentication.getPrincipal();
        return user.getUserId();
    }
}
