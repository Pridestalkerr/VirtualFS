import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.lang.InterruptedException;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.lang.Runtime;



public class Filesystem {

	private String configPath;
	private File configFile;
	private String diskPath;
	private File diskFile;

	public Filesystem(final String configPath, final String diskPath) {
		this.configPath = configPath;
		this.configFile = new File(configPath);

		this.diskPath = diskPath;
		this.diskFile = new File(diskPath);
	}

	public boolean isInstalled() {
		return (configFile.exists() && !configFile.isDirectory()) || (diskFile.exists() && !diskFile.isDirectory());
	}

	public void makeConfig() throws IOException{
		if (!configFile.createNewFile()) {
			throw new IOException("Config file already created.");
		}
	}

	public void makeDisk(final String size) throws IOException, InterruptedException{
		try {
			Runtime.getRuntime().exec(new String[] {"fallocate", "-l", size, diskPath}).waitFor();
			Runtime.getRuntime().exec(new String[] {"mkfs.exfat", diskPath}).waitFor();
		} catch (IOException | InterruptedException except) {
			throw except;
		}

	}

	public void addUser(final User user) throws IOException{
		try {
			if (!this.isInstalled()) {
				throw new IOException("Not installed.");
			}

			FileWriter writer = new FileWriter(configFile, true);
			writer.write(String.format("%s\n", user.toString()));
			writer.close();

		} catch (IOException except) {
			throw except;
		}
	}


}