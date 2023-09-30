package virtualfs.parser;
import virtualfs.system.User;

// classes
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.ArrayList;

// exceptions
import java.io.IOException;

// statics
import static java.lang.System.out;



public class Passwd {

	private Path path__;

	public Passwd(final Path path) {
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

		String[] lines;

		try (Stream <String> stream = Files.lines(path__)) {
			lines = stream.toArray(String[]::new);
		}
		return lines;
	}

	public void addUser(final User user) throws IOException {
		Files.write(path__, Collections.singleton(user.toString()),
			StandardOpenOption.CREATE,
			StandardOpenOption.WRITE,
			StandardOpenOption.APPEND
		);
	}

	public User[] parse() throws IOException {
		String[] lines = this.read();

		if (lines == null) {
			return null;
		}

		User[] users = new User[lines.length];

		for (int itr = 0; itr < lines.length; ++itr) {
			String[] config = lines[itr].split(":");
			users[itr] = new User(
				config[0], 
				Integer.parseInt(config[1]), 
				Integer.parseInt(config[2]), 
				config[3], 
				config[4]
			);
		}

		return users;
	}

}