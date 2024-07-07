package com.HNGInternship.HNGAuth.repository;

import com.HNGInternship.HNGAuth.model.HNGUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<HNGUser, String> {
    Optional<HNGUser> findByEmail(String username);
}
