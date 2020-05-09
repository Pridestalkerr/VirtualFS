package virtualfs.system;

import java.nio.file.Paths;
import java.nio.file.Path;

import java.util.ArrayList;



public class Info {

	public String hostname;
	public String main;
	public String[] secondary;

	public Info(final String hostname, final String main, final String[] secondary) {
		this.hostname = hostname;
		this.main = main;
		this.secondary = secondary.clone();
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