package me.t0x1c.pickaxesellplus.managers;

import me.mrCookieSlime.QuickSell.Shop;
import me.t0x1c.pickaxesellplus.Main;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class QuickS {
	
	private final Main pl;
	public QuickS(Main pl) {
		this.pl = pl;
	}
	
	public void sellItems(final Player player) {
		Shop shop = Shop.getHighestShop(player);
		if(shop == null) {
			return;
		}
		shop.sellall(player, "");
		if(!pl.getConfig().getBoolean("settings.cooldown")) {
			return;
		}
		if(player.hasPermission("pick.bypass.cooldown")) {
			return;
		}
		pl._cooldown.put(player, pl.getConfig().getInt("settings.cooldown-time"));
		new BukkitRunnable() {
			public void run() {
				if(!pl._cooldown.containsKey(player)) {
					pl._cooldown.remove(player);
					cancel();
					return;
				}
				if(pl._cooldown.get(player) == 0) {
					pl._cooldown.remove(player);
					cancel();
					return;
				}
				pl._cooldown.put(player, pl._cooldown.get(player) - 1);
			}
		}.runTaskTimer(pl, 20, 20);
	}
}