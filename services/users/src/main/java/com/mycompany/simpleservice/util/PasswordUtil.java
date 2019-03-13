package com.mycompany.simpleservice.util;

import java.security.SecureRandom;
import java.util.Random;

import org.bouncycastle.crypto.generators.BCrypt;

public class PasswordUtil {

	private static final Random SALT_GENERATOR = new SecureRandom();
	
	public static String generateSalt() {
		byte[] saltBytes = new byte[16];
		SALT_GENERATOR.nextBytes(saltBytes);
		return new String(saltBytes);
	}
	
	public static String hash(String password, String salt) {
		byte[] hash = BCrypt.generate(password.getBytes(), salt.getBytes(), 4);
		return new String(hash);
	}
	
}
