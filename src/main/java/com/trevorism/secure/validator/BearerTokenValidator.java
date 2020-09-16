package com.trevorism.secure.validator;

import com.trevorism.secure.*;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

public class BearerTokenValidator implements AuthorizationValidator {

    public static final String BEARER_PREFIX = "bearer ";
    private String reason;
    private ClaimProperties claimProperties = new ClaimProperties();

    @Override
    public String getAuthorizationString(ContainerRequestContext requestContext) {
        List<String> list = requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (list == null || list.isEmpty()) {
            throw new AuthorizationStringInvalid("Unable to find authorization data");
        }
        String authorizationHeader = list.get(0);
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_PREFIX)) {
            throw new AuthorizationStringInvalid("Authorization header does not begin with 'bearer'");
        }

        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

    @Override
    public boolean validate(String authorizationString, Secure secure) {
        boolean invalid = true;
        try {
            claimProperties = ClaimsProvider.getClaims(authorizationString);
            validateClaims(secure);
            return true;
        } catch (Exception e) {
            reason = e.getMessage();
        }
        return false;
    }

    private void validateClaims(Secure secure) {
        validateInputs(secure);
        validateIssuer();
        validateRole(secure);

        if (secure.authorizeAudience()) {
            validateAudience();
        }
    }

    private void validateAudience() {
        String claimAudience = claimProperties.getAudience();
        PropertiesProvider propertiesProvider = new PropertiesProvider();
        String thisAudience = propertiesProvider.getProperty("clientId");

        if (!claimAudience.equals(thisAudience)) {
            throw new AuthorizationNotValid("Audience in claim does not match clientId in secrets.properties");
        }
    }

    private void validateRole(Secure secure) {
        String role = secure.value();
        String claimRole = claimProperties.getRole();
        if (claimRole == null) {
            throw new AuthorizationNotValid("Unable to parse claim role");
        }
        if (role.isEmpty()) {
            return;
        }
        if (role.equals(Roles.ADMIN)) {
            if (!claimRole.equals(Roles.ADMIN)) {
                throw new AuthorizationNotValid("Insufficient access");
            }
        }
        if (role.equals(Roles.SYSTEM)) {
            if (claimRole.equals(Roles.USER)) {
                throw new AuthorizationNotValid("Insufficient access");
            }
        }
    }

    private void validateIssuer() {
        if (!"https://trevorism.com".equals(claimProperties.getIssuer())) {
            throw new AuthorizationNotValid("Unexpected issuer: " + claimProperties.getIssuer());
        }
    }

    private void validateInputs(Secure secure) {
        if (claimProperties == null) {
            throw new AuthorizationNotValid("Unable to parse claim");
        }
        if (secure == null) {
            throw new AuthorizationNotValid("Unable to validate against a method without the @Secure annotation");
        }
    }

    @Override
    public String getValidationErrorReason() {
        return reason;
    }

    @Override
    public ClaimProperties getClaimProperties() {
        return claimProperties;
    }
}
