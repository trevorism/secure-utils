package com.trevorism.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class ClaimsProvider {

    public static ClaimProperties getClaims(String bearerToken) {
        Key decodedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(PasswordProvider.getInstance().getSigningKey()));
        Jws<Claims> parsedClaims = Jwts.parserBuilder()
                .setAllowedClockSkewSeconds(120)
                .setSigningKey(decodedKey)
                .build()
                .parseClaimsJws(bearerToken);

        ClaimProperties claimProperties = createClaimProperties(parsedClaims);
        return claimProperties;
    }

    private static ClaimProperties createClaimProperties(Jws<Claims> parsedClaims) {
        ClaimProperties claimProperties = new ClaimProperties();
        Claims claims = parsedClaims.getBody();
        claimProperties.setId(claims.get("dbId", String.class));
        claimProperties.setSubject(claims.getSubject());
        claimProperties.setAudience(claims.getAudience());
        claimProperties.setIssuer(claims.getIssuer());
        claimProperties.setRole(claims.get("role", String.class));
        claimProperties.setType(claims.get("entityType", String.class));
        return claimProperties;
    }
}
