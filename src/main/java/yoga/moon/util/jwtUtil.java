package yoga.moon.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {

    // Use a securely generated key (at least 256 bits for HS256)
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Configurable token expiration time (in milliseconds)
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60; // 1 hour

    // Generate a JWT token
    public String generateToken(String username, Map<String, Object> extraClaims) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Overloaded method for simple token generation without extra claims
    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    // Extract username from token
    public String extractUsername(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        return extractClaim(token, Claims::getSubject);
    }

    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) {
            return false;
        }
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Invalid token
        }
    }

    // Check if token is expired
    private boolean isExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Generic method to extract claims
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(stripBearerPrefix(token))
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Invalid or expired JWT token", e);
        }
    }

    // Remove "Bearer " prefix if present
    private String stripBearerPrefix(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
