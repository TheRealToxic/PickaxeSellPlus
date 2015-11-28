package me.t0x1c.pickaxesellplus.events;

import me.t0x1c.pickaxesellplus.Main;
import me.t0x1c.pickaxesellplus.sellmanagers.AutoSell;
import me.t0x1c.pickaxesellplus.sellmanagers.QuickSell;

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
	
	AutoSell as = new AutoSell();
	QuickSell qs = new QuickSell();
	private final Main pl;
	public SellEvent(Main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Material material = Material.matchMaterial(pl.getConfig().getString("Material"));
			if(p.getItemInHand().getType() == material) {
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if((e.getClickedBlock().getState() instanceof Furnace) || (e.getClickedBlock().getState() instanceof Chest) || (e.getClickedBlock().getState() instanceof Sign) || (e.getClickedBlock().getType() == Material.ENDER_CHEST) || (e.getClickedBlock().getType() == Material.WORKBENCH)) {
						return;
					}
				}
				if(!p.hasPermission("pick.sell")) {
					p.sendMessage(colorThis(pl.getConfig().getString("Messages.No-Permissions")));
					return;
				}
				if(pl.cooldownTime.containsKey(p)) {
				    p.sendMessage(colorThis(pl.getConfig().getString("Messages.Time-Left").replaceAll("%time", String.valueOf(pl.cooldownTime.get(p)))));
				    return;
				}
				if(!pl._players.contains(p)) {
			        p.sendMessage(colorThis(pl.getConfig().getString("Messages.You-Have-Disabled")));
			        return;
				}
				if(pl.getConfig().getBoolean("Settings.AutoSellHook")) {
					as.sellPlayersItems(p);
					return;
				}
				if(!pl.getConfig().getBoolean("Settings.AutoSellHook")) {
					qs.sellItems(p);
				}
			}
		} else {
			return;
		}
	}
	
	String colorThis(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}