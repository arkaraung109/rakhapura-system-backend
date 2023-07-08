package com.pearlyadana.rakhapuraapp.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        try {
			if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
			    Authentication authentication = this.tokenProvider.getAuthentication(jwt);
			    SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} 
        catch (ExpiredJwtException e) {
        	e.printStackTrace();
        	
        	Map<String, Object> body = new HashMap<String, Object>();
    		body.put("timestamp", new Date());
    		body.put("status", HttpStatus.UNAUTHORIZED.value());
    		body.put("error", "Token Expired");
    		body.put("message", "Token Expired");
    		body.put("path", ((HttpServletRequest) servletRequest).getRequestURI());
    		
    		HttpServletResponse response = (HttpServletResponse) servletResponse;
    		response.setStatus(HttpStatus.UNAUTHORIZED.value());
    		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    		
    		try {
    			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
    			}.getType(), response.getWriter());
    		} catch (JsonIOException ex) {
    			// TODO Auto-generated catch block
    			ex.printStackTrace();
    		} catch (IOException ex) {
    			// TODO Auto-generated catch block
    			ex.printStackTrace();
    		}
        	
        }
        catch (Exception e) {
			e.printStackTrace();
		}
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
