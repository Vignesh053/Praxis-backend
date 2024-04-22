package com.origami.Praxisbackend.Dto;

import com.origami.Praxisbackend.Entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;


    private String email;

    private String password;

    private Set<Role> roles;
}
