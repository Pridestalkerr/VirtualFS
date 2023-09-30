package vfs;
import virtualfs.VirtualFS;

// classes
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

// exceptions
import java.io.IOException;
import java.lang.InterruptedException;

// statics
import static java.lang.System.out;



public class Main {

	enum Color {
	    //Color end string, color reset
	    RESET("\033[0m"),

	    // Regular Colors. Normal color, no bold, background color etc.
	    BLACK("\033[0;30m"),    // BLACK
	    RED("\033[0;31m"),      // RED
	    GREEN("\033[0;32m"),    // GREEN
	    YELLOW("\033[0;33m"),   // YELLOW
	    BLUE("\033[0;34m"),     // BLUE
	    MAGENTA("\033[0;35m"),  // MAGENTA
	    CYAN("\033[0;36m"),     // CYAN
	    WHITE("\033[0;37m"),    // WHITE

	    // Bold
	    BLACK_BOLD("\033[1;30m"),   // BLACK
	    RED_BOLD("\033[1;31m"),     // RED
	    GREEN_BOLD("\033[1;32m"),   // GREEN
	    YELLOW_BOLD("\033[1;33m"),  // YELLOW
	    BLUE_BOLD("\033[1;34m"),    // BLUE
	    MAGENTA_BOLD("\033[1;35m"), // MAGENTA
	    CYAN_BOLD("\033[1;36m"),    // CYAN
	    WHITE_BOLD("\033[1;37m"),   // WHITE

	    // Underline
	    BLACK_UNDERLINED("\033[4;30m"),     // BLACK
	    RED_UNDERLINED("\033[4;31m"),       // RED
	    GREEN_UNDERLINED("\033[4;32m"),     // GREEN
	    YELLOW_UNDERLINED("\033[4;33m"),    // YELLOW
	    BLUE_UNDERLINED("\033[4;34m"),      // BLUE
	    MAGENTA_UNDERLINED("\033[4;35m"),   // MAGENTA
	    CYAN_UNDERLINED("\033[4;36m"),      // CYAN
	    WHITE_UNDERLINED("\033[4;37m"),     // WHITE

	    // Background
	    BLACK_BACKGROUND("\033[40m"),   // BLACK
	    RED_BACKGROUND("\033[41m"),     // RED
	    GREEN_BACKGROUND("\033[42m"),   // GREEN
	    YELLOW_BACKGROUND("\033[43m"),  // YELLOW
	    BLUE_BACKGROUND("\033[44m"),    // BLUE
	    MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
	    CYAN_BACKGROUND("\033[46m"),    // CYAN
	    WHITE_BACKGROUND("\033[47m"),   // WHITE

	    // High Intensity
	    BLACK_BRIGHT("\033[0;90m"),     // BLACK
	    RED_BRIGHT("\033[0;91m"),       // RED
	    GREEN_BRIGHT("\033[0;92m"),     // GREEN
	    YELLOW_BRIGHT("\033[0;93m"),    // YELLOW
	    BLUE_BRIGHT("\033[0;94m"),      // BLUE
	    MAGENTA_BRIGHT("\033[0;95m"),   // MAGENTA
	    CYAN_BRIGHT("\033[0;96m"),      // CYAN
	    WHITE_BRIGHT("\033[0;97m"),     // WHITE

	    // Bold High Intensity
	    BLACK_BOLD_BRIGHT("\033[1;90m"),    // BLACK
	    RED_BOLD_BRIGHT("\033[1;91m"),      // RED
	    GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
	    YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
	    BLUE_BOLD_BRIGHT("\033[1;94m"),     // BLUE
	    MAGENTA_BOLD_BRIGHT("\033[1;95m"),  // MAGENTA
	    CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
	    WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

	    // High Intensity backgrounds
	    BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
	    RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
	    GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
	    YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
	    BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
	    MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
	    CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
	    WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE

	    private final String code;

	    Color(String code) {
	        this.code = code;
	    }

	    @Override
	    public String toString() {
	        return code;
	    }
	}

	static String BASE_PATH = "/root/.vfs/";

	public static String prettify(final String user, final String hostname) {
		return String.format(
			"%s%s@%s%s $%s ",
			Color.MAGENTA_BOLD_BRIGHT,
			user,
			hostname,
			Color.RED_BOLD_BRIGHT,
			Color.WHITE
		);
	}

	public static void
	runShell(
		final String user,
		final String hostname,
		virtualfs.system.System sys
	) throws IOException, InterruptedException {

		Scanner in = new Scanner(System.in);
		while (true) {

	        out.printf("%s", prettify(user, hostname));

			String command = in.nextLine();

			if (command.equals("exit")) {
				break;
			}

			InputStream output = sys.exec(command);

			BufferedReader br = new BufferedReader(
				new InputStreamReader(output)
			);
			String line;

			while ((line = br.readLine()) != null) {
	            out.println(line);
	        }
		}



		// File fd = sys.open("/aaaa/aaa/a", "rwb");
		// fd.read(100);
		// fd.write("4712gd87asgd67ah376g");
		// fd.seek(33, fd.START);
		// sys.logout();
	}

	public static void
	main(final String[] args) throws IOException, InterruptedException {

		// vfs root@host0
		// password: 
		// username
		// root@host0 >

		VirtualFS vfs = new VirtualFS(Paths.get(BASE_PATH, "config"));

		String[] attempt = args[0].split("@");
		String user = attempt[0];
		String hostname = attempt[1];

		virtualfs.system.System sys = vfs.getSystem(hostname);
		if (sys == null) {
			out.println("Invalid hostname.");
			System.exit(1);
		}

		out.println(sys.toString());

		sys.boot(Paths.get("/mnt/vfs/"));

		Runtime.getRuntime().addShutdownHook(new Thread() { 
			public void shutdown() throws IOException, InterruptedException {
				out.println("system shutdown...");
				sys.shutdown();
			} 
	    }); 

		if (!sys.validUser(user)) {
			out.println("Invalid user.");
			System.exit(1);
		}

		Scanner in = new Scanner(System.in);
		out.printf("Password: ");
		String password = in.nextLine();

		while (!sys.login(user, password)) {
			TimeUnit.SECONDS.sleep(3);
			out.println("Wrong password.");
			out.printf("Password: ");
			password = in.nextLine();
		}

		runShell(user, hostname, sys);

		sys.shutdown();

		System.exit(0);
	}

}