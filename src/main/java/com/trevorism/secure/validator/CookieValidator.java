package com.trevorism.secure.validator;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;

public class CookieValidator extends BearerTokenValidator implements AuthorizationValidator {

    @Override
    public String getAuthorizationString(ContainerRequestContext requestContext) {
        Cookie cookie = requestContext.getCookies().get("session");
        if(cookie == null){
            throw new AuthorizationStringInvalid("Unable to read cookie 'session'");
        }
        return cookie.getValue();
    }

}
