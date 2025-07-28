package com.kiwoom.app.system.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kiwoom.app.system.config.jwt.properties.JwtProperties;
import com.kiwoom.app.system.dto.TokenUser;
import com.kiwoom.app.system.type.TokenType;
import lombok.Data;

import java.util.Date;

@Data
public class JwtProvider {

    private JwtProperties jwtProperties;

    private Algorithm algorithm;

    private JWTVerifier jwtVerifierA;

    private JWTVerifier jwtVerifierR;

    public JwtProvider(JwtProperties jwtProperties) {
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
