package com.royalaviation.SpamCaller.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate token with 1 hour expiration time
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extract email from the token
    public String extractEmail(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired.");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid token format.");
        } catch (Exception e) {
            System.out.println("Error extracting email from token.");
        }
        return null;
    }

    // Check if the token is valid
    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            // Validate the username in the token
            if (claims.getSubject().equals(username)) {
                // Check if the token is expired
                return !claims.getExpiration().before(new Date());
            }
        } catch (ExpiredJwtException e) {
            // Handle expired token
            System.out.println("Token has expired.");
        } catch (MalformedJwtException e) {
            // Handle malformed token
            System.out.println("Invalid token format.");
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.out.println("Token validation error.");
        }
        return false;
    }

    // Method to get the expiration date of a token
    public Date getExpirationDate(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getExpiration();
        } catch (Exception e) {
            System.out.println("Error extracting expiration date from token.");
        }
        return null;
    }
}
