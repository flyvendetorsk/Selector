package trulscraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import api.ConfigAPI;

public class Loader extends JavaPlugin implements PluginMessageListener {


	public ConfigAPI config = ConfigAPI.getInstance();

	public void onEnable() {
		System.out.println("Aktiverer Selector...");
		saveDefaultConfig();
		config.setup(this);

		Bukkit.getPluginManager().registerEvents(new Selector_Listener(), this);
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

		System.out.println("Selector aktivert.");
	}

	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] arg2) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

	}
}
