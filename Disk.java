import java.io.File;

import java.io.IOException;
import java.lang.InterruptedException;



public class Disk {

	private File diskFile__;

	public Disk(final File diskFile) {
		diskFile__ = diskFile;
	}

	public boolean exists() {
		return diskFile__.exists() && !diskFile__.isDirectory();
	}

	public boolean checkIntegrity() { 
		//this should check that the file is actually formatted properly
		return true;//empty, for now
	}

	private void allocate(final String size) throws IOException, InterruptedException{
		try {
			Runtime.getRuntime().exec(new String[] {"fallocate", "-l", size, diskFile__.getAbsolutePath()}).waitFor();
		} catch (IOException | InterruptedException except) {
			throw except;
		}
	}

	private void mkfs(final String format) throws IOException, InterruptedException{
		try {
			Runtime.getRuntime().exec(new String[] {"mkfs.exfat", diskFile__.getAbsolutePath()}).waitFor();
		} catch (IOException | InterruptedException except) {
			throw except;
		}
	}

	public void create(final String format, final String size) throws IOException, InterruptedException {
		if (this.exists()) {
			throw new IOException("Passwd file already created.");
		}

		try {
			this.allocate(size);
			this.mkfs(format);
		} catch (IOException | InterruptedException except) {
			throw except;
		}
	}

	public void mount(final String mountpoint) throws IOException, InterruptedException{
		try {
			Runtime.getRuntime().exec(new String[] {"mount", diskFile__.getAbsolutePath(), mountpoint}).waitFor();
		} catch (IOException | InterruptedException except) {
			throw except;
		}
	}

}