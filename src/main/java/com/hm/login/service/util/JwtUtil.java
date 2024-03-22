package com.hm.login.service.util;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import com.hm.login.service.dto.AuthDTO;
import com.hm.login.service.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUtil {

	@Value("${app.public-key}")
	private String SECRET_KEY;

	/*
	 * security: oauth2: client: clientId: 340bdirectplus clientSecret: password
	 */

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;

	@Value("${Auth-credentials}")
	private String Auth_Credentials;
//	private String Auth_Credentials = clientId + ":" + clientSecret;

	@Value("${accessTokenUri}")
	private String AccessTokenUri;

	@Autowired
	private RestTemplate restTemplate;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public AuthDTO generateToken(UserDetails userDetails, String password) {
		return createToken(userDetails.getUsername(), password);
	}

	private AuthDTO createToken(String userName, String password) {
		String credentials = Auth_Credentials;
		String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);
		try {
			password = URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {

		}

		String access_token_url = AccessTokenUri;
		access_token_url += "?grant_type=password";
		access_token_url += "&username=" + userName;
		access_token_url += "&password=" + password;

		URI uri = URI.create(access_token_url);
		
		ResponseEntity<AuthDTO> response = null;
		log.info("Response : BEFORE >>>>"+response);
		try {
			log.info("Response : INSIDE BEFORE "+uri);
			response = restTemplate.exchange(uri, HttpMethod.POST, request, AuthDTO.class);
			log.info("Response INSIDE >>>:"+response);
		} catch (Exception e) {
			log.info("Response :"+e.getMessage());
			throw new CustomException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
//		ResponseEntity<AuthDTO> response = restTemplate.exchange(uri, HttpMethod.POST, request, AuthDTO.class);
		return response.getBody();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
