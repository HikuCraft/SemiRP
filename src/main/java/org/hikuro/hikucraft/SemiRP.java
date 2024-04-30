package org.hikuro.hikucraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.hikuro.hikucraft.listener.MinerJobListener;
import org.hikuro.hikucraft.service.EconomyService;

public class SemiRP extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("SemiRP has been enabled.");

		// Services
		EconomyService economyService = new EconomyService();

		// Register Listeners
		getServer().getPluginManager().registerEvents(new MinerJobListener(economyService), this);
	}

	@Override
	public void onDisable() {
		getLogger().info("SemiRP has been disabled.");
	}
}
