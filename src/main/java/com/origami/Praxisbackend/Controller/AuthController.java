package com.origami.Praxisbackend.Controller;

import com.origami.Praxisbackend.Dto.JwtAuthResponse;
import com.origami.Praxisbackend.Dto.LoginDto;
import com.origami.Praxisbackend.Dto.RegisterDto;
import com.origami.Praxisbackend.Dto.UserDto;
import com.origami.Praxisbackend.Service.AuthService;
import com.origami.Praxisbackend.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("auth")
public class AuthController {

    private UserService userService;

    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.fetchUser(id), HttpStatus.OK);
    }

    @GetMapping("/finddoctororpatientid/{id}")
    public ResponseEntity<Long> getPatientOrDocId(@PathVariable Long id){
        return new ResponseEntity<>(userService.findDOCorPATID(id), HttpStatus.OK);
    }
}
