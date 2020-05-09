import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.StandardOpenOption;

import java.util.stream.Stream;
import java.util.Arrays;


import java.io.IOException;


import static java.lang.System.out;



public class Main {

	public static void main(String[] args) {
		// Config conf = new Config(Paths.get("./nigger"));
		// try {
		// 	// out.println(conf.create());
		// 	out.println(conf.getSystems());
		// 	conf.addSystem(new System(Paths.get("./nigger")));
		// } catch (Exception e) {
		// 	out.println(e);
		// }

		// instantiate main object
		// 
		try {
			VirtualFS vfs = new VirtualFS("./test/config");
			String hostname = "main0";
			system.System sys = vfs.getSystem(hostname);
			sys.boot();
			String user = "root";
			String password = "niggacat";
			sys.login(user, password);
			File fd = sys.open("/aaaa/aaa/a", "rwb");
			fd.read(100);
			fd.write("4712gd87asgd67ah376g");
			fd.seek(33, fd.START);
			sys.logout();

		} catch (IOException except) {
			;
		}

		// String[][] str = new String[5][];

		// str[0] = new String[] {"aa", "aaaa"};


		// String[] lines = {"main0:sda0,sda1", "main0:sda0,sda1"};
		// Stream.of(lines).map(line -> {
		// 	String[] a = line.split(":");
		// 	return Stream.concat(Arrays.stream(new String[]{a[0]}), Arrays.stream(a[1].split(","))).toArray(String[]::new);
		// }).toArray(String[]::new);



		// return Stream.concat(Arrays.stream(a[0]), Arrays.stream(a[1].split(","))).toArray(String[]::new);

		// 

		// fs.logout();

		// fs.login(user, hostname);

		// //fs.ls(path);

		// File fd = fs.open(path);

		// Byte[] buf = fd.read(100);

		// fd.lseek(10);
		// fd.write("maestre, filesystemu");

		// try {
		// 	SeekableByteChannel file = Files.newByteChannel(Paths.get("niggacat"),
		// 		StandardOpenOption.CREATE,
		// 		StandardOpenOption.WRITE,
		// 		StandardOpenOption.APPEND
		// 	);

		// 	ByteBuffer

		// 	out.println(file.truncate(33));

		// } catch (IOException except) {
		// 	out.println("wtf");
		// }





	}

}