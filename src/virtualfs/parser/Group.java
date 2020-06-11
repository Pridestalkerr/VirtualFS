// package virtualfs.parser;

// // classes
// import java.nio.file.Paths;
// import java.nio.file.Path;
// import java.nio.file.Files;
// import java.nio.file.StandardOpenOption;

// import java.util.stream.Stream;

// import java.util.Collections;
// import java.util.ArrayList;

// // exceptions
// import java.io.IOException;

// // statics
// import static java.lang.System.out;




// public class Passwd {

// 	private Path path__;

// 	public Passwd(final String path) {
// 		this.path__ = Paths.get(path).toAbsolutePath();
// 	}

// 	public boolean exists() throws IOException {
// 		return Files.exists(this.path__) && !Files.isDirectory(path__);
// 	}

// 	public boolean create() throws IOException {
// 		if (this.exists()) {
// 			return false;
// 		}

// 		Files.createFile(path__);

// 		return true;
// 	}

// 	private String[] read() throws IOException {
// 		if (!this.exists()) {
// 			return null;
// 		}

// 		return Files.lines(path__).toArray(String[]::new);
// 	}

// 	public void addGroup(final User user) throws IOException {
// 		Files.write(path__, Collections.singleton(user.toString()),
// 			StandardOpenOption.CREATE,
// 			StandardOpenOption.WRITE,
// 			StandardOpenOption.APPEND
// 		);
// 	}

// 	public User[] parse() throws IOException {
// 		String[] lines = this.read();

// 		if (lines == null) {
// 			return null;
// 		}

// 		User[] users = new User[lines.length];

// 		for (int itr = 0; itr < lines.length; ++itr) {
// 			String[] config = lines[itr].split(":");
// 			users[itr] = new User(config[0], config[1], config[2], config[3], config[4]);
// 		}

// 		return users;
// 	}

// }