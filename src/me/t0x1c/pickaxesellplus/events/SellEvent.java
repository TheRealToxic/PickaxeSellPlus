package me.t0x1c.pickaxesellplus.events;

import me.mrCookieSlime.QuickSell.QuickSell;
import me.t0x1c.pickaxesellplus.Main;
import me.t0x1c.pickaxesellplus.managers.AutoS;
import me.t0x1c.pickaxesellplus.managers.FileManager;
import me.t0x1c.pickaxesellplus.managers.QuickS;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SellEvent implements Listener {
	
	private final Main pl;
	public SellEvent(Main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		AutoS as = new AutoS(pl);
		QuickS qs = new QuickS(pl);
		if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Material material = Material.matchMaterial(pl.getConfig().getString("settings.material"));
			if(player.getItemInHand().getType() == material) {
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if((e.getClickedBlock().getState() instanceof Furnace) || (e.getClickedBlock().getState() instanceof Chest) || (e.getClickedBlock().getState() instanceof Sign) || (e.getClickedBlock().getType() == Material.ENDER_CHEST) || (e.getClickedBlock().getType() == Material.WORKBENCH)) {
						return;
					}
				}
				if(!player.hasPermission("pick.sell")) {
					player.sendMessage(colorThis(pl.getConfig().getString("messages.no-permissions")));
					return;
				}
				if(pl._cooldown.containsKey(player)) {
				    player.sendMessage(colorThis(pl.getConfig().getString("messages.time-left").replaceAll("%time", String.valueOf(pl._cooldown.get(player)))));
				    return;
				}
				if(!FileManager.getPlayers().getBoolean("Players." + player.getName())) {
			        player.sendMessage(colorThis(pl.getConfig().getString("messages.you-have-disabled")));
			        return;
				}
				if(pl.getConfig().getBoolean("settings.autosell-hook")) {
					if(Bukkit.getPluginManager().isPluginEnabled("AutoSell")) {
						as.sellItems(player);
						return;
					}
					player.sendMessage(colorThis(pl.getConfig().getString("messages.no-autosell")));
				}
				if(!pl.getConfig().getBoolean("settings.autosell-hook")) {
					if(Bukkit.getPluginManager().isPluginEnabled(QuickSell.getInstance())) {
						qs.sellItems(player);
						return;
					}
					player.sendMessage(colorThis(pl.getConfig().getString("messages.no-quicksell")));
				}
			}
		}
	}

	String colorThis(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}