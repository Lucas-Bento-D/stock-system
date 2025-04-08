package com.marley_store.stock_system.service.jwtToken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.marley_store.stock_system.model.user.userDetailsImpl.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";
    private static final String ISSUER = "pizzurg-api";

    public String generateToken(UserDetailsImpl userDetails){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        try{
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(userDetails.getUser().getEmail())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new JWTCreationException("Error to generate token.", exception);
        }
    }

    public String getSubjectFromToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        try{
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // emissor do token como parametro
                    .build()
                    .verify(token) // verifica validade do token
                    .getSubject(); // pega o nome do usuario do token(nesse caso o email)
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid token");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
    }
}
