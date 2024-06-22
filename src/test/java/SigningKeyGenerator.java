import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

import java.security.Key;

public class SigningKeyGenerator {

    public static void main(String[] args) {
        Key key = Jwts.SIG.HS512.key().build();
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
    }
}
