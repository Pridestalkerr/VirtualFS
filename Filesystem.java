import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.lang.InterruptedException;
import java.io.FileNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.lang.Runtime;



public class Filesystem {

	private Disk disk__;
	private Passwd passwd__;

	public Filesystem(final File diskFile, final File passwdFile) {
		this.disk__ = new Disk(diskFile);
		this.passwd__ = new Passwd(passwdFile);
	}

	public boolean exists() {
		return disk__.exists() || passwd__.exists();
	}

	public void create(final String format, final String size) throws IOException, InterruptedException{
		if (this.exists()) {
			throw new IOException("Filesystem already exists.");
		}

		passwd__.create();
		disk__.create(format, size);

	}

	public void registerUser(final User user) throws IOException{
		passwd__.useradd(user);
	}

	public boolean auth(final User user) throws FileNotFoundException{
		return passwd__.auth(user);
	}

	public void mount(final String mountpoint) throws IOException, InterruptedException{	
		disk__.mount(mountpoint);
	}

}