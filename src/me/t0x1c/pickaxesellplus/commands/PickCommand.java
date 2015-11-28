package me.t0x1c.pickaxesellplus.commands;

import me.t0x1c.pickaxesellplus.Main;

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
			sender.sendMessage(colorThis(pl.getConfig().getString("Messages.OnlyPlayers")));
			return true;
		}
		Player p = (Player) sender;
	    if(cmd.getName().equalsIgnoreCase("pick")) {
			if(args.length == 0) {
				if(p.hasPermission("pick.sell")) {
					if(!pl._players.contains(p)) {
						pl._players.add(p);
						p.sendMessage(colorThis(pl.getConfig().getString("Messages.Enabled")));
						return true;
					}
					if(pl._players.contains(p)) {
						pl._players.remove(p);
						p.sendMessage(colorThis(pl.getConfig().getString("Messages.Disabled")));
						return true;
					}
				} else {
					p.sendMessage(colorThis(pl.getConfig().getString("Messages.No-Permissions")));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("help")) {
				if(p.hasPermission("pick.staff")) {
					p.sendMessage("§8§l§m--------------------------------------------");
			        p.sendMessage(" ");
			        p.sendMessage("§4§lPickaxeSell §8» §7§lCommands:");
			        p.sendMessage("§c§l/pick §8- §7Disable or Enable PickaxeSell.");
			        p.sendMessage("§c§l/pick reload §8- §7Reload config.yml.");
			        p.sendMessage("§c§l/pick help §8- §7Displays this.");
			        p.sendMessage("§c§l/pick cooldown check §8- §7Display if cooldown is on or off.");
			        p.sendMessage("§c§l/pick cooldown on §8- §7Turn on cooldown.");
			        p.sendMessage("§c§l/pick cooldown off §8- §7Turn off cooldown.");
			        p.sendMessage(" ");
			        p.sendMessage("§8§l§m--------------------------------------------");
				} else if(!p.hasPermission("pick.staff")) {
			        p.sendMessage(colorThis(pl.getConfig().getString("Messages.No-Permissions")));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				if(p.hasPermission("pick.reload")) {
					pl.reloadConfig();
			        p.sendMessage(colorThis(pl.getConfig().getString("Messages.Reload")));
				} else if(!p.hasPermission("pick.reload")) {
					p.sendMessage(colorThis(pl.getConfig().getString("Messages.No-Permissions")));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("cooldown")) {
				if(!p.hasPermission("pick.cooldown")) {
					p.sendMessage(colorThis(pl.getConfig().getString("Messages.No-Permissions")));
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(colorThis(pl.getConfig().getString("Messages.Wrong-Cooldown-Command")));
					return true;
				}
				if(args.length < 3) {
					if(args[1].equalsIgnoreCase("check")) {
						if(!pl.getConfig().getBoolean("Settings.Cooldown")) {
							p.sendMessage(colorThis(pl.getConfig().getString("Messages.Cooldown-False")));
							return true;
						}
						if(pl.getConfig().getBoolean("Settings.Cooldown")) {
							p.sendMessage(colorThis(pl.getConfig().getString("Messages.Cooldown-True")));
						}
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