package me.protectChicken.main;

import org.bukkit.plugin.java.JavaPlugin;

import me.protectChicken.listeners.Listeners;
import me.protectChicken.commands.Commands;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable(){
		new Commands(this);
		getServer().getPluginManager().registerEvents(new Listeners(), this);

	}
}
