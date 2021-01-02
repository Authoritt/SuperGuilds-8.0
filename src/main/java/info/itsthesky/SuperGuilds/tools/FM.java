package info.itsthesky.SuperGuilds.tools;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FM {

	public void reloadFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			FileConfiguration fileconfig = YamlConfiguration.loadConfiguration(file);
			fileconfig.options().copyDefaults(true);
		}
	}


}
