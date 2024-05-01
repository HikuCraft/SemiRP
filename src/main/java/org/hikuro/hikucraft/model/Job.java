package org.hikuro.hikucraft.model;

public enum Job {
	ALCHEMIST,
	BLACKSMITH,
	BUILDER,
	ENCHANTER,
	FARMER,
	FISHERMAN,
	HUNTER,
	LUMBERJACK,
	MINER;

	public String permission() {
		return "hikucraft.job." + this.toString().toLowerCase();
	}

	@Override
	public String toString() {
		switch (this) {
			case FARMER:
				return "Farmer";
			case MINER:
				return "Miner";
			case BUILDER:
				return "Builder";
			case HUNTER:
				return "Hunter";
			case FISHERMAN:
				return "Fisherman";
			case LUMBERJACK:
				return "Lumberjack";
			case BLACKSMITH:
				return "Blacksmith";
			case ENCHANTER:
				return "Enchanter";
			case ALCHEMIST:
				return "Alchemist";
			default:
				return "";
		}
	}

	public static Job fromString(String job) {
		job = job.toLowerCase();
		switch (job) {
			case "farmer":
				return FARMER;
			case "miner":
				return MINER;
			case "builder":
				return BUILDER;
			case "hunter":
				return HUNTER;
			case "fisherman":
				return FISHERMAN;
			case "lumberjack":
				return LUMBERJACK;
			case "blacksmith":
				return BLACKSMITH;
			case "enchanter":
				return ENCHANTER;
			case "alchemist":
				return ALCHEMIST;
			default:
				return null;
		}
	}
}
