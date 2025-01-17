package com.royalaviation.SpamCaller.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
String requestPath = request.getServletPath();

// Add public endpoints to bypass JWT authentication
if (isPublicEndpoint(requestPath)) {
    filterChain.doFilter(request, response);
    return;
}

// Extract Authorization header
final String authorizationHeader = request.getHeader("Authorization");
String username = null;
String jwt = null;

if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
    jwt = authorizationHeader.substring(7);
    username = jwtUtil.extractEmail(jwt);
}

if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    if (jwtUtil.isTokenValid(jwt, username)) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or expired token. Kindly log in again.");
        return;
    }
} else if (username == null) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing or invalid Authorization header.");
    return;
}

filterChain.doFilter(request, response);
}

// Utility method to check if the request path is for a public endpoint
private boolean isPublicEndpoint(String requestPath) {
return requestPath.equals("/login") || requestPath.equals("/register") || 
       requestPath.startsWith("/swagger-ui") || requestPath.startsWith("/v3/api-docs");
}

}
