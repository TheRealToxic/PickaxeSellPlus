package me.t0x1c.pickaxesellplus.events;

import me.t0x1c.pickaxesellplus.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
	
	private final Main pl;
	public JoinEvent(Main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		pl._players.add(e.getPlayer());
	}
}