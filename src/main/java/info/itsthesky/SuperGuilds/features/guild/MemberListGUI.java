package info.itsthesky.SuperGuilds.features.guild;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.tools.ItemBuilder;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class MemberListGUI {

	public void openPageAPI(Player player) {

		/* Files */
		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		if (!playerFile.exists()) { return; }
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		File guildFile = new File(SuperGuilds.getInstance().getDataFolder(), "guilds/" + playerConfig.get("Data.Guild").toString() + ".yml");
		if (!guildFile.exists()) { return; }
		FileConfiguration guildConfig = YamlConfiguration.loadConfiguration(guildFile);

		InventoryAPI inventory = InventoryAPI.create(SuperGuilds.class);
		inventory.setTitle(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("GUI.MemberList.Name"))
				.setSize(9 * 6)
				.setBorder(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName("§1").toItemStack());
		inventory.addItem(0, new ItemBuilder(Material.ARROW).setName(new LangManager().getLang("GUI.MemberList.Back")).toItemStack(), true, InventoryClickEvent -> {
			new GuildGUI().openGuildGUI(player);
		});

		Object[] values = guildConfig.getConfigurationSection("Data.Members").getKeys(false).toArray();

		int[] slotsInts = inventory.getBorder();
		int[] allInts = IntStream.range(0, 63).toArray();
		List<Integer> slots = new ArrayList<>();
		List<Integer> all = new ArrayList<>();

		for (Integer i : slotsInts) {
			slots.add(i);
		}
		for (Integer i : allInts) {
			all.add(i);
		}
		all.removeAll(slots);

		int index = 0;
		Player p;
		for (Object v : values) {
			p = Bukkit.getPlayer(UUID.fromString(v.toString()));
			inventory.addItem(all.get(index), new ItemBuilder(Material.PLAYER_HEAD).setOwner(p.getUniqueId()).setName("§6" + p.getDisplayName()).toItemStack(), true, InventoryClickEvent -> {
				player.closeInventory();
			});
			index++;
		}

		inventory.build(player);

	}
}
