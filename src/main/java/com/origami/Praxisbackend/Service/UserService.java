package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Dto.UserDto;

import java.util.Optional;

public interface UserService {

    String createUser(UserDto userDto);

    UserDto fetchUser(Long userId);

    UserDto updateUser(Long id, UserDto userDto);

    Long findDOCorPATID(Long userID);
}
