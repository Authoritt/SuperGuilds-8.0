package info.itsthesky.SuperGuilds.features.guild;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.features.emblem.EmblemEditor;
import info.itsthesky.SuperGuilds.features.players.PlayerInfoGUI;
import info.itsthesky.SuperGuilds.features.races.RaceGUI;
import info.itsthesky.SuperGuilds.tools.ItemBuilder;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.MSGPlayer;
import info.itsthesky.SuperGuilds.tools.inventory.InventoryAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class GuildGUI {

	public void openGuildGUI(Player player) {

		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		if (!playerFile.exists()) {
			return;
		}
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		if (playerConfig.get("Data.Race").toString().equalsIgnoreCase("*")) {
			new MSGPlayer().send(player, new LangManager().getLang("Race.NoRace"));
			new RaceGUI().openRaceGUI(player, false);
			return;
		}

		if (playerConfig.get("Data.Guild").toString().equalsIgnoreCase("*")) {
			new MSGPlayer().send(player, new LangManager().getLang("NoGuild"));
			return;
		}

		String guild = (String) playerConfig.get("Data.Guild");
		File guildFile = new File(SuperGuilds.getInstance().getDataFolder(), "guilds/" + guild + ".yml");
		if (!guildFile.exists()) {
			return;
		}
		FileConfiguration guildConfig = YamlConfiguration.loadConfiguration(guildFile);

		/* NEEDED VAR */
		Integer membersActual = guildConfig.getConfigurationSection("Data.Members").getKeys(false).size();
		Integer claimsActual;
		if (guildConfig.contains("Data.Claims")) {
			claimsActual = guildConfig.getConfigurationSection("Data.Claims").getKeys(false).size();
		} else {
			claimsActual = 0;
		}

		/* GUI ITSELF */

		// > Items
		ItemStack infoItem = new ItemBuilder(Material.PAPER).setName(new LangManager().getLang("GUI.Guild.Items.Info")).toItemStack();
		ItemStack closeItem = new ItemBuilder(Material.RED_DYE).setName(new LangManager().getLang("GUI.Guild.Items.Close")).toItemStack();
		ItemStack emblemItem = new ItemBuilder(Material.KNOWLEDGE_BOOK).setName(new LangManager().getLang("GUI.Guild.Items.Emblem")).toItemStack();
		ItemStack yourItem = new ItemBuilder(Material.PLAYER_HEAD).setOwner(player.getUniqueId()).setName(new LangManager().getLang("GUI.Guild.Items.PlayerInfo").replace("{PLAYER}", player.getDisplayName())).toItemStack();
		ItemStack racesItem = new ItemBuilder(Material.BONE).setName(new LangManager().getLang("GUI.Guild.Items.Races")).toItemStack();

		ItemStack membersItem = new ItemBuilder(Material.NETHER_STAR).setName(new LangManager().getLang("GUI.Guild.Items.Members").replace("{ACTUAL}", membersActual.toString()).replace("{MAX}", guildConfig.get("Data.MaxSlots").toString())).toItemStack();
		ItemStack claimsItem = new ItemBuilder(Material.GRASS_BLOCK).setName(new LangManager().getLang("GUI.Guild.Items.Claims").replace("{ACTUAL}", claimsActual.toString()).replace("{MAX}", guildConfig.get("Data.MaxClaims").toString())).toItemStack();
		ItemStack toggleItem;
		String newToggle;
		if (guildConfig.get("Data.JoinStatus").toString().equalsIgnoreCase("1")) {
			toggleItem = new ItemBuilder(Material.OAK_DOOR).setName(new LangManager().getLang("GUI.Guild.Items.JoinStatus.Enable.Name")).setLore(Collections.singletonList(new LangManager().getLang("GUI.Guild.Items.JoinStatus.Enable.Lore"))).toItemStack();
			newToggle = "0";
		} else {
			newToggle = "1";
			toggleItem = new ItemBuilder(Material.IRON_DOOR).setName(new LangManager().getLang("GUI.Guild.Items.JoinStatus.Disable.Name")).setLore(Collections.singletonList(new LangManager().getLang("GUI.Guild.Items.JoinStatus.Disable.Lore"))).toItemStack();
		}

		// > GUI Creation
		InventoryAPI inventory = InventoryAPI.create(SuperGuilds.class);
		inventory.setSize(6 * 9);
		inventory.setBorder(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§c").toItemStack());
		inventory.setTitle(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("GUI.Guild.Name"));

		// > Adding Items
		inventory.addItem(0, closeItem, true, inventoryClickEvent -> {
			player.closeInventory();
		});

		inventory.addItem(3, emblemItem, true, inventoryClickEvent -> {
			new EmblemEditor().openEmblemEditor(player, "7");
		});

		inventory.addItem(4, infoItem, true, inventoryClickEvent -> {
			new GuildInfoGUI().showGuildInfo(player, guild);
		});

		inventory.addItem(5, yourItem, true, inventoryClickEvent -> {
			new PlayerInfoGUI().showPlayerInfo(player, player);
		});

		inventory.addItem(8, racesItem, true, inventoryClickEvent -> {
			new RaceGUI().openRaceGUI(player, true);
		});

		/* --------------- */

		inventory.addItem(21, membersItem, true, inventoryClickEvent -> {
			new MemberListGUI().openPageAPI(player);
		});
		inventory.addItem(22, claimsItem, true, inventoryClickEvent -> {
			new RaceGUI().openRaceGUI(player, true);
		});
		inventory.addItem(23, toggleItem, true, inventoryClickEvent -> {
			guildConfig.set("Data.JoinStatus", newToggle);
			try {
				guildConfig.save(guildFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			openGuildGUI(player);
		});

		inventory.build(player);

	}
}
