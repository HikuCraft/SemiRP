package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class FishermanJobListener extends JobListener {
	private static final Map<EntityType, Double> fishValues = new EnumMap<>(EntityType.class);

	public FishermanJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.fisherman");
		fishValues.put(EntityType.COD, 3.0);
		fishValues.put(EntityType.SALMON, 4.0);
		fishValues.put(EntityType.PUFFERFISH, 6.0);
		fishValues.put(EntityType.TROPICAL_FISH, 5.0);
		// TODO: Add more fish values
	}

	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
			Optional.ofNullable(fishValues.get(event.getCaught().getType()))
					.ifPresent(
							value -> {
								this.economyService.deposit(player, value);
								player.sendMessage(
										"You caught a "
												+ event.getCaught().getType()
												+ " worth "
												+ value
												+ " coins.");
							});
		}
	}
}
