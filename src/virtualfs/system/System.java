package virtualfs.system;
import virtualfs.parser.Passwd;
// import virtualfs.parser.Group;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.InputStream;

import java.io.IOException;
import java.lang.InterruptedException;



import static java.lang.System.out;



public class System {

	private String hostname__;
	private Partition main__;
	private ArrayList <Partition> secondary__;

	private Passwd passwd__;
	// private Group group__;

	private Path entryPoint__;

	public System(final Info sysInfo) {
		int i = 0;    // temporary tag setting

		hostname__ = sysInfo.hostname;
		main__ = new Partition(sysInfo.main, "tag" + String.valueOf(i++));	// FIX TAGSS!!!!!!!!!!!!!!!!!!
		secondary__ = new ArrayList <Partition> ();
		for (Path secondary : sysInfo.secondary) {
			this.addPart(secondary, "tag" + String.valueOf(i++));
		}

		// passwd__ = new Passwd(Paths.get(main__.getMountpoint(), "/etc/passwd"));
		// group = new Group(Paths.get(main__.getMountpoint(), "/etc/group"));
	}

	public System(final String hostname, final Path mainPath) {
		hostname__ = hostname;
		main__ = new Partition(mainPath, "tag");
		secondary__ = new ArrayList <Partition> ();

		// passwd__ = new Passwd(Paths.get(main__.getMountpoint(), "/etc/passwd"));
	}

	public void addPart(final Path path, final String tag) {
		secondary__.add(new Partition(path, tag));
	}

	public void
	boot(final Path entryPoint) throws IOException, InterruptedException {
		entryPoint__ = entryPoint.resolve(hostname__);
		
		// /mnt/vfs/hostname/part...
		Path mountPoint = entryPoint__.resolve(main__.getTag());
		Files.createDirectories(mountPoint);
		main__.mount(mountPoint);
		passwd__ = new Passwd(Paths.get(main__.getMountpoint(), "/etc/passwd"));

		for (Partition part : secondary__) {
			mountPoint = entryPoint__.resolve(part.getTag());
			Files.createDirectories(mountPoint);
			part.mount(mountPoint);
		}
	}

	public void shutdown() throws IOException, InterruptedException {
		main__.umount();
		Path mountPoint = entryPoint__.resolve(main__.getTag());
		Files.delete(mountPoint);

		for (Partition part : secondary__) {
			part.umount();
			mountPoint = entryPoint__.resolve(part.getTag());
			Files.delete(mountPoint);
		}
	}

	public boolean validUser(final String username) throws IOException {
		User[] users = passwd__.parse();

		for (User user : users) {
			if (user.getName().equals(username)) {
				return true;
			}
		}

		return false;
	}

	public boolean
	login(final String username, final String password) throws IOException {
		User[] users = passwd__.parse();

		for (User user : users) {
			if (user.auth(username, password)) {
				return true;
			}
		}

		return false;
	}

	public File open(final String path, final String mode) {
		return null;
	}

	public InputStream
	exec(final String command) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec(
			new String[] {command}
		);
		p.waitFor();

		return p.getInputStream();
	} 

	@Override
	public String toString() {
		StringBuilder repr = new StringBuilder();

		repr.append(hostname__);
		repr.append(';');
		repr.append(main__.getPath());
		repr.append(':');
		repr.append(
			String.join(
				",", 
				secondary__.stream().map(
					partition -> partition.getPath()
				).toArray(String[]::new)
			)
		);

		return repr.toString();
	}


}