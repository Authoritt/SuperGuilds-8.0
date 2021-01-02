package info.itsthesky.SuperGuilds.tools;

import info.itsthesky.SuperGuilds.SuperGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangManager {

	public String getLang(String key) {
		File langFile = new File(SuperGuilds.getInstance().getDataFolder(), "locale.yml");
		FileConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
		return langConfig.get("Key." + key).toString().replace("&", "§");
	}

}
