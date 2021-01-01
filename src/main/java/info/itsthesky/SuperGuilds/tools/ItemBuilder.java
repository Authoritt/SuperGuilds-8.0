package info.itsthesky.SuperGuilds.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
	private final ItemStack item;

	public ItemBuilder(Material material) {
		item = new ItemStack(material, 1);
	}

	public ItemBuilder setName(String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setAmount(Integer quantity) {
		item.setAmount(quantity);
		return this;
	}

	public ItemBuilder setMaterial(Material material) {
		item.setType(material);
		return this;
	}

	public ItemBuilder toggleUnbreakable() {
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(!meta.isUnbreakable());
		item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setCMD(Integer cmd) {
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(cmd);
		item.setItemMeta(meta);
		return this;
	}

	public ItemStack toItemStack() {
		return item;
	}

}