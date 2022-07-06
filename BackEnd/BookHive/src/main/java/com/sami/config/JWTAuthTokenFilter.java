package com.sami.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JWTAuthTokenFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req  = (HttpServletRequest)request; //Sent request JWT token though Httpheader
		HttpServletResponse rep  = (HttpServletResponse)response;
		String headerToken = req.getHeader("Authorization");
		try {
		if(req.getMethod().equals("OPTIONS")) {
			rep.setStatus(HttpStatus.OK.value());
			chain.doFilter(request, response);
			
		}else if (headerToken.contains("null") ||  headerToken == null || !headerToken.startsWith("Bearer ")) {
			//rep.sendRedirect("/unauthorized");
			rep.setHeader("Access-Control-Allow-Origin", "*");
			rep.sendRedirect("/admin/unauthorized");
			return;
		}
		String jwtToken = headerToken.split(" ")[1];
		//parseClaimsJwt -- Claims (payload - entity) 
		//parseClaimJws -- signature 
		Claims claims = Jwts.parser().setSigningKey("bookHive").parseClaimsJws(jwtToken).getBody();
		req.setAttribute("claim", claims);
		chain.doFilter(request, response);
		}catch (Exception e) {
			rep.setHeader("Access-Control-Allow-Origin", "*");
			if(headerToken != null) {
				rep.sendRedirect("/admin/unauthorized");
			}
		}		
		
	}
	

}
