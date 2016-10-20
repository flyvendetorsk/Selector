package trulscraft;

import java.util.ArrayList;
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

	public void giveCustomItems(Player p) {
		for (String key : config.getConfig().getConfigurationSection("selectors").getKeys(false)) {
			ItemStack item = new ItemStack(
					Material.getMaterial(config.getConfig().getString("selectors." + key + ".item")));
			if (item.getType() == null) {
				for (int x = 0; 10 > x; x++) {
					System.out.println("[Selector] DU HAR OPPGITT ETT FEIL ITEMNAVN. BYTT I CONFIG");
				}
				return;
			}
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					config.getConfig().getString("selectors." + key + ".name")));
			List<String> lore = config.getConfig().getStringList("selectors." + key + ".lore");
			int x = 0;
			for (String key2 : lore) {
				lore.set(x, ChatColor.translateAlternateColorCodes('&', key2));
				x++;
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			p.getInventory().setItem((config.getConfig().getInt("selectors." + key + ".slot") - 1), item);
		}

	}

	public ArrayList<String> getCustomItemNames() {
		ArrayList<String> kake = new ArrayList<>();
		for (String key : config.getConfig().getConfigurationSection("selectors").getKeys(false)) {
			kake.add(ChatColor.translateAlternateColorCodes('&',
					config.getConfig().getString("selectors." + key + ".name")));
		}
		return kake;
	}

	public Inventory getInventory(Player p, String key2) {
		Inventory inv = Bukkit.createInventory(p,
				9 * config.getConfig().getInt("inventories." + key2 + ".inventory-size-rows"),
				ChatColor.translateAlternateColorCodes('&',
						config.getConfig().getString("inventories." + key2 + ".inventory-name")));
		for (String key : config.getConfig().getConfigurationSection("inventories." + key2 + ".items").getKeys(false)) {

			ItemStack item = new ItemStack(Material
					.getMaterial(config.getConfig().getString("inventories." + key2 + ".items." + key + ".type")));
			if (item.getType() == null) {
				for (int x = 0; 10 > x; x++) {
					System.out.println("[Selector] DU HAR OPPGITT ETT FEIL ITEMNAVN. BYTT I CONFIG");
				}
				continue;
			}
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					config.getConfig().getString("inventories." + key2 + ".items." + key + ".name")));
			List<String> lore = config.getConfig().getStringList("inventories." + key2 + ".items." + key + ".lore");
			int x = 0;
			for (String key5 : lore) {
				lore.set(x, ChatColor.translateAlternateColorCodes('&', key5));
				x++;
			}

			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(config.getConfig().getInt("inventories." + key2 + ".items." + key + ".slot") - 1, item);

		}
		return inv;

	}

}
