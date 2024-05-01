package org.hikuro.hikucraft;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hikuro.hikucraft.listener.*;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class SemiRP extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("SemiRP has been enabled.");

		// Services
		EconomyService economyService = new EconomyService();
		PermissionService permissionService = new PermissionService();

		// Register Commands
		getCommand("job")
				.setExecutor(
						(commandSender, command, s, strings) -> {
							this.openJobMenu((Player) commandSender);
							return false;
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
		Map<String, String> jobPermissions = new HashMap<>();
		jobPermissions.put("alchemist", "hikucraft.job.alchemist");
		jobPermissions.put("blacksmith", "hikucraft.job.blacksmith");
		jobPermissions.put("builder", "hikucraft.job.builder");
		jobPermissions.put("enchanter", "hikucraft.job.enchanter");
		jobPermissions.put("farmer", "hikucraft.job.farmer");
		jobPermissions.put("fisherman", "hikucraft.job.fisherman");
		jobPermissions.put("hunter", "hikucraft.job.hunter");
		jobPermissions.put("lumberjack", "hikucraft.job.lumberjack");
		jobPermissions.put("miner", "hikucraft.job.miner");

		Inventory gui = Bukkit.createInventory(player, 9, "Choose Your Job");
		for (String jobName : jobPermissions.keySet()) {
			ItemStack jobItem = createJobItem(jobName);
			gui.addItem(jobItem);
		}
		player.openInventory(gui);
	}

	private ItemStack createJobItem(String jobName) {
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(jobName);
		item.setItemMeta(meta);
		return item;
	}
}
