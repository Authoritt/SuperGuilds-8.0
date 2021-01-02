package info.itsthesky.SuperGuilds.features.guild;

import info.itsthesky.SuperGuilds.SuperGuilds;
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

public class GuildInfoGUI {

	public void showGuildInfo(Player player, String guild) {
		File guildFile = new File(SuperGuilds.getInstance().getDataFolder(), "guilds/" + guild + ".yml");
		if (!guildFile.exists()) {
			return;
		}
		FileConfiguration guildConfig = YamlConfiguration.loadConfiguration(guildFile);

		InventoryAPI inventory = InventoryAPI.create(SuperGuilds.class);
		inventory.setTitle(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("GUI.GuildInfo.Name"))
				.setSize(3 * 9)
				.setBorder(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§1").toItemStack());

		/* NEEDED VAR */
		Integer membersActual = guildConfig.getConfigurationSection("Data.Members").getKeys(false).size();
		Integer claimsActual;
		if (guildConfig.contains("Data.Claims")) {
			claimsActual = guildConfig.getConfigurationSection("Data.Claims").getKeys(false).size();
		} else {
			claimsActual = 0;
		}

		/* General Lore */
		List<String> lore = new LangManager().getLangList("GUI.GuildInfo.Items.General.Lore");
		int no = 0;
		for (String l : lore) {
			lore.set(no, lore.get(no)
					.replace("{NAME}", guild)
					.replace("{MEMBER_ACTUAL}", membersActual.toString())
					.replace("{MEMBER_MAX}", guildConfig.get("Data.MaxSlots").toString())
					.replace("{CLAIMS_ACTUAL}", claimsActual.toString())
					.replace("{CLAIMS_MAX}", guildConfig.get("Data.MaxClaims").toString())
					.replace("{LEADER}", guildConfig.get("Data.Leader").toString())
					.replace("{DATE}", guildConfig.get("Data.Create.Date").toString())
					.replace("{CREATOR}", guildConfig.get("Data.Create.Username").toString())
					.replace("&", "§")
			);
			no++;
		}

		/* EMBLEM LORE */
		List<String> emblems = Arrays.asList(guildConfig.get("Data.Emblem").toString().split(";"));
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

		/* GUI ITSELF */

		// > Items
		ItemStack backItem = new ItemBuilder(Material.ARROW).setName(new LangManager().getLang("GUI.GuildInfo.Items.Back")).toItemStack();
		ItemStack generalItem = new ItemBuilder(Material.BEACON).setName(new LangManager().getLang("GUI.GuildInfo.Items.General.Name")).setLore(lore).toItemStack();
		ItemStack emblemItem = new ItemBuilder(Material.KNOWLEDGE_BOOK).setName(new LangManager().getLang("GUI.GuildInfo.Items.Emblem")).setLore(emblemFinal).toItemStack();

		inventory.addItem(11, generalItem, true);
		inventory.addItem(15, emblemItem, true);
		inventory.addItem(0, backItem, true, InventoryClickEvent -> {
			new GuildGUI().openGuildGUI(player);
		});

		inventory.build(player);


	}

}
