package org.hikuro.hikucraft.service;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;

public class PermissionService {

	private final LuckPerms luckPerms = LuckPermsProvider.get();

	public boolean hasPermission(Player player, String permission) {
		return luckPerms
				.getUserManager()
				.getUser(player.getUniqueId())
				.getCachedData()
				.getPermissionData(luckPerms.getContextManager().getStaticQueryOptions())
				.checkPermission(permission)
				.asBoolean();
	}

	public void addPermission(Player player, String permission) {
		luckPerms
				.getUserManager()
				.modifyUser(
						player.getUniqueId(),
						user -> user.data().add(PermissionNode.builder(permission).build()));
	}

	public void removePermission(Player player, String permission) {
		luckPerms
				.getUserManager()
				.modifyUser(
						player.getUniqueId(),
						user -> user.data().remove(PermissionNode.builder(permission).build()));
	}
}
