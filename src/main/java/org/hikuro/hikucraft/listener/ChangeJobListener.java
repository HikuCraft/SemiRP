package org.hikuro.hikucraft.listener;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ChangeJobListener implements Listener {

	private final Map<String, String> jobPermissions = new HashMap<>();

	public ChangeJobListener() {
		jobPermissions.put("alchemist", "hikucraft.job.alchemist");
		jobPermissions.put("blacksmith", "hikucraft.job.blacksmith");
		jobPermissions.put("builder", "hikucraft.job.builder");
		jobPermissions.put("enchanter", "hikucraft.job.enchanter");
		jobPermissions.put("farmer", "hikucraft.job.farmer");
		jobPermissions.put("fisherman", "hikucraft.job.fisherman");
		jobPermissions.put("hunter", "hikucraft.job.hunter");
		jobPermissions.put("lumberjack", "hikucraft.job.lumberjack");
		jobPermissions.put("miner", "hikucraft.job.miner");
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory clickedInventory = event.getClickedInventory();
		if (clickedInventory != null && clickedInventory.getHolder() instanceof Player) {
			ItemStack clickedItem = event.getCurrentItem();
			if (clickedItem != null && clickedItem.hasItemMeta()) {
				String jobName = clickedItem.getItemMeta().getDisplayName();
				if (jobPermissions.containsKey(jobName)) {
					String permission = jobPermissions.get(jobName);
					player.sendMessage("You've chosen the job: " + jobName);
					player.closeInventory();
					Plugin plugin = Bukkit.getPluginManager().getPlugin("SemiRP");
					// Add permission to player
					player.addAttachment(plugin, permission, true);
					// Remove all other job permissions
					for (String jobPermission : jobPermissions.values()) {
						if (!jobPermission.equals(permission)) {
							player.removeAttachment(
									player.addAttachment(plugin, jobPermission, false));
						}
					}
				}
			}
		}
	}
}
