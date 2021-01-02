package info.itsthesky.SuperGuilds.features.emblem;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.tools.LangManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.upperlevel.spigot.book.BookUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EmblemEditor {

	public void openEmblemEditor(Player player, String choosedColor) {

		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		if (!playerFile.exists()) {
			return;
		}
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		if (!playerConfig.contains("Data.Temp.EmblemCode")) {
			playerConfig.set("Data.Temp.EmblemCode", "8;8;7;7;7;7;8;8;8;7;7;7;7;7;7;8;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;8;7;7;7;7;7;7;8;8;8;7;7;7;7;8;8");
			try {
				playerConfig.save(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String design = playerConfig.getString("Data.Temp.EmblemCode");
		String[] emblems = design.split(";");

		if (choosedColor == null) {
			choosedColor = "7";
		}
		String[] colors = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

		BookUtil.PageBuilder page = new BookUtil.PageBuilder()
				.add(new TextComponent(new LangManager().getLang("GUI.Prefix") + new LangManager().getLang("Emblem.BookName")))
				.newLine().newLine();
		int no = 0;
		int fullno = 0;
		for (String e : emblems) {
			if (no == 8) {
				page.newLine();
				no = 0;
			}
			if (no == 0) {
				page.add("     ");
			}
			page.add(BookUtil.TextBuilder.of("§" + e + "█")
					.onHover(BookUtil.HoverAction.showText("§" + choosedColor + new LangManager().getLang("Emblem.ClickToPaint")))
					.onClick(BookUtil.ClickAction.runCommand("/g emblem action:paint:" + fullno + ":" + choosedColor))
					.build()
			);
			no++;
			fullno++;
		}

		page.newLine().newLine();
		no = 0;
		for (String c : colors) {
			if (no == 8) {
				page.newLine();
				no = 0;
			}
			if (no == 0) {
				page.add("     ");
			}
			page.add(BookUtil.TextBuilder.of("§" + c + "█")
					.onHover(BookUtil.HoverAction.showText("§" + c + new LangManager().getLang("Emblem.ChangeColor")))
					.onClick(BookUtil.ClickAction.runCommand("/g emblem " + c))
					.build()
			);
			no++;
		}
		page.newLine();
		String[] buttons = new String[] {"Save", "Reset", "Back"};
		String buttonStyle;
		String buttonText;
		for (String b : buttons) {
			buttonStyle = new LangManager().getLang("Emblem.Button." + b);
			buttonText = new LangManager().getLang("Emblem.Button." + b + "Text");
			page.add(BookUtil.TextBuilder.of(buttonStyle)
					.onHover(BookUtil.HoverAction.showText(buttonText))
					.onClick(BookUtil.ClickAction.runCommand("/g emblem action:" + b))
					.build()
			);
		}

		page.newLine().add("dsq").newLine().add("dsq").newLine().add("dqs");
		ItemStack book = BookUtil.writtenBook()
				.author("Sky")
				.title("Emblem Editor")
				.pages(page.build())
				.build();
		BookUtil.openPlayer(player, book);
	}

}
