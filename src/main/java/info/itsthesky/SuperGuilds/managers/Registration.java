package info.itsthesky.SuperGuilds.managers;

import info.itsthesky.SuperGuilds.SuperGuilds;
import info.itsthesky.SuperGuilds.features.commands.MainCommand;
import info.itsthesky.SuperGuilds.features.races.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Registration {
	private Registration() { }

	public static void register() {
		SuperGuilds instance = SuperGuilds.getInstance();
		PluginManager pm = Bukkit.getPluginManager();
		instance.getServer().getPluginManager().registerEvents(new JoinEvent(), instance);

		instance.getCommand("superguilds").setExecutor(new MainCommand());
	}



}
