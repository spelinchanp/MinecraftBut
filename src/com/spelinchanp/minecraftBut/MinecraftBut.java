package com.spelinchanp.minecraftBut;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftBut extends JavaPlugin {
	private Plugin plugin;
	
	private Settings settings;
	private ButEvent but;

	@Override
	public void onEnable() {
		// Set the plugin
		this.plugin = this;
		
		// Set the default plugin
		plugin.saveDefaultConfig();
		
		// Set the settings
		settings = new Settings(plugin);
		settings.read();
		
		//settings = new Settings(this.plugin);
		// Set the event manager
		but = new ButEvent(settings);
		
		run();
	}

	@Override
	public void onDisable() {		
	}

	public void run() {
		// Register the event listener
		getServer().getPluginManager().registerEvents(new MinecraftButListener(but), this);
		
		// Instantiate MinecraftEvent
		MinecraftEvent event = new MinecraftEvent(plugin, settings);	
		
		// Select a new MinecraftButEvent every five minutes
		Bukkit.getScheduler()
			.scheduleSyncRepeatingTask(
					plugin,
				new Runnable() {
					@Override
					public void run() {
						// Randomly select a new event
						but.setRandomButEvents(); 
						//but.butEvent = ButEvents.MobsStacked;
						
						// run MinecraftEvent
						// (all other listener-based events are run in MinecraftButListener)
						for (int i = 0; i < but.butEvents.size(); i++) {
							event.run(but.butEvents.get(i));
							
							switch (but.butEvents.get(i)) {
							case TntRain:		
								Bukkit.broadcastMessage(
										ChatColor.RED + "TNT Rain has commenced! TNT will drop every ten seconds! Slap on some leather boots to become immune!");
								break;
							case RandomBlocksWalking:	
								Bukkit.broadcastMessage(
										ChatColor.GREEN + "Grass block chaos has commenced! Every grass block you "
												+ "walk on will change to a random block!");		
								break;
							case EggsOP:
								Bukkit.broadcastMessage(
										ChatColor.YELLOW + "Thrown eggs will spawn OP items!");		
								break;
							case RandomEnchants:
								Bukkit.broadcastMessage(
										ChatColor.BLUE + "Random enchants has commenced! A random item in your inventory"
												+ " will be given a random enchantment every three seconds");
								break;
							case MobsStacked:
								Bukkit.broadcastMessage(
										ChatColor.DARK_GRAY + "Mob stacking has commenced! "
												+ "Every mob has spawns will now be stacked!");
								break;
							case GlassSkyWalker:
								Bukkit.broadcastMessage(
										ChatColor.WHITE + "Glass sky walker has commenced!");
								break;
							case EndermiteInfestedBlocks: 
								Bukkit.broadcastMessage(ChatColor.GRAY + "Endermite infested blocks has commenced!");
								break;
							case GravitizedLeaves:
								Bukkit.broadcastMessage(ChatColor.GREEN + "Gravitized leaves has commenced!");
							}
						}
					}
				}, 1, 20 * 30);

	}
}
