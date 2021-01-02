package info.itsthesky.SuperGuilds.features.guild;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.MSGPlayer;
import info.itsthesky.SuperGuilds.tools.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuildManager {

	public List<String> allServerGuilds() {
		File folder = new File("plugins/SuperGuilds/guilds/");
		List<String> returns = new ArrayList<>();
		if (!folder.exists()) {
			return returns;
		}
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				returns.add(file.getName());
			}
		}
		return returns;
	}

	public void createNewGuild(Player player, String name) {

		/* Fichier de config */
		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		File configFile = new File(SuperGuilds.getInstance().getDataFolder(), "config.yml");
		if (!playerFile.exists() || !configFile.exists()) {
			return;
		}
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		FileConfiguration configConfig = YamlConfiguration.loadConfiguration(configFile);

		List<String> allowedChars = Arrays.asList(configConfig.get("Guild.Allowed-Symbol").toString().split("-"));
		String control = name;
		Integer min = Integer.parseInt(configConfig.get("Guild.NameSize.Min").toString());
		Integer max = Integer.parseInt(configConfig.get("Guild.NameSize.Max").toString());
		if (!(control.length() > min && control.length() < max)) {
			new MSGPlayer().send(player, new LangManager().getLang("TooLongTooShort").replace("{MIN}", min.toString()).replace("{MAX}", max.toString()));
			return;
		}
		for (String c : allowedChars) {
			if (control.contains(c)) {
				control = control.replace(c, "");
			}
		}
		if (!control.equalsIgnoreCase("")) {
			new MSGPlayer().send(player, new LangManager().getLang("CharacterNotAllowed").replace("{CHARS}", control));
			return;
		}

		control = name;
		for (String c : new GuildManager().allServerGuilds()) {
			if (control.contains(c)) {
				control = control.replace(c, "");
			}
		}
		if (control.equalsIgnoreCase("")) {
			new MSGPlayer().send(player, new LangManager().getLang("GuildAlreadyExist"));
			return;
		}

		File guildFile = new File(SuperGuilds.getInstance().getDataFolder(), "guilds/" + name + ".yml");
		try {
			guildFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileConfiguration guildConfig = YamlConfiguration.loadConfiguration(guildFile);

		guildConfig.set("Data.Create.Username", player.getDisplayName());
		guildConfig.set("Data.Create.UUID", player.getUniqueId().toString());
		guildConfig.set("Data.Create.Date", new Utils().now("normal"));
		guildConfig.set("Data.Leader", player.getDisplayName());
		guildConfig.set("Data.Emblem", "8;8;7;7;7;7;8;8;8;7;7;7;7;7;7;8;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;8;7;7;7;7;7;7;8;8;8;7;7;7;7;8;8");
		guildConfig.set("Data.MaxSlots", "4");
		guildConfig.set("Data.MaxClaims", "5");
		guildConfig.set("Data.JoinStatus", "1");
		try {
			guildConfig.save(guildFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		addPlayer(player, name, "Leader");

	}

	public void addPlayer(Player player, String guild, String rank) {
		/* Fichier de config */
		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		File guildFile = new File(SuperGuilds.getInstance().getDataFolder(), "guilds/" + guild + ".yml");
		if (!playerFile.exists() || !guildFile.exists()) {
			return;
		}
		FileConfiguration guildConfig = YamlConfiguration.loadConfiguration(guildFile);
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		playerConfig.set("Data.Guild", guild);
		guildConfig.set("Data.Members." + player.getUniqueId() + ".Username", player.getDisplayName());
		guildConfig.set("Data.Members." + player.getUniqueId() + ".Rank", rank);
		guildConfig.set("Data.Members." + player.getUniqueId() + ".JoinDate", new Utils().now("normal"));
		try {
			playerConfig.save(playerFile);
			guildConfig.save(guildFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
