package com.trevorism.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author tbrooks
 */
public class SecureRequestFilter implements ContainerRequestFilter {

    private PasswordProvider passwordProvider = new PasswordProvider();
    private static final Logger log = Logger.getLogger(SecureRequestFilter.class.getName());

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> list = requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (headerIsInvalid(list)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean headerIsInvalid(List<String> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }

        String authorizationHeader = list.get(0);

        if (authorizationHeader.equals(passwordProvider.getPassword())) {
            return false;
        }

        if (!authorizationHeader.toLowerCase().startsWith("bearer ")) {
            return true;
        }

        String bearerToken = authorizationHeader.substring(7);

        return isBearerTokenInvalid(bearerToken);
    }

    private boolean isBearerTokenInvalid(String bearerToken) {
        boolean invalid = true;
        try {
            Key decodedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(passwordProvider.getSigningKey()));
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setAllowedClockSkewSeconds(120)
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(bearerToken);

            invalid = areClaimsInvalid(claims);
            if (!invalid) {
                log.info("Successfully validated bearer token from " + claims.getBody().getSubject());
            }
        } catch (Exception e) {
            log.warning("Exception parsing bearer token " + e.getMessage());
        }
        return invalid;
    }

    private boolean areClaimsInvalid(Jws<Claims> claims) {
        Secure secure = resourceInfo.getResourceMethod().getAnnotation(Secure.class);

        String role = secure.value();
        if (!claims.getBody().getIssuer().equals("https://trevorism.com")) {
            return true;
        }

        String claimRole = claims.getBody().get("role", String.class);
        if (role.equals(Roles.ADMIN)) {
            return !claimRole.equals(Roles.ADMIN);
        }

        if (role.equals(Roles.SYSTEM) && claimRole.equals(Roles.USER)) {
            return true;
        }

        return false;
    }

}
