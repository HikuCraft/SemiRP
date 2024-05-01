package org.hikuro.hikucraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hikuro.hikucraft.listener.*;
import org.hikuro.hikucraft.service.EconomyService;

public class SemiRP extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("SemiRP has been enabled.");

		// Services
		EconomyService economyService = new EconomyService();

		// Register Commands
		getCommand("job")
				.setExecutor(
						new CommandExecutor() {
							@Override
							public boolean onCommand(
									CommandSender commandSender,
									Command command,
									String s,
									String[] strings) {
								// TODO: Implement command
								return false;
							}
						});

		// Register Listeners
		PluginManager pluginManager = getServer().getPluginManager();
		JobListener[] jobListeners = {
			new AlchemistJobListener(economyService),
			new BlacksmithJobListener(economyService),
			new BuilderJobListener(economyService),
			new EnchanterJobListener(economyService),
			new FarmerJobListener(economyService),
			new FishermanJobListener(economyService),
			new HunterJobListener(economyService),
			new LumberjackJobListener(economyService),
			new MinerJobListener(economyService)
		};

		for (JobListener jobListener : jobListeners) {
			pluginManager.registerEvents(jobListener, this);
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("SemiRP has been disabled.");
	}
}
