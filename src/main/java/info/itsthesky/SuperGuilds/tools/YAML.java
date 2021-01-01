package info.itsthesky.SuperGuilds.tools;

import info.itsthesky.SuperGuilds.SuperGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;

import java.io.File;

public class YAML {
	private FileConfiguration config;

	public YAML(String path) {
		File file = new File(path);
		if (!file.exists() && !file.mkdir()) {
			SuperGuilds.getInstance().getLogger().severe("Unable to find or create the file " +path+ ".");
			return;
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void setValue(String key, Object value) {
		config.set(key, value);
	}

	public Object getValue(String node) {
		if (!config.contains(node)) {
			return "NODE_NOT_FOUND";
		}
		return config.get(node);
	}

}
