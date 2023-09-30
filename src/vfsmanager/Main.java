package vfsmanager;
import virtualfs.parser.Config;
import virtualfs.parser.Passwd;
import virtualfs.system.Partition;
import virtualfs.system.Info;
import virtualfs.system.User;

// classes
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Arrays;

// exceptions
import java.io.IOException;

// statics
import static java.lang.System.out;



public class Main {

	static String BASE_PATH = "/root/.vfs/";

	public static void
	main(String[] args) throws IOException, InterruptedException {

		// vfsmanager ls part
		// vfsmanager ls sys
		// vfsmanager add part path format size
		// vfsmanager add sys part_paths[]
		// vfsmanager add user path_to_part

		if (args[0].equals("ls")) {
			if (args[1].equals("part")) {
				// vfsmanager ls part
				Process p = Runtime.getRuntime().exec(
					new String[] {"ls", "-lah", BASE_PATH + "part/"}
				);
				p.waitFor();
				BufferedReader br = new BufferedReader(
					new InputStreamReader(p.getInputStream())
				);
				String line;

				while ((line = br.readLine()) != null) {
		            out.println(line);
		        }

			} else if (args[1].equals("sys")) {
				// vfsmanager ls sys
				Config conf = new Config(Paths.get(BASE_PATH, "config"));
				Info[] systems = conf.parse();
				for (Info sys : systems) {
					out.println(sys);
				}

			} else {
				out.println("Invalid arguments.");
			}
		} else if (args[0].equals("add")) {
			if (args[1].equals("part")) {
				// vfsmanager add part name format size
				Partition part = new Partition(
					Paths.get(BASE_PATH, "part", args[2]),
					"tag"
				);
				part.create(args[3], args[4]);

				out.println("ok");

			} else if (args[1].equals("sys")) {
				// vfsmanager add sys hostname main_part sec_part[]
				String hostname = args[2];
				Path main = Paths.get(BASE_PATH, "part", args[3]);

				Path secondaries[] = Arrays.stream(
					Arrays.copyOfRange(args, 4, args.length)
				).map(
					tag -> Paths.get(BASE_PATH, "part", tag)
				).toArray(Path[]::new);

				Config conf = new Config(Paths.get(BASE_PATH, "config"));
				conf.addSystem(new Info(hostname, main, secondaries));

			} else if (args[1].equals("user")) {
				// vfsmanager add user part 
				// mount partition, open /etc/passwd/ and add user to it
				Partition part = new Partition(
					Paths.get(BASE_PATH, "part", args[2]),
					"tag"
				);
				// /mnt/vfs/part
				Path mountpoint = Paths.get("/mnt/vfs/", args[2]);
				part.mount(mountpoint);

				Scanner in = new Scanner(System.in);
				out.printf("Username: ");
				String username = in.nextLine();
				out.printf("Password: ");
				String password = in.nextLine();

				Passwd passwd = new Passwd(mountpoint.resolve("etc/passwd"));
				passwd.addUser(new User(
					username,
					0,
					0,
					"/home/" + username,
					User.hashString(password)
				));

				part.umount();

			} else {
				out.println("Invalid arguments.");
			}

		} else {
			out.println("Invalid arguments.");
		}
	}

}