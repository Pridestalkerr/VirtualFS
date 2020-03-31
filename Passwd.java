import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;

import java.io.IOException;
import java.io.FileNotFoundException;



public class Passwd {

	private File passwdFile__;

	public Passwd(final File passwdFile) {
		this.passwdFile__ = passwdFile;
	}

	public boolean exists() {
		return passwdFile__.exists() && !passwdFile__.isDirectory();
	}

	public boolean checkIntegrity() { 
		//this should check that the file is actually formatted properly
		return true;	//empty, for now
	}

	public void create() throws IOException{
		if (this.exists()) {
			throw new IOException("Passwd file already created.");
		}

		passwdFile__.createNewFile();
	}

	public void useradd(final User user) throws IOException{
		if (!this.exists()) {
			throw new IOException("Passwd file does not exist.");
		}

		try {
			FileWriter writer = new FileWriter(passwdFile__, true);
			writer.write(String.format("%s\n", user.toString()));
			writer.close();

		} catch (IOException except) {
			throw except;
		}
	}

	public boolean auth(final User user) throws FileNotFoundException{
		try {
			Scanner reader = new Scanner(passwdFile__);

			while (reader.hasNextLine()) {
				if (user.toString().equals(reader.nextLine())) {
					return true;
				}
			}

			return false;

		} catch (FileNotFoundException except) {
			throw except;
		}
	}

}