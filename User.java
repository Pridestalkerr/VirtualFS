import java.security.NoSuchAlgorithmException;
import java.lang.IllegalStateException;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.math.BigInteger;



public class User {

	private String username;
	private String password;

	public User(final String username, final String password) {
		this.username = username;
		this.password = hashString(password);
	}

	private String hashString(final String input) {
		try {
			byte[] hashed;

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hashed = digest.digest(input.getBytes(StandardCharsets.UTF_8));

			return String.format("%064x", new BigInteger(1, hashed));

		} catch (NoSuchAlgorithmException except) {
			//hardcoded to "SHA-256", will never throw
			throw new IllegalStateException();
		}
	}

	public String toString() {
		return String.join(":", username, password);
	}

}