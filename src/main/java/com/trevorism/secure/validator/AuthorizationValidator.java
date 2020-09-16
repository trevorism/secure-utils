package com.trevorism.secure.validator;

import com.trevorism.secure.ClaimProperties;
import com.trevorism.secure.Secure;

import javax.ws.rs.container.ContainerRequestContext;

public interface AuthorizationValidator {

    String getAuthorizationString(ContainerRequestContext requestContext);
    boolean validate(String authorizationString, Secure secure);
    String getValidationErrorReason();
    ClaimProperties getClaimProperties();
}
