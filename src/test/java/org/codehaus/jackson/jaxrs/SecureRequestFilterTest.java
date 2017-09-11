package org.codehaus.jackson.jaxrs;

import org.junit.Test;

import java.io.IOException;

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
}