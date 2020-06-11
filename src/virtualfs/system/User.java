package virtualfs.system;

// classes
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.math.BigInteger;

// exceptions
import java.security.NoSuchAlgorithmException;
import java.lang.IllegalStateException;



public final class User {

	private String username__;
	private int uid__;
	private int gid__;
	private String home__;
	private String password__;

	public User(
		final String username,
		final int uid,
		final int gid,
		final String home,
		final String password
	) {
		username__ = username;
		uid__ = uid;
		gid__ = gid;
		home__ = home;
		password__ = password;
	}

	public String toString() {
		return String.join(
			":",
			username__,
			String.valueOf(uid__),
			String.valueOf(gid__),
			home__,
			password__
		);
	}

	public boolean auth(final String username, String password) {
		if (!username.equals(username__)) {
			return false;
		}

		password = User.hashString(password);
		if (!password.equals(password__)) {
			return false;
		}
		
		return true;
	}

	public String getName() {
		return username__;
	}

	public int getUid() {
		return uid__;
	}

	public int getGid() {
		return gid__;
	}

	public String getHome() {
		return home__;
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