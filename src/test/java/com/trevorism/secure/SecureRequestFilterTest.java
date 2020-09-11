package com.trevorism.secure;

import org.codehaus.jackson.jaxrs.TestBearerTokenContainerRequestContext;
import org.codehaus.jackson.jaxrs.TestContainerRequestContext;
import org.junit.Test;

import javax.ws.rs.container.ResourceInfo;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author tbrooks
 */
public class SecureRequestFilterTest {

    @Test
    public void testWithCorrectInjectedPassword() throws IOException {
        TestContainerRequestContext context = new TestContainerRequestContext("test");
        SecureRequestFilter filter = new SecureRequestFilter();
        filter.filter(context);

        assert !context.isAborted();
    }

    @Test
    public void testWithInvalidPassword() throws IOException {
        TestContainerRequestContext context = new TestContainerRequestContext("asdfsadf");
        SecureRequestFilter filter = new SecureRequestFilter();
        filter.filter(context);

        assert context.isAborted();
    }

    @Test
    public void testWithBearerToken() throws IOException {
        TestContainerRequestContext context = new TestBearerTokenContainerRequestContext();
        SecureRequestFilter filter = new SecureRequestFilter();
        filter.resourceInfo = new ResourceInfo() {
            @Override
            public Method getResourceMethod() {
                return SecuredMethod.class.getMethods()[0];
            }

            @Override
            public Class<?> getResourceClass() {
                return SecuredMethod.class;
            }
        };
        filter.filter(context);

        assert context.isAborted();
    }

    public class SecuredMethod{
        @Secure()
        public void doSomething(){

        }
    }
}