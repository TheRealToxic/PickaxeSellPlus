package me.t0x1c.pickaxesellplus.sellmanagers;

import me.clip.autosell.SellHandler;
import me.clip.autosell.objects.SellResponse;
import me.clip.autosell.objects.Shop;
import me.t0x1c.pickaxesellplus.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSell {
	
	Main pl = new Main();
	
	public void sellPlayersItems(final Player p) {
        Shop shop = SellHandler.getShop(p);
        if(shop == null) {
            return;
        }
        SellResponse response = SellHandler.sellInventory(p, shop);
        switch(response.getResponseType()) {
        case FAIL_NO_ITEMS:
            p.sendMessage(colorThis(pl.getConfig().getString("Messages.NoBlocks")));
            break;
        case FAIL_NO_SHOP:
            p.sendMessage(colorThis(pl.getConfig().getString("Messages.NoShop")));
            break;
        case SUCCESS:
            p.sendMessage(colorThis(pl.getConfig().getString("Messages.Success").replaceAll("%items%", String.valueOf(response.getItemsSold())).replaceAll("%amount%", String.valueOf(response.getPrice()))));
            if(!pl.getConfig().getBoolean("Settings.Cooldown")) {
            	SellHandler.sellInventory(p, shop);
				break;
			} else {
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
			}
            break;  
        }      
    }
	
	String colorThis(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}