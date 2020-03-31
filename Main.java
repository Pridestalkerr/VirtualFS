
//sudo groupadd fs-java
//sudo chgrp fs-java fs
import java.io.File;

import java.util.Scanner;

import java.io.IOException;
import java.lang.InterruptedException;

import java.util.concurrent.TimeUnit;



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

			filesystem.create("exfat", size);
			filesystem.registerUser(new User("root", password));

			System.out.println("Done.");

		} catch (IOException | InterruptedException except) {
			System.out.println(except.getMessage());
		}
	}

	public static void runCLI(final Filesystem filesystem) {
		try {
			Scanner input = new Scanner(System.in);
			String username;
			String password;

			System.out.println("Welcome.");

			System.out.printf("username: ");
			username = input.nextLine();

			System.out.printf("password: ");
			password = input.nextLine();

			User user = new User(username, password);

			while (!filesystem.auth(user)) {
				TimeUnit.SECONDS.sleep(3);

				System.out.printf("Wrong credentials. Would you like to register a user? (y/n): ");

				String option = input.nextLine();
				if (option.equals("y") || option.equals("Y")) {
					//prompt a register
					System.out.printf("Choose a username: ");
					username = input.nextLine();

					System.out.printf("Choose a password: ");
					password = input.nextLine();
					while (password.matches(".*\\s+.*")) {
						System.out.printf("Password must contain no whitespace characters.\nTry again: ");
						password = input.nextLine();
					}

					filesystem.registerUser(new User(username, password));

					break;
				}

				System.out.printf("username: ");
				username = input.nextLine();

				System.out.printf("password: ");
				password = input.nextLine();

				user = new User(username, password);
			}

			//user passed the auth, either registered or login'd

			System.out.printf("Welcome %s\n", username);

			//mount filesystem

			filesystem.mount("./fs-test");

		} catch (IOException | InterruptedException except) {
			System.out.println(except.getMessage());
		}
	}


	public static void main(String[] args) {
		new Disk(new File("."));
		new Passwd(new File("."));
		Filesystem filesystem = new Filesystem(new File("./.disk"), new File("./.passwd"));	//config file, disk file
		if (!filesystem.exists()) {
			runInstaller(filesystem);
		} else {
			runCLI(filesystem);
		}
	}

}