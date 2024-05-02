package org.hikuro.hikucraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hikuro.hikucraft.api.HikuCraftAPI;
import org.hikuro.hikucraft.listener.*;
import org.hikuro.hikucraft.model.Job;
import org.hikuro.hikucraft.provider.HikuCraftProvider;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class SemiRP extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("SemiRP has been enabled.");

		// Get Services
		HikuCraftAPI hikuCraftAPI = HikuCraftProvider.get();
		EconomyService economyService = hikuCraftAPI.getEconomyService();
		PermissionService permissionService = hikuCraftAPI.getPermissionService();

		// Register Commands
		getCommand("job")
				.setExecutor(
						(commandSender, command, s, strings) -> {
							this.openJobMenu((Player) commandSender);
							return true;
						});
		getServer()
				.getPluginManager()
				.registerEvents(new ChangeJobListener(permissionService), this);

		// Register Listeners
		PluginManager pluginManager = getServer().getPluginManager();
		JobListener[] jobListeners = {
			new AlchemistJobListener(economyService, permissionService),
			new BlacksmithJobListener(economyService, permissionService),
			new BuilderJobListener(economyService, permissionService),
			new EnchanterJobListener(economyService, permissionService),
			new FarmerJobListener(economyService, permissionService),
			new FishermanJobListener(economyService, permissionService),
			new HunterJobListener(economyService, permissionService),
			new LumberjackJobListener(economyService, permissionService),
			new MinerJobListener(economyService, permissionService)
		};

		for (JobListener jobListener : jobListeners) {
			pluginManager.registerEvents(jobListener, this);
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("SemiRP has been disabled.");
	}

	private void openJobMenu(Player player) {
		Inventory gui = Bukkit.createInventory(player, 9, "Choose Your Job");
		for (Job job : Job.values()) {
			ItemStack item = new ItemStack(Material.DIAMOND);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(job.toString());
			item.setItemMeta(meta);
			gui.addItem(item);
		}
		player.openInventory(gui);
	}
}
