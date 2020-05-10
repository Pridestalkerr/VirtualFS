import java.security.NoSuchAlgorithmException;
import java.lang.IllegalStateException;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.math.BigInteger;



public class User {

	private String username__;
	private int uid__;
	private int gid__;
	private String home__;
	private String password__;

	public User(final String username, final int uid, final int gid, final String home, final String password) {
		username__ = username;
		uid__ = uid;
		gid__ = gid;
		home__ = home;
		password__ = password;
	}

	public String toString() {
		return String.join(":", username__, uid__, gid__, home__, password__);
	}

	public boolean compareHash(final String hash) {
		if (hash.equals(password))
			return true;
		else
			return false;
	}

	public static String hashString(final String input) {
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

}