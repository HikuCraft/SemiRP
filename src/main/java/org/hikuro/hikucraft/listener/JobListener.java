package org.hikuro.hikucraft.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.hikuro.hikucraft.service.EconomyService;

public abstract class JobListener implements Listener {
	protected final EconomyService economyService;
	private final String jobPermission;

	protected JobListener(EconomyService economyService, String jobPermission) {
		this.economyService = economyService;
		this.jobPermission = jobPermission;
	}

	protected boolean isRightJob(Player player) {
		return !player.hasPermission(jobPermission);
	}
}
