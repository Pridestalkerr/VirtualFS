package virtualfs.system;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;



public final class Info {

	public String hostname;
	public Path main;
	public Path[] secondary;

	public
	Info(final String hostname, final Path main, final Path[] secondary) {
		this.hostname = hostname;
		this.main = main;
		this.secondary = secondary.clone();
	}

	@Override
	public String toString() {
		StringBuilder repr = new StringBuilder();

		repr.append(hostname);
		repr.append(';');
		repr.append(main.toString());
		repr.append(':');
		repr.append(
			String.join(
				",", 
				Arrays.stream(secondary).map(
					path -> path.toString()
				).toArray(String[]::new)
			)
		);

		return repr.toString();
	}

	// @Override
	// public int hashCode() {
	// 	return this.hostname.hashCode();
	// }

	// @Override
	// public int equals(final Info other) {
	// 	return this.hostname == other.hostname;
	// }
	
}