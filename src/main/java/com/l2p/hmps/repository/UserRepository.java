package com.l2p.hmps.repository;

import com.l2p.hmps.model.Role;
import com.l2p.hmps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Used during login and by Spring Security's UserDetailsService 
    Optional<User> findByEmail(String email);

    long countByRole(Role role);
    
    // Used to prevent duplicate registrations [cite: 34, 36]
    boolean existsByEmail(String email);
}