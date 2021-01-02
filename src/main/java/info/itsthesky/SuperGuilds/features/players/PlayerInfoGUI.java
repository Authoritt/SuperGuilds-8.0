package info.itsthesky.SuperGuilds.features.players;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.features.guild.GuildGUI;
import info.itsthesky.SuperGuilds.tools.ItemBuilder;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.inventory.InventoryAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PlayerInfoGUI {

	public void showPlayerInfo(Player player, Player in) {
		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + in.getUniqueId() + ".yml");
		File raceFile = new File(SuperGuilds.getInstance().getDataFolder(), "races.yml");
		if (!playerFile.exists() || !raceFile.exists()) {
			return;
		}
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		FileConfiguration raceConfig = YamlConfiguration.loadConfiguration(raceFile);

		InventoryAPI inventory = InventoryAPI.create(SuperGuilds.class);
		inventory.setTitle(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("GUI.GuildInfo.Name"))
				.setSize(3 * 9)
				.setBorder(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("§1").toItemStack());

		/* General Lore */
		List<String> lore = new LangManager().getLangList("GUI.PlayerInfo.Items.General.Lore");
		int no = 0;
		for (String l : lore) {
			lore.set(no, lore.get(no)
					.replace("{NAME}", in.getDisplayName())
					.replace("{UUID}", in.getUniqueId().toString())
					.replace("{GUILD}", playerConfig.get("Data.Guild").toString())
					.replace("&", "§")
			);
			no++;
		}

		/* EMBLEM LORE */
		List<String> elore = new LangManager().getLangList("GUI.PlayerInfo.Items.Race.Lore");
		no = 0;
		for (String l : elore) {
			elore.set(no, elore.get(no)
					.replace("{RACE}", playerConfig.get("Data.Race").toString())
					.replace("{COUNT}", playerConfig.get("Data.RaceRight").toString())
					.replace("&", "§")
			);
			no++;
		}
		String race = playerConfig.get("Data.Race").toString();

		List<String> emblems = Arrays.asList(raceConfig.get("Races." + race + ".Emblem").toString().split(";"));
		StringBuilder emblemBuilder = new StringBuilder();
		no = 0;
		for (String e : emblems) {
			if (no == 8) {
				emblemBuilder.append("-");
				no = 0;
			}
			emblemBuilder.append("§" + e + "█");
			no++;
		}
		List<String> emblemFinal = Arrays.asList(emblemBuilder.toString().split("-"));
		elore.addAll(emblemFinal);

		/* GUI ITSELF */

		// > Items
		ItemStack backItem = new ItemBuilder(Material.ARROW).setName(new LangManager().getLang("GUI.PlayerInfo.Items.Back")).toItemStack();
		ItemStack generalItem = new ItemBuilder(Material.PLAYER_HEAD).setOwner(in.getUniqueId()).setName(new LangManager().getLang("GUI.PlayerInfo.Items.General.Name")).setLore(lore).toItemStack();
		ItemStack emblemItem = new ItemBuilder(Material.BONE).setName(new LangManager().getLang("GUI.PlayerInfo.Items.Race.Name")).setLore(elore).toItemStack();

		inventory.addItem(11, generalItem, true);
		inventory.addItem(15, emblemItem, true);
		inventory.addItem(0, backItem, true, InventoryClickEvent -> {
			new GuildGUI().openGuildGUI(player);
		});

		inventory.build(player);


	}

}
