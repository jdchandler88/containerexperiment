package util.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {

	public static String issueToken(String username, String[] roles, int expiresInSeconds) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		Date now = new Date();
		Calendar expiresInCalendar = Calendar.getInstance();
		expiresInCalendar.setTime(now);
		expiresInCalendar.add(Calendar.SECOND, expiresInSeconds);
		Date expiresIn = expiresInCalendar.getTime();
	    return JWT.create()
	    	.withSubject(username)
	    	.withArrayClaim("roles", roles)
	        .withIssuer("app")
	        .withNotBefore(now)
	        .withExpiresAt(expiresIn)
	        .sign(algorithm);
	}
	
	public static void verifyToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("app")
            .acceptLeeway(1)
            .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
	}
	
}
