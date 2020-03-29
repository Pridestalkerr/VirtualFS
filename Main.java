
//sudo groupadd fs-java
//sudo chgrp fs-java fs

import java.io.IOException;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.lang.InterruptedException;



public class Main {


	public static void runInstaller(final Filesystem filesystem) {
		try {
			Scanner input = new Scanner(System.in);
			String password;
			String size;

			System.out.println("Welcome.\nThe following wizard will allow you to install the application.");

			System.out.printf("Choose a size for your virtual filesystem: ");
			size = input.nextLine();

			System.out.printf("Choose a password for the root user (no whitespaces): ");
			password = input.nextLine();

			while (password.matches(".*\\s+.*")) {
				System.out.printf("Password must contain no whitespace characters.\nTry again: ");
				password = input.nextLine();
			}

			System.out.println("Ok. Installing...");

			filesystem.makeConfig();
			filesystem.makeDisk(size);
			filesystem.addUser(new User("root", password));

			System.out.println("Done.");

		} catch (IOException | NoSuchAlgorithmException | InterruptedException except) {
			System.out.println(except.getMessage());
		}
	}

	public static void runCLI(final Filesystem filesystem) {
		return;
	}


	public static void main(String[] args) {

		Filesystem filesystem = new Filesystem("./.passwd", "./.disk0");	//config file, disk file
		if (!filesystem.isInstalled()) {
			runInstaller(filesystem);
		} else {
			runCLI();
		}
	}

}