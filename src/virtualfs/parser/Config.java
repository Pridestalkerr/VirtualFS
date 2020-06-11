package virtualfs.parser;
import virtualfs.system.System;
import virtualfs.system.Info;

// classes
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;

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

	public void addSystem(final Info system) throws IOException {
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
			// hostname;main0:sda0,sda1
			String[] line = lines[itr].split(";");
			String hostname = line[0];
			String[] config = line[1].split(":");
			Path main = Paths.get(config[0]);
			Path[] secondaries = Arrays.stream(config[1].split(",")).map(
				string -> Paths.get(string)
			).toArray(Path[]::new);

			// configs[itr] = Stream.of(
			// 	new String[] {config[0]}, config[1].split(",")
			// ).flatMap(Stream::of).toArray(String[]::new);

			configs[itr] = new Info(hostname, main, secondaries);
		}

		return configs;
	}

}