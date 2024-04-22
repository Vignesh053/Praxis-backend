package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Role;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestPropertySource(properties = { "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect" })
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        Role role = Role.builder()
                .name("ROLE_USER")
                .build();
        entityManager.persist(role);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();
        user.setRoles(roles);
        entityManager.persist(user);
    }

    @Test
    @DisplayName("Given email and username, when existsByEmailOrUsername, should return true")
    void givenEmailAndUsername_whenExistsByEmailOrUsername_shouldReturnTrue() {
        boolean exists = userRepository.existsByEmailOrUsername(user.getEmail(), user.getUsername());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Given username, when findByUsername, should return user")
    void givenUsername_whenFindByUsername_shouldReturnUser() {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    @DisplayName("Given username, when existsByUsername, should return true")
    void givenUsername_whenExistsByUsername_shouldReturnTrue() {
        boolean exists = userRepository.existsByUsername(user.getUsername());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Given email, when existsByEmail, should return true")
    void givenEmail_whenExistsByEmail_shouldReturnTrue() {
        boolean exists = userRepository.existsByEmail(user.getEmail());
        assertThat(exists).isTrue();
    }
}