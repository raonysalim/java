package com.br.demo.dtos.userDto;

import java.util.Optional;

import com.br.demo.entity.Users;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadUserDTO {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public ReadUserDTO(Optional<Users> userOPT) {
        Users user = userOPT.get();
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public ReadUserDTO(Users user) {
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
