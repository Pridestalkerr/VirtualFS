package virtualfs;
import virtualfs.parser.Config;
import virtualfs.system.System;
import virtualfs.system.Info;
import virtualfs.system.Partition;

// classes
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;

// exceptions
import java.io.IOException;



public class VirtualFS {

	private Config config__;
	private HashMap <String, Info> sysInfos__;

	public VirtualFS(final Path configPath) {
		config__ = new Config(configPath);
	}

	public void init() throws IOException {
		config__.create();
		Info[] infos = config__.parse();
		for (Info info : infos) {
			sysInfos__.put(info.hostname, info);
		}
	}

	public System getSystem(final String hostname) {
		Info sysInfo = sysInfos__.get(hostname);

		if (sysInfo == null) {
			return null;
		}

		return new System(null);
	}

}