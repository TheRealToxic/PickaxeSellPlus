package me.t0x1c.pickaxesellplus;

import java.util.HashMap;

import me.t0x1c.pickaxesellplus.commands.PickCommand;
import me.t0x1c.pickaxesellplus.events.JoinEvent;
import me.t0x1c.pickaxesellplus.events.SellEvent;
import me.t0x1c.pickaxesellplus.managers.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public HashMap<Player, Integer> _cooldown = new HashMap<Player, Integer>();
	
	public void onEnable() {
		FileManager.setup(this);
		loadEvent();
		loadCommand();
	    saveDefaultConfig();
	    reloadConfig();
	    for(Player all : Bukkit.getOnlinePlayers()) {
	    	if(!FileManager.getPlayers().contains("Players" + all.getName())) {
	    		FileManager.getPlayers().set("Players." + all.getName(), true);
	    		FileManager.savePlayers();
	    		FileManager.reloadPlayers();
			}
	    }
	}
	
	void loadCommand() {
		getCommand("pick").setExecutor(new PickCommand(this));
	}
	
	void loadEvent() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SellEvent(this), this);
		pm.registerEvents(new JoinEvent(), this);
	}
}