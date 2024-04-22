package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Dto.JwtAuthResponse;
import com.origami.Praxisbackend.Dto.LoginDto;
import com.origami.Praxisbackend.Dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}
