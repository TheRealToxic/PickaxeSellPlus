package me.t0x1c.pickaxesellplus.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {
	
	static FileConfiguration players;
	static File pfile;
	
	public static void setup(Plugin plugin) {
		pfile = new File(plugin.getDataFolder(), "players.yml");
		players = YamlConfiguration.loadConfiguration(pfile);
		if(!pfile.exists()) {
			players.options().header("If players has enabled their PickaxeSell then it'll set it to true else it'll be false");
			try {
				players.save(pfile);
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(colorThis("&4Error: &7Could not create players.yml"));
			}
		}
	}
	
	public static FileConfiguration getPlayers() {
		return players;
	}
	
	public static void savePlayers() {
		try {
			players.save(pfile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reloadPlayers() {
		players = YamlConfiguration.loadConfiguration(pfile);
	}
	
	static String colorThis(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}