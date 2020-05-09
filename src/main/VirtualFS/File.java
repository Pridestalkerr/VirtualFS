package virtualfs;

import java.nio.file.Path;



public class File {

	private Path path__;

	public File(final String path) {
		this.path__ = Paths.get(path).toAbsolutePath();
	}

	public boolean exists() {
		return Files.exists(this.path__) && !Files.isDirectory(path__);
	}

	public boolean create() {
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