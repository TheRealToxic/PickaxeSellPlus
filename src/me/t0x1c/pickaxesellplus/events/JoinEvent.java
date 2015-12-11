package me.t0x1c.pickaxesellplus.events;

import me.t0x1c.pickaxesellplus.managers.FileManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(!FileManager.getPlayers().contains("Players" + player.getName())) {
			FileManager.getPlayers().set("Players." + player.getName(), true);
		}
	}
}