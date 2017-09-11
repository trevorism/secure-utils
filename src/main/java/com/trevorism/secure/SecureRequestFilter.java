package com.trevorism.secure;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * @author tbrooks
 */
public class SecureRequestFilter implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String password = PasswordProvider.PASSWORD;
        List<String> list = requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaderDoesNotMatchSecurePassword(list, password)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean authorizationHeaderDoesNotMatchSecurePassword(List<String> list, String password) {
        return list == null || list.isEmpty() || !list.get(0).equals(password);
    }
}
