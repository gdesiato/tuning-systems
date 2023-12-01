package com.desiato.puresynth.services;

import com.desiato.puresynth.models.Role;
import com.desiato.puresynth.models.User;
import com.desiato.puresynth.repositories.RoleRepository;
import com.desiato.puresynth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        // Create and save a test user, if not already present
        User user = userRepository.findByUsername("testUser");
        if (user == null) {
            user = new User();
            user.setUsername("testUser");
            user.setPassword(passwordEncoder.encode("testPassword"));
            user.setEmail("testUser@mail.com");

            // Fetch or create the role
            Role userRole = roleRepository.findByName("ROLE_USER"); // role name
            if (userRole == null) {
                // Create the role if it doesn't exist
                userRole = new Role();
                userRole.setName("ROLE_USER"); // role name
                roleRepository.save(userRole);
            }

            // Set the role to the user
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            // Save the user with the role
            userRepository.save(user);
        }
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "testUser";
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Assert
        assertEquals(username, userDetails.getUsername());
        assertTrue(passwordEncoder.matches("testPassword", userDetails.getPassword()));
        assertFalse(userDetails.getAuthorities().isEmpty());
    }
}