package info.itsthesky.SuperGuilds.features.races;

import info.itsthesky.SuperGuilds.SuperGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + event.getPlayer().getUniqueId() + ".yml");
		File configFile = new File(SuperGuilds.getInstance().getDataFolder(), "config.yml");

		if (!playerFile.exists()) {

			playerFile.getParentFile().mkdirs();

			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			FileConfiguration configConfig = YamlConfiguration.loadConfiguration(configFile);

			if (((Boolean) configConfig.get("General.Debug"))) {
				SuperGuilds.getInstance().getLogger().info("Player " + event.getPlayer().getDisplayName() + " ["+event.getPlayer().getUniqueId()+"] doesn't have any SuperGuilds file! Creation ...");
			}
			List<String> yaml = new ArrayList<>();
			yaml.add("Name;" + event.getPlayer().getDisplayName());
			yaml.add("Guild;*");
			yaml.add("Race;*");
			yaml.add("RaceRight;" + configConfig.get("Races.DefaultRights"));
			for (String n : yaml) {
				String[] values = n.split(";");
				if (!playerConfig.contains("Data." + values[0])) {
					playerConfig.set("Data." + values[0], values[1]);
				}
			}
			try {
				playerConfig.save(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
