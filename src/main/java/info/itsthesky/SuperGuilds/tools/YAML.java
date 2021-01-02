/* package info.itsthesky.SuperGuilds.tools;

import info.itsthesky.SuperGuilds.SuperGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAML {
	private FileConfiguration config;
	private File realFile;

	public YAML(String path) {
		File file = new File(SuperGuilds.getInstance().getDataFolder() + "/" + path + ".yml");
		if (!file.exists() && !file.getParentFile().mkdirs()) {
			try {
				if (!file.createNewFile()) {
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		realFile = file;
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void save() {
		config.options().copyDefaults(true);
	}

	public void setValue(String key, Object value) {
		config.set(key, value);
	}

	public Boolean contains(String node) {
		return config.contains(node);
	}

	public Boolean exist() {
		File tempfile = new File(this.realFile.getPath());
		return tempfile.exists();
	}

	public Object getValue(String node) {
		if (!config.contains(node)) {
			return "NODE_NOT_FOUND";
		}
		return config.get(node);
	}

} */
