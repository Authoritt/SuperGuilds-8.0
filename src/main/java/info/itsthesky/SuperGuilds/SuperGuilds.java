package info.itsthesky.SuperGuilds;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class SuperGuilds extends JavaPlugin {
	private static SuperGuilds instance;


	@Override
	public void onEnable() {
		instance = this;

		getLogger().info("==========================================");
		getLogger().info("SuperGuilds is loading ...");

		saveResourceAs("config.yml", "config.yml");
		saveResourceAs("races.yml", "races.yml");
		saveResourceAs("locale.yml", "locale.yml");

		getLogger().info("SuperGuilds v" + getDescription().getVersion() + " has been loaded!");
		getLogger().info("==========================================");
	}

	public static SuperGuilds getInstance() {
		return instance;
	}

	private void saveResourceAs(String inPath, String outPath) {
		if (inPath == null || inPath.isEmpty()) {
			throw new IllegalArgumentException("The input path cannot be null or empty");
		}
		InputStream in = getResource(inPath);
		if (in == null) {
			throw new IllegalArgumentException("The file "+inPath+" cannot be found in plugin's resources");
		}
		if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
			getLogger().severe("Failed to make the main directory !");
		}
		File outFile = new File(getDataFolder(), outPath);
		try {
			if (!outFile.exists()) {
				getLogger().info("The file "+outFile+" cannot be found, create one for you ...");
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int n;

				while ((n = in.read(buf)) >= 0) {
					out.write(buf, 0, n);
				}

				out.close();
				in.close();

				if (!outFile.exists()) {
					getLogger().severe("Unable to copy file !");
				} else {
					getLogger().info("The file "+outFile+" has been created!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
