package com.pi.spring_security.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "32b0e1ae9d7067abedcbad6bdd0f43b3df26e24f4af74c962c8cd685e78f68f64135c435bfb9d1e13d68bd5c83f9731cfe5f97742f51fcd194f68c2ce95411469f7d26fb5af0c1de7f852385272e0ab1ab510fa22e77c9c51da3958d2fe8e94976b035063b23b5b545e0581200cc12c59aaeeabc433ad1a6dfb1615bb2ff5b5002e3da1087d3e9c1d4d0daaec791847d99cb6f9e5de5fc75dde4f0e3686fbf18adddaf083c7ec8c5b64d7107170b3c07c8644b75974e2ae3e4b2d76b63f6b0c1484e3a8452ef0b872dbaadc0bf137f73ab545dc7251115b30576b2fcd5b4a831bb516418642a943ad68ad362f1902e600a0f386f2e68e7b1e85f6bd2f60a9242";
	

	public String getUsernameFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build().parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails ) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
		
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
