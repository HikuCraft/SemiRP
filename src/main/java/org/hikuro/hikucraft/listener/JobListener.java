package org.hikuro.hikucraft.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public abstract class JobListener implements Listener {
	protected final EconomyService economyService;
	protected final PermissionService permissionService;
	private final String jobPermission;

	protected JobListener(
			EconomyService economyService,
			PermissionService permissionService,
			String jobPermission) {
		this.economyService = economyService;
		this.permissionService = permissionService;
		this.jobPermission = jobPermission;
	}

	protected boolean isRightJob(Player player) {
		return this.permissionService.hasPermission(player.getUniqueId(), jobPermission);
	}
}
