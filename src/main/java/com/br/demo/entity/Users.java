package com.br.demo.entity;

import com.br.demo.dtos.userDto.CreateUserDTO;
import com.br.demo.dtos.userDto.UpdateUserDTO;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users", schema = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    @Email
    private String email;
    private String password;

    public Users(CreateUserDTO user) throws IllegalArgumentException {
        this.firstname = user.firstName;
        this.lastname = user.lastName;
        this.email = user.email;
        this.password = user.password;
    }

    public Users(UpdateUserDTO user) {
        this.firstname = user.firstName;
        this.lastname = user.lastName;
        this.email = user.email;
        this.password = user.password;
    }

    // public void SetPassword(String password, String rePassword) {
    // if (password.compareTo(rePassword) != 0)
    // throw new IllegalArgumentException("As senhas não são iguais!");

    // this.password = password;
    // }

    @Override
    public String toString() {
        return this.getId() + "\n" + this.getEmail();
    }
}
