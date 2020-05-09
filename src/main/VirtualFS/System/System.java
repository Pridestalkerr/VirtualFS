package virtualfs.system;

import java.nio.file.Paths;
import java.nio.file.Path;

import java.util.ArrayList;



public class System {

	private String hostname__;
	private Partition main__;
	private ArrayList <Partition> secondary__;
	private String entryPoint__;

	public System(final Info sysInfo, final String entryPoint) {
		hostname__ = sysInfo.hostname;
		main__ = new Partition(sysInfo.main);
		for (String secondary : sysInfo.secondary) {
			this.addPart(secondary);
		}
	}

	// public System(final String mainPath) {
	// 	main__ = new Partition(mainPath, "./");
	// }

	public void addPart(final String path, final String name) {
		secondary__.add(new Partition(path, entryPoint__ + name));
	}

	public void boot() {
		main__.mount(entryPoint__ + main__.getPath());
		for (Partition part : secondary__) {
			part.mount(entryPoint__ + part.getPath());
		}
	}

	public boolean login(final String user, final String password) {
		// scout main part for passwd
		this.open(main__.getMountpoint() + "/etc/passwd", "r");
		
	}

	public File open(final String path) {

	}


	public String toString() {
		StringBuilder repr = new StringBuilder();

		repr.append(main__.getPath());
		repr.append(':');
		repr.append(String.join(",", secondary__.stream().map(partition -> partition.getPath()).toArray(String[]::new)));

		return repr.toString();
	}


}