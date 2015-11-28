package me.t0x1c.pickaxesellplus;

import java.util.ArrayList;
import java.util.HashMap;

import me.t0x1c.pickaxesellplus.commands.PickCommand;
import me.t0x1c.pickaxesellplus.events.JoinEvent;
import me.t0x1c.pickaxesellplus.events.SellEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	
	public HashMap<Player, Integer> cooldownTime = new HashMap<Player, Integer>();
	public HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<Player, BukkitRunnable>();
	public ArrayList<Player> _players = new ArrayList<Player>();
	
	public void onEnable() {
		loadEvent();
		loadCommand();
	    saveDefaultConfig();
	    getConfig().options().copyDefaults(true);
	    for(Player player : Bukkit.getOnlinePlayers()) {
	    	_players.add(player);
	    }
	}
	
	public void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			_players.remove(player);
		}
	}
	
	void loadCommand() {
		getCommand("pick").setExecutor(new PickCommand(this));
	}
	
	void loadEvent() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SellEvent(this), this);
		pm.registerEvents(new JoinEvent(this), this);
	}
}