package me.t0x1c.pickaxesellplus.sellmanagers;

import me.mrCookieSlime.QuickSell.Shop;
import me.t0x1c.pickaxesellplus.Main;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class QuickSell {
	
	Main pl = new Main();
	
	public void sellItems(final Player p) {
		Shop shop = Shop.getHighestShop(p);
		if(shop != null) {
			if(!pl.getConfig().getBoolean("Settings.Cooldown")) {
				shop.sellall(p, "");
			} else {
				shop.sellall(p, "");
				if(p.hasPermission("pick.bypass.cooldown")) {
					return;
				} else {
					pl.cooldownTime.put(p, pl.getConfig().getInt("CooldownTime"));
					pl.cooldownTask.put(p, new BukkitRunnable() {
						public void run() {
							pl.cooldownTime.put(p, pl.cooldownTime.get(p) - 1);
							if(pl.cooldownTime.get(p) == 0) {
								pl.cooldownTime.remove(p);
								pl.cooldownTask.remove(p);
								cancel();
							}
						}
					});
					if(pl.cooldownTime.containsKey(p)) {
						pl.cooldownTask.get(p).runTaskTimer(pl, 20, 20);
					} else {
						return;
					}
				}
				return;
			}
		}
	}
}