package me.protectChicken.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
import org.bukkit.inventory.ItemStack;


public class Listeners implements Listener{
	boolean onLeash;
	
	public Listeners() {
		onLeash = true;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void leashBreak(EntityUnleashEvent e) {
		onLeash = false;
		if(!e.getReason().equals(UnleashReason.HOLDER_GONE))	{
			new Thread(new Runnable() {
				long time1 = System.currentTimeMillis();
				long time2 = System.currentTimeMillis();
				long difference = 10;
				long originalDiff = 10;
				public void run(){
					while(time2 - time1 < 30000) {
						difference -= time2 - time1;
						difference = (int)difference/1000;
						if(difference != originalDiff) {
							long showtime = 30 + difference;
							if(showtime == 30 || showtime == 25 || showtime == 20) {
								e.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.YELLOW + "Time Left: " + showtime);
	
							} else if(showtime < 20 && showtime > 9) {
								e.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.GOLD + "Time Left: " + showtime);
							} else if(showtime < 10) {
								e.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.RED + "Time Left: " + showtime);
							}
							
							originalDiff = difference;
						}
						if(onLeash) {
							return;
						}
						
						time2 = System.currentTimeMillis();
	
				}
					try {
						e.getEntity().getWorld().getPlayers().get(0).setHealth(0);
					} catch(Exception e) {
						throw new IllegalStateException("lol u died and i have a bug im too lazy to fix.");
					}
	
				 
			}}).start();
		}
		
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void leashed(PlayerLeashEntityEvent e) {
		if(e.getEntity().getType().equals(EntityType.CHICKEN)) {
			onLeash = true;
			e.getPlayer().sendMessage(ChatColor.GREEN +"Saved a chicken.");
		}
	}
	@EventHandler(priority = EventPriority.LOW) 
	public void mobSpawning(EntitySpawnEvent e) {
		
		if(e.getEntityType().equals(EntityType.ZOMBIE)) {
			Zombie entity = (Zombie)e.getEntity();
			entity.getEquipment().setHelmet(new ItemStack(Material.EGG));

		}
		else if(e.getEntityType().equals(EntityType.SKELETON)) {
			Skeleton entity = (Skeleton)e.getEntity();
			entity.getEquipment().setHelmet(new ItemStack(Material.EGG));

		}
		else if(e.getEntityType().equals(EntityType.WITHER_SKELETON)) {
			WitherSkeleton entity = (WitherSkeleton)e.getEntity();  
			entity.getEquipment().setHelmet(new ItemStack(Material.EGG));

		}
		
	}
	@EventHandler
	public void playerDamaged(EntityDamageEvent e) {
		if(e.getEntity().getType().equals(EntityType.PLAYER)) {
			
			e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0, 5, 0), EntityType.EGG);

		}
	}
	@EventHandler
	public void projectileLaunch(ProjectileLaunchEvent e) {
		if(e.getEntityType().equals(EntityType.ARROW)) {
			e.getEntity().getShooter().launchProjectile(Egg.class, e.getEntity().getVelocity().normalize());	
			e.setCancelled(true);
		}

	}
	@EventHandler
	public void projectileHit(ProjectileHitEvent e) {
		Location loc = e.getEntity().getLocation().add(0, 3, 0);
		if(e.getEntity().getShooter() instanceof Ghast
				|| e.getEntity().getShooter() instanceof Blaze) {
			for(int i = 0; i < 4; i++) {
				e.getEntity().getWorld().spawnEntity(loc, EntityType.EGG);
			}
		}
	}
	@EventHandler
	public void entityDie(EntityDeathEvent e) {
		if(!e.getEntityType().equals(EntityType.PLAYER)) {
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.EGG));
		}
		if(e.getEntityType().equals(EntityType.BAT)) {
			e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
		}
	}

}
