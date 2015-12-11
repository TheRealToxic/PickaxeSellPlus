package me.t0x1c.pickaxesellplus.commands;

import me.t0x1c.pickaxesellplus.Main;
import me.t0x1c.pickaxesellplus.managers.FileManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PickCommand implements CommandExecutor {
	
	private final Main pl;
	public PickCommand(Main pl) {
		this.pl = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(colorThis(pl.getConfig().getString("messages.only-players")));
			return true;
		}
		Player player = (Player) sender;
	    if(cmd.getName().equalsIgnoreCase("pick")) {
			if(args.length == 0) {
				if(player.hasPermission("pick.sell")) {
					if(!FileManager.getPlayers().getBoolean("Players." + player.getName())) {
						FileManager.getPlayers().set("Players." + player.getName(), true);
						FileManager.savePlayers();
						player.sendMessage(colorThis(pl.getConfig().getString("messages.enabled")));
						return true;
					}
					FileManager.getPlayers().set("Players." + player.getName(), false);
					FileManager.savePlayers();
					player.sendMessage(colorThis(pl.getConfig().getString("messages.disabled")));
					return true;
				}
				player.sendMessage(colorThis(pl.getConfig().getString("messages.no-permissions")));
				return true;
			}
			if(args[0].equalsIgnoreCase("help")) {
				if(player.hasPermission("pick.staff")) {
					player.sendMessage(colorThis("&8&m--------------------------------------------"));
			        player.sendMessage("");
			        player.sendMessage(colorThis("&2PickaxeSell&a+ &8» &7Commands:"));
			        player.sendMessage(colorThis("&a/pick &8- &7Disable or Enable PickaxeSell."));
			        player.sendMessage(colorThis("&a/pick reload &8- &7Reload config.yml."));
			        player.sendMessage(colorThis("&a/pick help &8- &7Displays this."));
			        player.sendMessage(colorThis("&a/pick cooldown check &8- &7Display if cooldown is on or off."));
			        player.sendMessage("");
			        player.sendMessage(colorThis("&8&m--------------------------------------------"));
			        return true;
				}
				player.sendMessage(colorThis(pl.getConfig().getString("messages.no-permissions")));
			    return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				if(player.hasPermission("pick.reload")) {
					pl.reloadConfig();
			        player.sendMessage(colorThis(pl.getConfig().getString("messages.reload")));
			        return true;
				}
				player.sendMessage(colorThis(pl.getConfig().getString("messages.no-permissions")));
				return true;
			}
			if(args[0].equalsIgnoreCase("cooldown")) {
				if(!player.hasPermission("pick.cooldown")) {
					player.sendMessage(colorThis(pl.getConfig().getString("messages.no-permissions")));
					return true;
				}
				if(args.length < 2) {
					player.sendMessage(colorThis(pl.getConfig().getString("messages.wrong-cooldown-command")));
					return true;
				}
				if(args.length < 3) {
					if(args[1].equalsIgnoreCase("check")) {
						if(!pl.getConfig().getBoolean("settings.cooldown")) {
							player.sendMessage(colorThis(pl.getConfig().getString("messages.cooldown-false")));
							return true;
						}
						player.sendMessage(colorThis(pl.getConfig().getString("messages.cooldown-true")));
					}
				}
			}
	    }
		return true;
	}
	
	String colorThis(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}