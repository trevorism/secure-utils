package com.trevorism.secure.validator;

import com.trevorism.secure.ClaimProperties;
import com.trevorism.secure.Roles;
import com.trevorism.secure.Secure;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

public class BearerTokenValidatorTest {

    @Test
    public void validateSecureAtUserLevel() {
        BearerTokenValidator bearerTokenValidator = new BearerTokenValidator();
        ClaimProperties claimProperties = createUserClaimWithRole(Roles.USER);
        bearerTokenValidator.setClaimProperties(claimProperties);

        try {
            bearerTokenValidator.validateClaims(createSecureInstance("", false));
            bearerTokenValidator.validateClaims(createSecureInstance(Roles.USER, false));
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validateSecureAtAdminLevel() {
        BearerTokenValidator bearerTokenValidator = new BearerTokenValidator();
        ClaimProperties claimProperties = createUserClaimWithRole(Roles.ADMIN);
        bearerTokenValidator.setClaimProperties(claimProperties);

        try {
            bearerTokenValidator.validateClaims(createSecureInstance(Roles.ADMIN, false));
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validateSecureAtAdminLevelWithUserClaimsFails() {
        BearerTokenValidator bearerTokenValidator = new BearerTokenValidator();
        ClaimProperties claimProperties = createUserClaimWithRole(Roles.USER);
        bearerTokenValidator.setClaimProperties(claimProperties);

        try {
            bearerTokenValidator.validateClaims(createSecureInstance(Roles.ADMIN, false));
            fail();
        } catch (Exception e) {
            assertEquals("Insufficient access", e.getMessage());
        }
    }

    @Test
    public void validateSecureAtSystemLevelWithUserClaimsFails() {
        BearerTokenValidator bearerTokenValidator = new BearerTokenValidator();
        ClaimProperties claimProperties = createUserClaimWithRole(Roles.USER);
        bearerTokenValidator.setClaimProperties(claimProperties);

        try {
            bearerTokenValidator.validateClaims(createSecureInstance(Roles.SYSTEM, false));
            fail();
        } catch (Exception e) {
            assertEquals("Insufficient access", e.getMessage());
        }
    }

    @Test
    public void validateSecureWithAuthorizeAudienceFails() {
        BearerTokenValidator bearerTokenValidator = new BearerTokenValidator();
        ClaimProperties claimProperties = createUserClaimWithRole(Roles.SYSTEM);
        bearerTokenValidator.setClaimProperties(claimProperties);

        try {
            bearerTokenValidator.validateClaims(createSecureInstance(Roles.USER, true));
            fail();
        } catch (Exception e) {
            assertEquals("Audience in claim does not match clientId in secrets.properties", e.getMessage());
        }
    }

    private ClaimProperties createUserClaimWithRole(String role) {
        ClaimProperties claimProperties = new ClaimProperties();
        claimProperties.setSubject("username");
        claimProperties.setType("user");
        claimProperties.setRole(role);
        claimProperties.setIssuer("https://trevorism.com");
        claimProperties.setId("7475634562");
        return claimProperties;
    }

    private Secure createSecureInstance(String role, boolean authorizeAudience) {
        return new Secure() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Secure.class;
            }

            @Override
            public String value() {
                return role;
            }

            @Override
            public boolean authorizeAudience() {
                return authorizeAudience;
            }
        };
    }
}