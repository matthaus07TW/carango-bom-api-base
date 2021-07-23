package br.com.caelum.carangobom.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${carangobom.jwt.expiration}")
	private String expiration;
	
	@Value("${carangobom.jwt.signingKey}")
	private String signingKey;

	public String generateToken(Authentication authentication) {
		User loggedInUser = (User) authentication.getPrincipal();
		Date now = new Date();
		Date expirationTime = new Date(now.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API Carango Bom")
				.setSubject(loggedInUser.getId().toString())
				.setIssuedAt(now)
				.setExpiration(expirationTime)
				.signWith(SignatureAlgorithm.HS256, signingKey)
				.compact();
	}
	
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.signingKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.signingKey).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
