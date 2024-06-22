package com.trevorism;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class ClaimsProvider {

    public static final String DB_ID = "dbId";
    public static final String ROLE = "role";
    public static final String ENTITY_TYPE = "entityType";
    public static final String TENANT = "tenant";
    public static final String PERMISSIONS = "permissions";

    public static ClaimProperties getClaims(String bearerToken, String signingKey) {
        SecretKey decodedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKey));
        Jws<Claims> parsedClaims = Jwts.parser()
                .clockSkewSeconds(120)
                .verifyWith(decodedKey)
                .build()
                .parseSignedClaims(bearerToken);

        return createClaimProperties(parsedClaims);
    }

    private static ClaimProperties createClaimProperties(Jws<Claims> parsedClaims) {
        ClaimProperties claimProperties = new ClaimProperties();
        Claims claims = parsedClaims.getPayload();
        claimProperties.setSubject(claims.getSubject());
        claimProperties.setAudience(claims.getAudience());
        claimProperties.setIssuer(claims.getIssuer());
        claimProperties.setId(claims.get(DB_ID, String.class));
        claimProperties.setPermissions(claims.get(PERMISSIONS, String.class));
        claimProperties.setRole(claims.get(ROLE, String.class));
        claimProperties.setType(claims.get(ENTITY_TYPE, String.class));
        claimProperties.setTenant(claims.get(TENANT, String.class));
        return claimProperties;
    }
}
