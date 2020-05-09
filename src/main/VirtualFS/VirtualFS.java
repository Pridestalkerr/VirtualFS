package virtualfs;

import java.util.HashMap;


public class VirtualFS {

	private Config config__;
	private Path entryPoint__;
	private HashMap <String, System.Info> sysInfos__;

	public VirtualFS(final String configPath, final String entryPoint) throws java.io.IOException {
		config__ = new Config(configPath);
		entryPoint__ = Paths.get(entryPoint).toAbsolutePath();
	}

	public void init() {
		config__.create();
		System.Info[] infos = config__.parse();
		for (System.Info[] info : infos) {
			sysInfos__.put(info.hostname, info);
		}
	}

	// public boolean checkHost(final String hostname) {
	// 	if (sysInfos__.get(hostname)) {
	// 		return true;
	// 	} else {
	// 		return false;
	// 	}
	// }

	// private boolean setHost(final String hostname) {
	// 	sysInfo = sysInfos__.get(hostname)

	// 	if (!sysInfo) {
	// 		return false;
	// 	}

	// 	system__ = new system.System(sysInfo, entryPoint__);

	// 	return true;
	// }

	// public boolean bootHost(final String hostname) {
	// 	if (!this.setHost(hostname)) {
	// 		system__.boot();
	// 		return true;
	// 	} else {
	// 		return false;
	// 	}
	// }

	// public boolean login(final String user, final String password) {
	// 	system__.login(user, password);
	// }

	public system.System getSystem(final String hostname) {
		sysInfo = sysInfos__.get(hostname)

		if (!sysInfo) {
			return false;
		}

		return new System(sysInfo, entryPoint__);
	}


	// public File open(final String path) {
	// 	system__.open()
	// }

}