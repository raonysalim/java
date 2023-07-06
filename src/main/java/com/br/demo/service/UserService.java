package com.br.demo.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.br.demo.dtos.TokenDTO;
import com.br.demo.dtos.userDto.CreateUserDTO;
import com.br.demo.dtos.userDto.ReadUserDTO;
import com.br.demo.dtos.userDto.RequestUserDTO;
import com.br.demo.dtos.userDto.UpdateUserDTO;
import com.br.demo.entity.Users;
import com.br.demo.entity.UsersRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;

@Service
public class UserService {
    @Autowired
    private UsersRepository repository;

    @Value("${secutiry.token.secret}")
    private String secret;

    public List<Users> FindAllUsers() {
        return repository.findAll();
    }

    public ReadUserDTO FindByIdUsers(long id) throws IllegalArgumentException {
        Optional<Users> user = repository.findById(id);
        if (!user.isPresent())
            throw new IllegalArgumentException();
        return new ReadUserDTO(user.get());
    }

    public ReadUserDTO FindByEmailUsers(String email) throws IllegalArgumentException {
        Optional<Users> user = repository.findByEmail(email);
        if (!user.isPresent())
            throw new IllegalArgumentException();
        return new ReadUserDTO(user.get());
    }

    public TokenDTO Login(@RequestBody RequestUserDTO userRequest, @PathVariable("id") long id) {

        ReadUserDTO user = this.FindByIdUsers(id);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        BCrypt.Result checkUser = BCrypt.verifyer().verify(userRequest.password().toCharArray(), user.password);

        if (!checkUser.verified)
            throw new IllegalArgumentException();

        String jwtToken = Jwts.builder()
                .setSubject("teste")
                .setIssuer("localhost:8080")
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now().plusMinutes(15L)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new TokenDTO(jwtToken);

    }

    public ReadUserDTO CreateUser(@RequestBody RequestUserDTO userRequest) {
        var createUser = new CreateUserDTO(userRequest);
        Users newUser = repository.save(new Users(createUser));
        return new ReadUserDTO(newUser);
    }

    public Users UpdateUser(long id, UpdateUserDTO userRequest) throws IllegalArgumentException {

        Optional<Users> userOPT = repository.findById(id);

        if (userOPT.isEmpty())
            throw new IllegalArgumentException();

        Users user = userOPT.get();
        user.setEmail(userRequest.email.length() > 0 ? userRequest.email : user.getEmail());
        user.setFirstname(userRequest.firstName.length() > 0 ? userRequest.firstName : user.getFirstname());
        user.setLastname(userRequest.lastName.length() > 0 ? userRequest.lastName : user.getLastname());
        user.setPassword(userRequest.password.length() > 0 ? userRequest.password : user.getPassword());
        return repository.save(user);

    }

    public Jws<?> checkToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(
                System.getenv("JWT_SECRET_KEY")
                        .getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public boolean DeleteUser(long id) {
        Optional<Users> user = repository.findById(id);
        if (user.isPresent()) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }
}
