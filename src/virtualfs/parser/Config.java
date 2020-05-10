package virtualfs.parser;
import virtualfs.system.System;
import virtualfs.system.Info;

// classes
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

// exceptions
import java.io.IOException;

// statics
import static java.lang.System.out;




public class Config {

	private Path path__;

	public Config(final Path path) {
		path__ = path;
	}

	public boolean exists() throws IOException {
		return Files.exists(this.path__) && !Files.isDirectory(path__);
	}

	public boolean create() throws IOException {
		if (this.exists()) {
			return false;
		}

		Files.createFile(path__);

		return true;
	}

	private String[] read() throws IOException {
		if (!this.exists()) {
			return null;
		}

		return Files.lines(path__).toArray(String[]::new);
	}

	public void addSystem(final System system) throws IOException {
		Files.write(path__, Collections.singleton(system.toString()),
			StandardOpenOption.CREATE,
			StandardOpenOption.WRITE,
			StandardOpenOption.APPEND
		);
	}

	public Info[] parse() throws IOException {
		String[] lines = this.read();

		if (lines == null) {
			return null;
		}

		Info[] configs = new Info[lines.length];

		for (int itr = 0; itr < lines.length; ++itr) {
			// main0:sda0,sda1
			String[] config = lines[itr].split(":");
			String main = config[0];
			String[] secondaries = config[1].split(",");

			// configs[itr] = Stream.of(
			// 	new String[] {config[0]}, config[1].split(",")
			// ).flatMap(Stream::of).toArray(String[]::new);

			configs[itr] = new Info(main, main, secondaries);
		}

		return configs;
	}

}