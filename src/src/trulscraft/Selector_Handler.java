package trulscraft;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import api.IsaksApi;

public class Selector_Handler extends IsaksApi {

	public void giveCustomItem(Player p) {
		ItemStack item = new ItemStack(Material.getMaterial(config.getConfig().getString("item")));
		if (item.getType() == null) {
			for (int x = 0; 10 > x; x++) {
				System.out.println("[Selector] DU HAR OPPGITT ETT FEIL ITEMNAVN. BYTT I CONFIG");
			}
			return;
		}
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("name")));
		List<String> lore = config.getConfig().getStringList("lore");
		int x = 0;
		for (String key : lore) {
			lore.set(x, ChatColor.translateAlternateColorCodes('&', key));
			x++;
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		p.getInventory().addItem(item);

	}

	public String getCustomItemName() {
		return ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("name"));
	}

	public Inventory getInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9 * config.getConfig().getInt("inventory-size-rows"),
				ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("inventory-name")));
		for (String key : config.getConfig().getConfigurationSection("inventory").getKeys(false)) {

			ItemStack item = new ItemStack(
					Material.getMaterial(config.getConfig().getString("inventory." + key + ".type")));
			if (item.getType() == null) {
				for (int x = 0; 10 > x; x++) {
					System.out.println("[Selector] DU HAR OPPGITT ETT FEIL ITEMNAVN. BYTT I CONFIG");
				}
				continue;
			}
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					config.getConfig().getString("inventory." + key + ".name")));
			List<String> lore = config.getConfig().getStringList("inventory." + key + ".lore");
			int x = 0;
			for (String key2 : lore) {
				lore.set(x, ChatColor.translateAlternateColorCodes('&', key2));
				x++;
			}

			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(config.getConfig().getInt("inventory." + key + ".slot") - 1, item);

		}
		return inv;

	}

}
