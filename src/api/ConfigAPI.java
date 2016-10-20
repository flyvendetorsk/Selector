package api;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class ConfigAPI {
	public static ConfigAPI instance = new ConfigAPI();
	Plugin p;
	FileConfiguration config;
	File cfile;

	private ConfigAPI() {
	}

	public static ConfigAPI getInstance() {
		return instance;
	}

	public void setup(Plugin p) {
		this.cfile = new File(p.getDataFolder(), "config.yml");
		this.config = p.getConfig();
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}

	}

	public FileConfiguration getConfig() {
		return this.config;
	}

	public void saveConfig() {
		try {
			this.config.save(this.cfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe((Object) ChatColor.RED + "Could not save config.yml!");
		}
	}

	public void reloadConfig() {
		this.config = YamlConfiguration.loadConfiguration((File) this.cfile);
	}

	public PluginDescriptionFile getDesc() {
		return this.p.getDescription();
	}
}