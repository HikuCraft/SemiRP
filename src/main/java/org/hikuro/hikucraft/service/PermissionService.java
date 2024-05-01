package org.hikuro.hikucraft.service;

import java.util.Objects;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;

public class PermissionService {
	private final LuckPerms luckPerms = LuckPermsProvider.get();

	public boolean hasPermission(Player player, String permission) {
		return Objects.requireNonNull(this.luckPerms.getPlayerAdapter(Player.class).getUser(player))
				.getCachedData()
				.getPermissionData(this.luckPerms.getContextManager().getStaticQueryOptions())
				.checkPermission(permission)
				.asBoolean();
	}

	public void addPermission(Player player, String permission) {
		PermissionNode builder = PermissionNode.builder(permission).value(true).build();
		this.luckPerms
				.getUserManager()
				.modifyUser(player.getUniqueId(), user -> user.data().add(builder));
	}

	public void removePermission(Player player, String permission) {
		PermissionNode builder = PermissionNode.builder(permission).value(false).build();
		this.luckPerms
				.getUserManager()
				.modifyUser(player.getUniqueId(), user -> user.data().add(builder));
	}
}
