package me.t0x1c.pickaxesellplus.managers;

import me.clip.autosell.SellHandler;
import me.clip.autosell.objects.SellResponse;
import me.clip.autosell.objects.Shop;
import me.t0x1c.pickaxesellplus.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoS {
	
	private final Main pl;
	public AutoS(Main pl) {
		this.pl = pl;
	}
	
	public void sellItems(final Player player) {
        Shop shop = SellHandler.getShop(player);
        if(shop == null) {
            return;
        }
        SellResponse response = SellHandler.sellInventory(player, shop);
        switch(response.getResponseType()) {
        case FAIL_NO_ITEMS:
            player.sendMessage(colorThis(pl.getConfig().getString("messages.no-blocks")));
            break;
        case FAIL_NO_SHOP:
            player.sendMessage(colorThis(pl.getConfig().getString("messages.no-shop")));
            break;
        case SUCCESS:
        	SellHandler.sellInventory(player, shop);
            player.sendMessage(colorThis(pl.getConfig().getString("messages.success").replaceAll("%items%", String.valueOf(response.getItemsSold())).replaceAll("%amount%", String.valueOf(response.getPrice()))));
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
            break;  
        }      
    }
	
	String colorThis(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}