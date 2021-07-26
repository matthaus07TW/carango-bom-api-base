package br.com.caelum.carangobom.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.caelum.carangobom.model.User;
import br.com.caelum.carangobom.repository.UserRepository;



public class AuthenticationByTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UserRepository repository;

	public AuthenticationByTokenFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = retrieveToken(request);
		boolean isValidToken = tokenService.isTokenValid(token);
		if (isValidToken) {
			authenticateClient(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateClient(String token) {
		Long userId = tokenService.getUserId(token);
		Optional<User> user = repository.findById(userId);
		
		if (user.isPresent()) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
