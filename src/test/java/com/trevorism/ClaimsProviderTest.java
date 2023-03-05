package com.trevorism;

import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClaimsProviderTest {

    @Test(expected = WeakKeyException.class)
    public void getClaimsWithWeakKey() {
        ClaimsProvider.getClaims("ey.x.y", "signingKey");
    }

    @Test(expected = SignatureException.class)
    public void getClaimsWithUnsignedBearerToken() {
        ClaimsProvider.getClaims("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlRyZXZvcmlzbSIsImlhdCI6MTUxNjIzOTAyMn0.Wjh2NZsdP3Dw-FZ3dkzend8BFhkyMEz1pkC1Y6lzO2__QnLW8SiSwIVbad-4z4M6IKzA8Bs8LOxw8pOjLUKr-w", "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void getClaims() {
        //jwt.io can generate a bearer token with the signing key here.
        ClaimProperties claimProperties = ClaimsProvider.getClaims("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlRyZXZvcmlzbSIsImlhdCI6MTUxNjIzOTAyMn0.s0_anafHIi-y1IzRc84b4QcNA8DZFbrzHRz4Cn-n4hPFeUf3_P88he3Uz-8-5MRphgCW3aKEdDg4J6_7x0oMOw", "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz");
        assertEquals("1234567890", claimProperties.getSubject());
    }
}