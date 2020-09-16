package com.trevorism.secure;

import com.trevorism.secure.validator.AuthorizationValidator;
import com.trevorism.secure.validator.BearerTokenValidator;
import com.trevorism.secure.validator.CookieValidator;
import com.trevorism.secure.validator.PasswordValidator;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author tbrooks
 */
public class SecureRequestFilter implements ContainerRequestFilter {

    private static final Logger log = Logger.getLogger(SecureRequestFilter.class.getName());

    private final AuthorizationValidator passwordValidator = new PasswordValidator();
    private final AuthorizationValidator bearerTokenValidator = new BearerTokenValidator();
    private final AuthorizationValidator cookieValidator = new CookieValidator();

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Secure secure = null;
        if (resourceInfo != null) {
            secure = resourceInfo.getResourceMethod().getAnnotation(Secure.class);
        }

        if (passwordValidator.validate(requestContext, secure) ||
                bearerTokenValidator.validate(requestContext, secure) ||
                cookieValidator.validate(requestContext, secure)) {
            return;
        }

        log.fine("Authentication for secure endpoint failed");
        log.finer(passwordValidator.getValidationErrorReason());
        log.finer(bearerTokenValidator.getValidationErrorReason());
        log.finer(cookieValidator.getValidationErrorReason());

        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
