package me.protectChicken.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.protectChicken.main.Main;


public class Commands implements CommandExecutor{
	private Chicken chicken;

	public Commands(Main plugin){
		plugin.getCommand("chickMe").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player)sender; 

		switch(cmd.getName()){
			
			case "chickMe":
				try {
					Player p = Bukkit.getPlayer(args[0]);
					
					chicken = (Chicken)p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
					chicken.setLeashHolder(p);
				} catch(Exception e) {
					player.sendMessage("You must put a player to chick");
					throw new ArrayIndexOutOfBoundsException("You must put a player to chick");
					
				}
				
				
			break;
		}
		return false;
	}
	public Chicken getChicken() {
		return chicken; 
	}

}
