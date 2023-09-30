package virtualfs.system;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException;



public class File {

	private Path path__;

	public File(final String path) {
		this.path__ = Paths.get(path).toAbsolutePath();
	}

	public boolean exists() {
		return Files.exists(this.path__) && !Files.isDirectory(path__);
	}

	public boolean create() throws IOException {
		if (this.exists()) {
			return false;
		}

		Files.createFile(path__);

		return true;
	}

	public void seek(final int offset, final int whence) {

	}

	public byte[] read(final int size) {
		return null;
	}

	public void write(final byte[] buf) {

	}
	
}