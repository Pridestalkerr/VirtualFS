package vfsmanager;
import virtualfs.parser.Config;
import virtualfs.system.Partition;
import virtualfs.system.System;
import virtualfs.system.Info;

// std classes
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// std exceptions
import java.io.IOException;

// statics
import static java.lang.System.out;



public class Main {

	static String BASE_PATH = "/root/.virtualfs/";

	public static void main(String[] args) throws IOException, InterruptedException {

		// vfsmanager ls part
		// vfsmanager ls sys
		// vfsmanager add part path format size
		// vfsmanager add sys part_paths[]

		if (args[0].equals("ls")) {
			if (args[1].equals("part")) {
				// vfsmanager ls part
				Process p = Runtime.getRuntime().exec(new String[] {"ls", "-lah", BASE_PATH + "part/"});
				p.waitFor();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
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
				Partition part = new Partition(Paths.get(BASE_PATH, "part", args[2]));
				part.create(args[3], args[4]);

				out.println("ok");

			} else if (args[1].equals("sys")) {
				// vfsmanager add sys main_part sec_part[]
				System sys = new System(Paths.get(BASE_PATH, "part", args[2]));
				for (int itr = 3; itr < args.length; ++itr) {
					sys.addPart(Paths.get(BASE_PATH, "part", args[itr]));
				}
				Config conf = new Config(Paths.get(BASE_PATH, "config"));
				conf.addSystem(sys);

			} else {
				out.println("Invalid arguments.");
			}
		} else {
			out.println("Invalid arguments.");
		}
	}

}