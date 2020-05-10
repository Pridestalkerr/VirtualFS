package virtualfs.system;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.util.ArrayList;

import java.io.IOException;



import static java.lang.System.out;



public class System {

	private String hostname__;
	private Partition main__;
	private ArrayList <Partition> secondary__;

	public System(final Info sysInfo) {
		hostname__ = sysInfo.hostname;
		main__ = new Partition(sysInfo.main);
		for (String secondary : sysInfo.secondary) {
			this.addPart(secondary);
		}
	}

	public System(final Path mainPath) {
		main__ = new Partition(mainPath);
		secondary__ = new ArrayList <Partition> ();
	}

	public void addPart(final Path path) throws IOException {
		secondary__.add(new Partition(path));
	}

	public void boot(final Path entryPoint) {
		entryPoint__ = entryPoint;

		// /mnt/vfs/part_tag
		Path mountPoint = entryPoint__.resolve(main__.tag);
		Files.createDirectory(mountPoint);
		main__.mount(mountPoint);

		for (Partition part : secondary__) {
			mountPoint = entryPoint__.resolve(part.tag);
			Files.createDirectory(mountPoint);
			part.mount(mountPoint);
		}
	}

	public void shutdown() {
		main__.umount();
		Path mountPoint = entryPoint__.resolve(main__.tag);
		Files.delete(mountPoint);

		for (Partition part : secondary__) {
			part.umount();
			mountPoint = entryPoint__.resolve(part.tag);
			Files.delete(mountPoint);
		}
	}

	public boolean login(final String user, final String password) {
		// scout main part for passwd
		this.open(main__.getMountpoint() + "/etc/passwd", "r");
		return true;
	}

	public File open(final String path, final String mode) {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder repr = new StringBuilder();

		repr.append(main__.getPath());
		repr.append(':');
		repr.append(String.join(",", secondary__.stream().map(partition -> partition.getPath()).toArray(String[]::new)));

		return repr.toString();
	}


}