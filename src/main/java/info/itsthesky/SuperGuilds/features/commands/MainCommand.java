package info.itsthesky.SuperGuilds.features.commands;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.features.races.RaceGUI;
import info.itsthesky.SuperGuilds.tools.LangManager;
import info.itsthesky.SuperGuilds.tools.MSGPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class MainCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof ConsoleCommandSender) {
			new MSGPlayer().sendConsole(new LangManager().getLang("NoConsole"));
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Header"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line1"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line2"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line3"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line4"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Line5"));
			new MSGPlayer().send(player, new LangManager().getLang("HelpMessage.Footer"));
			return true;
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

		if (args[0].equalsIgnoreCase("race") || args[0].equalsIgnoreCase("races")) {
			new RaceGUI().openRaceGUI(player);
			return true;
		}

		return false;
	}
}
