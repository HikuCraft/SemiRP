package org.hikuro.hikucraft.listener;

import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.hikuro.hikucraft.model.Job;
import org.hikuro.hikucraft.service.PermissionService;

public class ChangeJobListener implements Listener {
	private final PermissionService permissionService;

	public ChangeJobListener(PermissionService permissionService) {
		this.permissionService = permissionService;
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
			Job job = Job.fromString(clickedItem.getItemMeta().getDisplayName());
			if (job == null) return;
			Optional.of(job.permission())
					.ifPresent(
							permission -> {
								player.sendMessage("You've chosen the job: " + job);
								player.closeInventory();
								manageJobPermissions(player, permission);
							});
		}
	}

	private void manageJobPermissions(Player player, String permission) {
		// Remove all previous job permissions
		for (Job job : Job.values()) {
			permissionService.removePermission(player, job.permission());
		}
		// Add the new job permission
		permissionService.addPermission(player, permission);
	}
}
