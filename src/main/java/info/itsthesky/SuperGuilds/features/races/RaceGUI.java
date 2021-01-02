package info.itsthesky.SuperGuilds.features.races;


import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.features.guild.GuildGUI;
import info.itsthesky.SuperGuilds.tools.ItemBuilder;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.Utils;
import info.itsthesky.SuperGuilds.tools.inventory.InventoryAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaceGUI {

	public void openRaceGUI(Player player, Boolean fromGuild) {

		final InventoryAPI inventory = InventoryAPI.create(SuperGuilds.class);

		File racesFile = new File(SuperGuilds.getInstance().getDataFolder(), "races.yml");
		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");

		if (!racesFile.exists() || !playerFile.exists()) {
			return;
		}

		FileConfiguration racesConfig = YamlConfiguration.loadConfiguration(racesFile);
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		int customSize = racesConfig.getInt("Settings.Menu-Rows");
		Integer currentRight = Integer.parseInt(playerConfig.get("Data.RaceRight").toString());

		ItemBuilder closeItemBuilder = new ItemBuilder(Material.getMaterial(racesConfig.getString("Settings.Items.Close.Item")));
		closeItemBuilder.setName(racesConfig.getString("Settings.Items.Close.Name").replace("&", "§"));
		ItemStack closeItem = closeItemBuilder.toItemStack();

		ItemBuilder infoItemBuilder = new ItemBuilder(Material.getMaterial(racesConfig.getString("Settings.Items.Information.Item")));
		infoItemBuilder.setName(racesConfig.getString("Settings.Items.Information.Name").replace("&", "§"));
		List<String> infoUncoloredLore = racesConfig.getStringList("Settings.Items.Information.Lore");
		int no = 0;
		for (String l : infoUncoloredLore) {
			infoUncoloredLore.set(no, infoUncoloredLore.get(no).replace("&", "§"));
			no++;
		}
		infoItemBuilder.setLore(infoUncoloredLore);
		ItemStack infoItem = infoItemBuilder.toItemStack();

		ItemBuilder countItemBuilder = new ItemBuilder(Material.getMaterial(racesConfig.getString("Settings.Items.RaceRight.Item")));
		assert currentRight != null;
		countItemBuilder.setName(racesConfig.getString("Settings.Items.RaceRight.Name").replace("&", "§").replace("{RIGHTS}", currentRight.toString()));
		ItemStack countItem = countItemBuilder.toItemStack();

		ItemBuilder backBuilder = new ItemBuilder(Material.getMaterial(racesConfig.getString("Settings.Items.Back.Item")));
		backBuilder.setName(racesConfig.getString("Settings.Items.Back.Name").replace("&", "§"));
		ItemStack backItem = backBuilder.toItemStack();


		inventory.setSize(9 * customSize);
		inventory.setTitle(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("GUI.Race"));
		inventory.setBorder(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§1").toItemStack());

		if (fromGuild) {
			inventory.addItem(racesConfig.getInt("Settings.Items.Back.Slot"), backItem, true, inventoryClickEvent -> {
				new GuildGUI().openGuildGUI(player);
			});
		} else {
			inventory.addItem(racesConfig.getInt("Settings.Items.Close.Slot"), closeItem, true, inventoryClickEvent -> {
				player.closeInventory();
			});
		}
		inventory.addItem(racesConfig.getInt("Settings.Items.Information.Slot"), infoItem, true);
		inventory.addItem(racesConfig.getInt("Settings.Items.RaceRight.Slot"), countItem, true);

		String[] races = racesConfig.getConfigurationSection("Races").getKeys(false).toArray(new String[0]);
		for (String n : races) {
			String name = (String) racesConfig.get("Races." + n + ".Name");
			name = (String) racesConfig.get("Races." + n + ".Color") + name;
			name = name.replace("&", "§");

			// Emblem Generator
			List<String> lore = new ArrayList<>();
			lore.add("§8§o" + racesConfig.get("Races." + n + ".Lore").toString());
			lore.add("§1");
			lore.add("§7Emblem:");
			String[] emblems = racesConfig.get("Races." + n + ".Emblem").toString().split(";");

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
			lore.addAll(emblemFinal);

			ItemBuilder raceItem = new ItemBuilder(Material.getMaterial(racesConfig.get("Races." + n + ".Item").toString()));
			raceItem.setName(racesConfig.getString("Races." + n + ".Color").replace("&", "§") + racesConfig.getString("Races." + n + ".Name"));
			raceItem.setLore(lore);
			inventory.addItem(racesConfig.getInt("Races." + n + ".Slot"), raceItem.toItemStack(), true, inventoryClickEvent -> {
				new RaceUtils().changeRace(player, n);
			});
		}

		inventory.build(player);
	}

}
