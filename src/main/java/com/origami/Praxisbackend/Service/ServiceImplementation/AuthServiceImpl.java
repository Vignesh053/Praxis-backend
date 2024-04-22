package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Dto.JwtAuthResponse;
import com.origami.Praxisbackend.Dto.LoginDto;
import com.origami.Praxisbackend.Dto.RegisterDto;
import com.origami.Praxisbackend.Entity.Role;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.RoleRepository;
import com.origami.Praxisbackend.Repository.UserRepository;
import com.origami.Praxisbackend.Security.JwtUtil;
import com.origami.Praxisbackend.Service.AuthService;
import com.origami.Praxisbackend.Service.UserService;
import com.origami.Praxisbackend.exception.EmailAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new EmailAlreadyExistsException("User name already exist:" + registerDto.getUsername());
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EmailAlreadyExistsException(registerDto.getEmail());
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);
        return "User Registered Successfully!!";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(loginDto.getUsername());

        Optional<User> userOptional = userRepository
                .findByUsername(loginDto.getUsername());

        String role = null;
        Long userId = null;

        if (userOptional.isPresent()) {
            User loggedInUser = userOptional.get();
            userId = loggedInUser.getId();
//            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();
            List<Role> dbRoles = loggedInUser.getRoles().stream()
                    .filter(_role -> _role.getName().equalsIgnoreCase("ROLE_ADMIN"))
                    .collect(Collectors.toList());
            Role userRole;
            if (dbRoles.size() > 0) {
                userRole = dbRoles.get(0);
            } else {
                userRole = loggedInUser.getRoles().stream().findFirst().get();
            }
            role = userRole.getName();
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setUserId(userId);
        jwtAuthResponse.setDocOrPatId(userService.findDOCorPATID(userId));
        return jwtAuthResponse;
    }
}
