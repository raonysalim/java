package com.br.demo.dtos.userDto;

import java.util.Objects;

import org.hibernate.sql.results.graph.instantiation.internal.ArgumentDomainResult;
import org.springframework.http.HttpStatus;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Getter;
import lombok.Setter;

public class CreateUserDTO {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public CreateUserDTO(String firstName, String lastName, String email, String password, String rePassword) {
        passCheck(password, rePassword);
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        this.email = email != null ? email : "";
        this.password = password != null ? password : "";
    }

    public CreateUserDTO(RequestUserDTO user) throws IllegalArgumentException {
        passCheck(user.password(), user.rePassword());

        String hash = BCrypt.withDefaults().hashToString(12, user.password().toCharArray());

        this.firstName = user.firstName();
        this.lastName = user.lastName();
        this.email = user.email();
        this.password = hash;
    }

    public void passCheck(String password, String rePassword) throws IllegalArgumentException {
        if (password.compareTo(rePassword) != 0)
            throw new IllegalArgumentException("Senhas não são iguais!");
    }

}