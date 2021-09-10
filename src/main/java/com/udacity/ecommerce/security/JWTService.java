package com.udacity.ecommerce.security;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JWTService {

    /**
     * Adds JWT to the response header
     *
     * @param res      Response
     * @param username Username
     */
    public void addAuthentication(HttpServletResponse res, String username) {
        String jwt = createToken(username);
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + " " + jwt);
    }

    /**
     * Create a signed JWT with Subject to the payload
     *
     * @param subject e.g. Username for authentication
     * @return JWT
     */
    public String createToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
    }

    /**
     * Verifies JWT based on a configured secret key for User Authentication
     *
     * @param token JWT
     * @return UsernamePasswordAuthentication
     */
    public Authentication getAuthentication(String token) {
        String user = JWT.require(HMAC512(SecurityConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getSubject();
        return user != null ? new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()) : null;
    }
}
