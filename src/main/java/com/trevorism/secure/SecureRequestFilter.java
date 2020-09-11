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

/**
 * @author tbrooks
 */
public class SecureRequestFilter implements ContainerRequestFilter {

    private PasswordProvider passwordProvider = new PasswordProvider();

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
        try {
            Key decodedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(passwordProvider.getSigningKey()));
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setAllowedClockSkewSeconds(120)
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(bearerToken);

            return areClaimsInvalid(claims);
        } catch (Exception ignored) {
            System.out.println(ignored);
        }
        return true;
    }

    private boolean areClaimsInvalid(Jws<Claims> claims) {
        String role = resourceInfo.getResourceMethod().getAnnotation(Secure.class).value();

        if (!claims.getBody().getIssuer().equals("https://trevorism.com")) {
            return true;
        }

        if (Roles.validate(role) && claimsDoesNotHaveRole(claims, role)) {
            return true;
        }

        return false;
    }

    private boolean claimsDoesNotHaveRole(Jws<Claims> claims, String role) {
        String claimRole = claims.getBody().get("role", String.class);

        if (!Roles.validate(claimRole)) {
            return true;
        }

        if (claimRole.equals(Roles.GLOBAL_ADMIN) || claimRole.equals(Roles.TENANT_ADMIN))
            return false;

        return !claimRole.equals(role);
    }


}
