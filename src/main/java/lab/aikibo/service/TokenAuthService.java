package lab.aikibo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lab.aikibo.auth.TokenUser;
import lab.aikibo.auth.UserAuthentication;
import lab.aikibo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by tamami on 04/02/17.
 */
@Service
public class TokenAuthService {

    private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000; // 10 days
    private static final String AUTH_HEADER_NAME = "x-auth-token";

    @Value("${token.secret}")
    private String secret;

    public String addAuthentication(HttpServletResponse response, TokenUser tokenUser) {
        String token = createTokenForUser(tokenUser.getUser());
        response.addHeader(AUTH_HEADER_NAME, token);
        return token;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if(token != null && !token.isEmpty()) {
            final TokenUser tokenUser = parseUserFromToken(token);
            if(tokenUser != null) {
                return new UserAuthentication(tokenUser);
            }
        }
        return null;
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
            .setSubject(toJSON(user))
            .signWith(SignatureAlgorithm.HS512,secret)
            .compact();
    }

    public TokenUser parseUserFromToken(String token) {
        String userJSON = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        return new TokenUser(fromJSON(userJSON));
    }

    private String toJSON(User user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch(JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private User fromJSON(final String userJSON) {
        try {
            return new ObjectMapper().readValue(userJSON, User.class);
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
