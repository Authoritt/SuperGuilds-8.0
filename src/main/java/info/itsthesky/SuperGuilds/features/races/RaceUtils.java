package info.itsthesky.SuperGuilds.features.races;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.MSGPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RaceUtils {


	public void changeRace(Player player, String race) {

		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");

		if (!playerFile.exists()) {
			return;
		}

		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		int raceRights = Integer.parseInt(playerConfig.get("Data.RaceRight").toString());
		if (raceRights == 0) {
			new MSGPlayer().send(player, new LangManager().getLang("Race.NoRightsRemaining"));
			return;
		}

		if (race.equalsIgnoreCase(playerConfig.getString("Data.Race"))) {
			new MSGPlayer().send(player, new LangManager().getLang("Race.SameRace"));
			new RaceGUI().openRaceGUI(player);
			return;
		}

		player.closeInventory();
		new MSGPlayer().send(player, new LangManager().getLang("Race.RaceChanged").replace("{RACE}", race));
		raceRights = raceRights-1;

		playerConfig.set("Data.RaceRight", raceRights);
		playerConfig.set("Data.Race", race);
		try {
			playerConfig.save(playerFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		loadRaceEffect(player);
	}

	public void loadRaceEffect(Player player) {

		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		File racesFile = new File(SuperGuilds.getInstance().getDataFolder(), "races.yml");

		if (!racesFile.exists() || !playerFile.exists()) {
			return;
		}

		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		FileConfiguration racesConfig = YamlConfiguration.loadConfiguration(racesFile);

		String playerRace = playerConfig.getString("Data.Race");
		assert playerRace != null;
		if (playerRace.equalsIgnoreCase("*")) {
			return;
		}

		ArrayList<String> effects = (ArrayList<String>) racesConfig.getStringList("Races." + playerRace + ".Effects");
		for (String e : effects) {
			String[] effectslist = e.split("/");
			String eff = effectslist[0];
			player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(eff)), 9999999, 0));
		}
	}
}
