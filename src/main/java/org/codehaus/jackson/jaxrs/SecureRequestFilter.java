package org.codehaus.jackson.jaxrs;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author tbrooks
 */
public class SecureRequestFilter implements ContainerRequestFilter {

    public static final String SECURE_FILE_NAME = "secure.txt";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        InputStream secure = SecureRequestFilter.class.getClassLoader().getResourceAsStream(SECURE_FILE_NAME);
        if (secure == null) {
            requestContext.abortWith(Response.status(javax.ws.rs.core.Response.Status.UNAUTHORIZED).build());
            return;
        }
        try {
            validateRequestHeaderContainsAuthorization(requestContext, secure);
        } finally {
            secure.close();
        }
    }

    private void validateRequestHeaderContainsAuthorization(ContainerRequestContext requestContext, InputStream secure) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(secure));
        String password = reader.readLine();
        List<String> list = requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaderDoesNotMatchSecurePassword(list, password)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean authorizationHeaderDoesNotMatchSecurePassword(List<String> list, String password) {
        return list == null || list.isEmpty() || !list.get(0).equals(password);
    }
}
