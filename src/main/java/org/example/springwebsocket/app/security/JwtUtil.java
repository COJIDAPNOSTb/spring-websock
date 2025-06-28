package org.example.springwebsocket.app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.springwebsocket.app.repository.TokenRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final String secret = "superSecretKeyqw4teyrtyuljhgnfdsfdeqwrretryy";
    private final long expirationMs = 86400000;
    private final TokenRepository tokenRepository;
    
    public JwtUtil(TokenRepository tokenRepository) {
    	this.tokenRepository = tokenRepository;
    }
    
    private SecretKey getSigningKey() {
    	byte[] keyBytes = Decoders.BASE64URL.decode( secret );
    	return Keys.hmacShaKeyFor( keyBytes );
    }
    
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
    	
    	JwtParserBuilder parser = Jwts.parserBuilder();
    	parser.setSigningKey( getSigningKey() );
        return parser.build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
        	JwtParserBuilder parser = Jwts.parserBuilder();
        	parser.setSigningKey( getSigningKey() );
            parser.build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
    
    private Date extractExpiration(String token) {
    	JwtParserBuilder parser = Jwts.parserBuilder();
    	parser.setSigningKey( getSigningKey() );
        return parser.build().parseClaimsJws(token).getBody().getExpiration();
	}
    
    private boolean isAccessTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }
	
	public boolean isValid(String token, UserDetails user) {
		
		String username = extractUsername( token );
		boolean isValidToken = tokenRepository.findByAccessToken( token )
				.map(t -> !t.isLoggedOut()).orElse( false );
		System.out.println( isValidToken );
		return username.equals(user.getUsername())
				&& isAccessTokenExpired( token )
				&& isValidToken;
	}

}
