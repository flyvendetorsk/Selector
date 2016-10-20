package trulscraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import api.ConfigAPI;
import api.IsaksApi;
import net.md_5.bungee.api.ChatColor;

public class Selector_Listener extends IsaksApi implements Listener {

	@EventHandler
	public void joinEvent(PlayerJoinEvent e) {

		if (config.getConfig().getBoolean("itemonjoin")) {
			new Selector_Handler().giveCustomItems(e.getPlayer());
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
			for (String key : config.getConfig().getConfigurationSection("inventories").getKeys(false)) {
				if (e.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',
						config.getConfig().getString("inventories." + key + ".inventory-name")))) {
					e.setCancelled(true);
					ItemStack item;
					Player p = Bukkit.getPlayer(e.getWhoClicked().getName());
					if (e.getSlot() != -999 && (item = e.getCurrentItem()) != null && item.hasItemMeta()
							&& item.getItemMeta().hasDisplayName()) {

						for (String key2 : config.getConfig().getConfigurationSection("inventories." + key + ".items")
								.getKeys(false)) {
							if (item.getItemMeta().getDisplayName()
									.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', config.getConfig()
											.getString("inventories." + key + ".items." + key2 + ".name")))) {

								if (config.getConfig()
										.get("inventories." + key + ".items." + key2 + ".function") != null) {
									if (config.getConfig()
											.get("inventories." + key + ".items." + key2 + ".function.type") != null) {
										if (config.getConfig()
												.getString("inventories." + key + ".items." + key2 + ".function.type")
												.equalsIgnoreCase("BUNGEE_TELEPORT")) {

											ByteArrayDataOutput out = ByteStreams.newDataOutput();
											out.writeUTF("Connect");
											out.writeUTF(config.getConfig().getString(
													"inventories." + key + ".items." + key2 + ".function.variable"));
											p.sendPluginMessage(Bukkit.getPluginManager().getPlugin("Selector"),
													"BungeeCord", out.toByteArray());

										} else if (config.getConfig()
												.getString("inventories." + key + ".items." + key2 + ".function.type")
												.equalsIgnoreCase("COMMAND")) {

											p.performCommand(config.getConfig().getString(
													"inventories." + key + ".items." + key2 + ".function.variable"));

										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void itemClickEvent(PlayerInteractEvent e) {
		if (e.getItem() != null) {
			if (e.getItem().getType() != null) {
				if (e.getItem().hasItemMeta()) {
					if (e.getItem().getItemMeta().hasDisplayName()) {
						for (String key : new Selector_Handler().getCustomItemNames()) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(key)) {
								Player p = e.getPlayer();
								for (String key2 : config.getConfig().getConfigurationSection("selectors")
										.getKeys(false)) {
									if (ChatColor
											.translateAlternateColorCodes('&',
													config.getConfig().getString("selectors." + key2 + ".name"))
											.equalsIgnoreCase(key)) {
										if (config.getConfig().get("selectors." + key2 + ".function") != null) {
											if (config.getConfig()
													.getString("selectors." + key2 + ".function." + ".type")
													.equalsIgnoreCase("INVENTORY")) {
												if (config.getConfig().getString(
														"selectors." + key2 + ".function." + ".variable") != null) {
													for (String key3 : config.getConfig()
															.getConfigurationSection("inventories").getKeys(false)) {
														if (key3.equalsIgnoreCase(config.getConfig().getString(
																"selectors." + key2 + ".function." + ".variable"))) {
															p.openInventory(
																	new Selector_Handler().getInventory(p, key3));
														}

													}
												}
											} else if (config.getConfig()
													.getString("selectors." + key2 + ".function." + ".type")
													.equalsIgnoreCase("COMMAND")) {
												if (config.getConfig().getString(
														"selectors." + key2 + ".function." + ".variable") != null) {
													e.getPlayer()
															.performCommand(config.getConfig()
																	.getString("selectors." + key2 + ".function."
																			+ ".variable")
																	.replaceAll("%player%", e.getPlayer().getName()));
												}
											}
										}

									}
								}
							}
						}
					}
				}
			}
		}
	}

}
