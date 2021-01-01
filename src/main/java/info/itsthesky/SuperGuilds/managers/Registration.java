package info.itsthesky.SuperGuilds.managers;

import info.itsthesky.SuperGuilds.SuperGuilds;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.io.InputStream;

public class Registration {
	private Registration() { }

	public static void register() {
		SuperGuilds instance = SuperGuilds.getInstance();
		PluginManager pm = Bukkit.getPluginManager();
	}

}
