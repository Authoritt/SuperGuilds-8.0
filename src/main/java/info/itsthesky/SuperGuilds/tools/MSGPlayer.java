package info.itsthesky.SuperGuilds.tools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MSGPlayer {

	public void send(Player player, String message) {
		player.sendMessage(new LangManager().getLang("Prefix") + message);
	}

	public void sendConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(new LangManager().getLang("Prefix") + message);
	}

}
