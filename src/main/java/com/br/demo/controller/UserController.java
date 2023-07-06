package com.br.demo.controller;

import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.hibernate.mapping.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.var;

import com.br.demo.assets.Errorsd;
import com.br.demo.dtos.TokenDTO;
import com.br.demo.dtos.userDto.CreateUserDTO;
import com.br.demo.dtos.userDto.ReadUserDTO;
import com.br.demo.dtos.userDto.RequestUserDTO;
import com.br.demo.dtos.userDto.UpdateUserDTO;
import com.br.demo.entity.Users;
import com.br.demo.entity.UsersRepository;
import com.br.demo.service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private UserService service;

    @GetMapping
    public List<Users> FindAllUsers() {
        return repository.findAll();
    }

    @PostMapping("/teste/{id}")
    public ResponseEntity teste(@RequestBody RequestUserDTO requestUser, @PathVariable("id") long id) {

        return new ResponseEntity<>(service.checkToken(requestUser.password()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity FindUserById(@PathVariable("id") long id) {
        try {
            ReadUserDTO user = service.FindByIdUsers(id);
            return new ResponseEntity<ReadUserDTO>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity CreateUser(@RequestBody RequestUserDTO user) {
        try {
            return new ResponseEntity<ReadUserDTO>(service.CreateUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/{id}")
    public ResponseEntity login(@RequestBody RequestUserDTO userRequest, @PathVariable("id") long id) {

        try {
            TokenDTO token = service.Login(userRequest, id);
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity UpdatePutUser(@PathVariable("id") long id,
            @RequestBody UpdateUserDTO userRequest) {
        try {
            Users user = service.UpdateUser(id, userRequest);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("FODEUUUUUUUUUUUUUUUUUUUU");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity DeleteUser(@PathVariable("id") long id) {
        try {
            return service.DeleteUser(id) ? new ResponseEntity<>("Usuário deletado", HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

// ReadUserDTO user = service.FindByIdUsers(id);
// BCrypt.Result result =
// BCrypt.verifyer().verify(requestUser.password().toCharArray(),
// user.password);
// return result.verified;
// try {
// SecretKey key = Keys.hmacShaKeyFor(
// System.getenv("JWT_SECRET_KEY")
// .getBytes(StandardCharsets.UTF_8));
// String jwtToken = Jwts.builder()
// .setSubject("teste")
// .setIssuer("localhost:8080")
// .setIssuedAt(new Date())
// .setExpiration(
// Date.from(
// LocalDateTime.now().plusMinutes(15L)
// .atZone(ZoneId.systemDefault())
// .toInstant()))
// .signWith(key, SignatureAlgorithm.HS512)
// .compact();

// try {
// Jwt t = Jwts.parserBuilder().setSigningKey(key).build().parse(
// "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZSIsImlzcyI6ImxvY2FsaG9zdDo4MDgwIiwiaWF0IjoxNjg3NjI5NjYzLCJleHAiOjE2ODc2MzA1NjN9.knDOCZL9C3JTM5IHy2q9Mry-2gvTzNTymyVu5WwlEAoIk1qUV-aSwuaI1Jwe5m-MLwcUvzfim8_hVOPS4qo_hQ");
// System.out.println(t);
// } catch (Exception e) {
// System.out.println(e.getMessage());
// System.out.println("\n");
// System.out.println(jwtToken);
// }

// } catch (Exception e) {
// System.out.println(e.getMessage() + " aaaaaa");

// }