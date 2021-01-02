package info.itsthesky.SuperGuilds.features.guild;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.tools.ItemBuilder;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.inventory.InventoryAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class InfoGUI {

	public void showGuildInfo(Player player, String guild) {
		File guildFile = new File(SuperGuilds.getInstance().getDataFolder(), "guilds/" + guild + ".yml");
		if (!guildFile.exists()) {
			return;
		}
		FileConfiguration guildConfig = YamlConfiguration.loadConfiguration(guildFile);

		InventoryAPI inventory = InventoryAPI.create(SuperGuilds.class);
		inventory.setTitle(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("GUI.GuildInfo.Name"))
				.setSize(3 * 9)
				.setBorder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("§1").toItemStack());



	}

}
