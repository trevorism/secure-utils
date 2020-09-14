package com.trevorism.secure;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author tbrooks
 */
public class SecureRequestFilter implements ContainerRequestFilter {

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

        if (authorizationHeader.equals(PasswordProvider.getInstance().getPassword())) {
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
            ClaimProperties claimProperties = ClaimsProvider.getClaims(bearerToken);
            invalid = areClaimsInvalid(claimProperties);
            if (!invalid) {
                log.info("Successfully validated bearer token from " + claimProperties.getSubject());
            }
        } catch (Exception e) {
            log.warning("Exception parsing bearer token " + e.getMessage());
        }
        return invalid;
    }

    private boolean areClaimsInvalid(ClaimProperties claims) {
        Secure secure = resourceInfo.getResourceMethod().getAnnotation(Secure.class);

        if (!claims.getIssuer().equals("https://trevorism.com")) {
            return true;
        }

        String role = secure.value();
        String claimRole = claims.getRole();
        if (claimRole == null) {
            return true;
        }
        if (role.isEmpty()) {
            return false;
        }

        if (role.equals(Roles.ADMIN)) {
            return !claimRole.equals(Roles.ADMIN);
        }

        //TODO: Parse secrets.properties, get clientId, validate audience based on Secure

        return role.equals(Roles.SYSTEM) && claimRole.equals(Roles.USER);

    }

}
