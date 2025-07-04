package com.foresys.core.component.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.foresys.core.model.jwt.TokenType;
import com.foresys.core.model.jwt.TokenUser;
import com.foresys.core.properties.jwt.JwtProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Getter
@Setter
public class JwtComponent {

    private JwtProperties jwtProperties;

    private Algorithm algorithm;

    private JWTVerifier jwtVerifierA;

    private JWTVerifier jwtVerifierR;

    public JwtComponent(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.algorithm = Algorithm.HMAC512(jwtProperties.getEncryptor().getSecretKey());
        this.jwtVerifierA = JWT
                .require(algorithm)
                .acceptExpiresAt(jwtProperties.getAccess().getTimeOut())
                .build();
        this.jwtVerifierR = JWT
                .require(algorithm)
                .acceptExpiresAt(jwtProperties.getRefresh().getTimeOut())
                .build();
    }

    public String getNamePrefix(TokenType type) {
        if(type == TokenType.A)
            return jwtProperties.getAccess().getNamePrefix();
        else if (type == TokenType.R)
            return jwtProperties.getRefresh().getNamePrefix();

        return null;
    }

    public boolean isValid(String token) {
        TokenUser user = decode(token);
        if(user == null) {
            return false;
        }

        try {
            if(user.getType().equals("A")) jwtVerifierA.verify(token);
            else jwtVerifierR.verify(token);
            return true;
        }catch (JWTVerificationException e) {
            log.error("JWT isValid error ::: ", e);
            return false;
        }
    }

    public TokenUser decode(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String type = jwt.getClaim(jwtProperties.getEncryptor().getTypeClaimKey()).asString();
            String id = jwt.getClaim(jwtProperties.getEncryptor().getIdClaimKey()).asString();
            return new TokenUser(type, id);
        }catch (JWTDecodeException e) {
            log.error("JWT decode error :: ", e);
            return null;
        }
    }

    public String createToken(TokenUser user) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + (user.getType().equals("A") ? jwtProperties.getAccess().getTimeOut() : jwtProperties.getRefresh().getTimeOut()) * 1000);

        return JWT.create()
                .withSubject(user.getType().equals("A") ? "access" : "refresh")
                .withClaim(jwtProperties.getEncryptor().getTypeClaimKey(), user.getType())
                .withClaim(jwtProperties.getEncryptor().getIdClaimKey(), user.getId())
                .withExpiresAt(expiresAt)
                .withIssuedAt(now)
                .sign(algorithm);
    }

}
