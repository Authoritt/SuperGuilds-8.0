package info.itsthesky.SuperGuilds.tools;

import info.itsthesky.SuperGuilds.SuperGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LangManager {

	public String getLang(String key) {
		File langFile = new File(SuperGuilds.getInstance().getDataFolder(), "locale.yml");
		FileConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
		if (!langConfig.contains("Key." + key)) {
			SuperGuilds.getInstance().getLogger().severe("Cannot check the locale node " + key + " ! Verify you've the latest language file!");
			return "*";
		}
		return langConfig.get("Key." + key).toString().replace("&", "§");
	}

	public List<String> getLangList(String key) {
		File langFile = new File(SuperGuilds.getInstance().getDataFolder(), "locale.yml");
		FileConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
		if (!langConfig.contains("Key." + key)) {
			SuperGuilds.getInstance().getLogger().severe("Cannot check the locale node " + key + " ! Verify you've the latest language file!");
			return new ArrayList<>();
		}
		return (List<String>) langConfig.get("Key." + key);
	}

}
