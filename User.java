import java.security.NoSuchAlgorithmException;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.math.BigInteger;



public class User {

	private String username;
	private String password;

	public User(String username, String password) throws NoSuchAlgorithmException{
		try {
			this.username = username;
			this.password = hashString(password);
		} catch (NoSuchAlgorithmException except) {
			throw except;
		}
	}

	private String hashString(final String input) throws NoSuchAlgorithmException{
		try {
			byte[] hashed;

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hashed = digest.digest(input.getBytes(StandardCharsets.UTF_8));

			return String.format("%064x", new BigInteger(1, hashed));

		} catch (NoSuchAlgorithmException except) {
			throw except;
		}
	}

	public String toString() {
		return String.join(":", username, password);
	}
}