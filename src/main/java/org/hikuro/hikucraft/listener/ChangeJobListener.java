package org.hikuro.hikucraft.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.hikuro.hikucraft.service.PermissionService;

public class ChangeJobListener implements Listener {
	private final PermissionService permissionService;
	private final Map<String, String> jobPermissions = new HashMap<>();

	public ChangeJobListener(PermissionService permissionService) {
		this.permissionService = permissionService;
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
		ItemStack clickedItem =
				Optional.ofNullable(event.getClickedInventory())
						.filter(inventory -> inventory.getHolder() instanceof Player)
						.map(inventory -> inventory.getItem(event.getSlot()))
						.orElse(null);

		if (clickedItem != null && clickedItem.hasItemMeta()) {
			String jobName = clickedItem.getItemMeta().getDisplayName();
			Optional.ofNullable(jobPermissions.get(jobName))
					.ifPresent(
							permission -> {
								player.sendMessage("You've chosen the job: " + jobName);
								player.closeInventory();
								manageJobPermissions(player, permission);
							});
		}
	}

	private void manageJobPermissions(Player player, String permission) {
		// Remove all previous job permissions
		for (Map.Entry<String, String> entry : jobPermissions.entrySet()) {
			permissionService.removePermission(player, entry.getValue());
		}
		// Add the new job permission
		permissionService.addPermission(player, permission);
	}
}
