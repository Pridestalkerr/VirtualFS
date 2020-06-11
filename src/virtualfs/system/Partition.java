package virtualfs.system;

// classes
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

// exceptions
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// statics
import static java.lang.System.out;



public class Partition {

	private Path path__;
	private String tag__;
	private Path mountpoint__;

	public Partition(final Path path, final String tag) {
		path__ = path;
		mountpoint__ = null;
		tag__ = tag;
	}

	public boolean exists() {
		return Files.exists(path__) && !Files.isDirectory(path__);
	}

	public boolean checkIntegrity() {
		return true;
	} 

	private void 
	allocate(final String size) throws IOException, InterruptedException{
		Process p = Runtime.getRuntime().exec(
			new String[] {"fallocate", "-l", size, path__.toString()}
		);

		p.waitFor();
		BufferedReader br = new BufferedReader(
			new InputStreamReader(p.getInputStream())
		);
		String line;

		while ((line = br.readLine()) != null) {
            out.println(line);
        }
	}

	private void
	mkfs(final String format) throws IOException, InterruptedException{
		Process p = Runtime.getRuntime().exec(
			new String[] {"mkfs.exfat", path__.toString()}
		);

		p.waitFor();
		BufferedReader br = new BufferedReader(
			new InputStreamReader(p.getInputStream())
		);
		String line;

		while ((line = br.readLine()) != null) {
            out.println(line);
        }
	}

	public boolean
	create(final String format, final String size) 
	throws IOException, InterruptedException {
		if (this.exists()) {
			return false;
		}

		this.allocate(size);
		this.mkfs(format);

		return true;
	}

	private void setMountpoint(final Path mountpoint) {
		mountpoint__ = mountpoint;
	}

	public boolean
	mount(final Path mountpoint) throws IOException, InterruptedException{
		if (!this.exists()) {
			return false;
		}

		this.setMountpoint(mountpoint);

		Process p = Runtime.getRuntime().exec(
			new String[] {"mount", path__.toString(), mountpoint__.toString()}
		);

		p.waitFor();
		BufferedReader br = new BufferedReader(
			new InputStreamReader(p.getInputStream())
		);
		String line;

		while ((line = br.readLine()) != null) {
            out.println(line);
        }

		return true;
	}

	public void umount() throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec(
			new String[] {"umount", mountpoint__.toString()}
		);

		out.println(mountpoint__.toString());
		p.waitFor();
		BufferedReader br = new BufferedReader(
			new InputStreamReader(p.getInputStream())
		);
		String line;

		while ((line = br.readLine()) != null) {
            out.println(line);
        }
	}

	public String getPath() {
		return path__.toString();
	}

	public String getTag() {
		return tag__;
	}

	public String getMountpoint() {
		return mountpoint__.toString();
	}

}