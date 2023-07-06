package com.br.demo.dtos.userDto;

import org.hibernate.sql.results.graph.instantiation.internal.ArgumentDomainResult;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateUserDTO {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String rePassword;

    public UpdateUserDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        this.email = email != null ? email : "";
        this.password = password != null ? password : "";
    }

}
