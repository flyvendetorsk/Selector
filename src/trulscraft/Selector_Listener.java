package trulscraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import api.IsaksApi;
import net.md_5.bungee.api.ChatColor;

public class Selector_Listener extends IsaksApi implements Listener {

	@EventHandler
	public void joinEvent(PlayerJoinEvent e) {

		if (config.getConfig().getBoolean("itemonjoin")) {
			new Selector_Handler().giveCustomItem(e.getPlayer());
		}

	}

	@EventHandler
	public void itemDropEvent(PlayerDropItemEvent e) {
		if (!config.getConfig().getBoolean("drop-allowed")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void inventory(InventoryClickEvent e) {
		if (e.getInventory().getName() != null) {
			if (e.getInventory().getName().equalsIgnoreCase(
					ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("inventory-name")))) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void itemClickEvent(PlayerInteractEvent e) {
		if (e.getItem() != null) {
			if (e.getItem().getType() != null) {
				if (e.getItem().hasItemMeta()) {
					if (e.getItem().getItemMeta().hasDisplayName()) {
						if (e.getItem().getItemMeta().getDisplayName()
								.equalsIgnoreCase(new Selector_Handler().getCustomItemName())) {
							Player p = e.getPlayer();
							if (Loader.selectorstate.equals(SelectorState.KOMMANDO)) {
								for (String key : config.getConfig().getStringList("kommandoer-vanilla")) {
									p.performCommand(key);
								}
							} else if (Loader.selectorstate.equals(SelectorState.BUNGEE)) {
								for (String key : config.getConfig().getStringList("kommandoer-bungee")) {
									p.performCommand(key);
								}
							} else if (Loader.selectorstate.equals(SelectorState.INVENTORY)) {
								p.openInventory(new Selector_Handler().getInventory(p));
							}
						}
					}

				}
			}
		}
	}

}
