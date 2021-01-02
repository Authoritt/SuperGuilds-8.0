package info.itsthesky.SuperGuilds.features.commands;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.features.emblem.EmblemEditor;
import info.itsthesky.SuperGuilds.features.guild.GuildGUI;
import info.itsthesky.SuperGuilds.features.guild.GuildManager;
import info.itsthesky.SuperGuilds.features.races.RaceGUI;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.MSGPlayer;
import info.itsthesky.SuperGuilds.tools.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof ConsoleCommandSender) {
			new MSGPlayer().sendConsole(new LangManager().getLang("NoConsole"));
			return false;
		}

		Player player = (Player) sender;

		/* Fichier de config */
		File playerFile = new File(SuperGuilds.getInstance().getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		File configFile = new File(SuperGuilds.getInstance().getDataFolder(), "config.yml");
		if (!playerFile.exists() || !configFile.exists()) {
			return false;
		}
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		FileConfiguration configConfig = YamlConfiguration.loadConfiguration(configFile);

		if (args.length == 0) {
			new GuildGUI().openGuildGUI(player);
			return true;
		}

		if (args[0].equalsIgnoreCase("help")) {
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Header"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line1"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line2"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line3"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line4"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line5"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Footer"));
			return true;
		}

		if (args[0].equalsIgnoreCase("create")) {
			if (!playerConfig.get("Data.Guild").toString().equalsIgnoreCase("*")) {
				new MSGPlayer().send(player, new LangManager().getLang("Guild.AlreadyHaveGuild"));
				return false;
			}
			if (playerConfig.get("Data.Race").toString().equalsIgnoreCase("*")) {
				new MSGPlayer().send(player, new LangManager().getLang("Race.NoRace"));
				new RaceGUI().openRaceGUI(player, false);
				return false;
			}
			if (args.length == 1) {
				new MSGPlayer().send(player, new LangManager().getLang("NoGuildName"));
				return false;
			}

			new GuildManager().createNewGuild(player, args[1]);

		}

		if (args[0].equalsIgnoreCase("reload")) {
			if (args.length == 1) {
				new MSGPlayer().send(player, new LangManager().getLang("ReloadMessage.Header"));
				new MSGPlayer().send(player, new LangManager().getLang("ReloadMessage.Line1"));
				new MSGPlayer().send(player, new LangManager().getLang("ReloadMessage.Line2"));
				new MSGPlayer().send(player, new LangManager().getLang("ReloadMessage.Line3"));
				new MSGPlayer().send(player, new LangManager().getLang("ReloadMessage.Line4"));
				new MSGPlayer().send(player, new LangManager().getLang("ReloadMessage.Footer"));
				return true;
			}
		}

		if (args[0].equalsIgnoreCase("emblem")) {
			if (args.length == 1) {
				new EmblemEditor().openEmblemEditor(player, playerConfig.getString("Data.Temp.EmblemColor"));
				return true;
			}

			String[] options = args[1].split(":");

			/* Actions (Save, reset, back, paint, etc...) */
			if (options[0].equalsIgnoreCase("action")) {
				if (options[1].equalsIgnoreCase("back")) {
					new GuildGUI().openGuildGUI(player);
					return true;
				}
				if (options[1].equalsIgnoreCase("reset")) {
					playerConfig.set("Data.Temp.EmblemCode", "8;8;7;7;7;7;8;8;8;7;7;7;7;7;7;8;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;7;8;7;7;7;7;7;7;8;8;8;7;7;7;7;8;8");
					try {
						playerConfig.save(playerFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					new EmblemEditor().openEmblemEditor(player, playerConfig.getString("Data.Temp.EmblemColor"));
				} else if (options[1].equalsIgnoreCase("paint")) {
					List<String> design = Arrays.asList(playerConfig.get("Data.Temp.EmblemCode").toString().split(";"));
					design.set(Integer.parseInt(options[2]), options[3]);
					String finalDesign = new Utils().join(design, ";");
					playerConfig.set("Data.Temp.EmblemCode", finalDesign);
					try {
						playerConfig.save(playerFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					new EmblemEditor().openEmblemEditor(player, playerConfig.getString("Data.Temp.EmblemColor"));
					return true;
				}
			/* Changement de couleur */
			} else {
				playerConfig.set("Data.Temp.EmblemColor", args[1]);
				try {
					playerConfig.save(playerFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				new EmblemEditor().openEmblemEditor(player, playerConfig.getString("Data.Temp.EmblemColor"));
				return true;
			}
		}

		if (args[0].equalsIgnoreCase("race") || args[0].equalsIgnoreCase("races")) {
			new RaceGUI().openRaceGUI(player, false);
			return true;
		}

		return false;
	}
}
