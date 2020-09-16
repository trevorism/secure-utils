package com.trevorism.secure.validator;

import com.trevorism.secure.PasswordProvider;
import com.trevorism.secure.Secure;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

public class PasswordValidator implements AuthorizationValidator {

    private String reason;

    @Override
    public String getAuthorizationString(ContainerRequestContext requestContext) {
        List<String> list = requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (list == null || list.isEmpty()) {
            throw new AuthorizationStringInvalid("Unable to find authorization data");
        }
        return list.get(0);
    }

    @Override
    public boolean validate(ContainerRequestContext requestContext, Secure secure) {
        try {
            String authorizationString = getAuthorizationString(requestContext);
            if (authorizationString.equals(PasswordProvider.getInstance().getPassword())) {
                return true;
            }
            reason = "Authorization string " + authorizationString + " does not match the password";
        } catch (AuthorizationStringInvalid ex) {
            reason = ex.getMessage();
        }
        return false;
    }

    @Override
    public String getValidationErrorReason() {
        return reason;
    }

}
