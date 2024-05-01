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
		return "hikucraft.semirp.job." + this.toString().toLowerCase();
	}

	@Override
	public String toString() {
		switch (this) {
			case ALCHEMIST:
				return "Alchemist";
			case BLACKSMITH:
				return "Blacksmith";
			case BUILDER:
				return "Builder";
			case ENCHANTER:
				return "Enchanter";
			case FARMER:
				return "Farmer";
			case FISHERMAN:
				return "Fisherman";
			case HUNTER:
				return "Hunter";
			case LUMBERJACK:
				return "Lumberjack";
			case MINER:
				return "Miner";
			default:
				return "";
		}
	}

	public static Job fromString(String job) {
		job = job.toLowerCase();
		switch (job) {
			case "alchemist":
				return ALCHEMIST;
			case "blacksmith":
				return BLACKSMITH;
			case "builder":
				return BUILDER;
			case "enchanter":
				return ENCHANTER;
			case "farmer":
				return FARMER;
			case "fisherman":
				return FISHERMAN;
			case "hunter":
				return HUNTER;
			case "lumberjack":
				return LUMBERJACK;
			case "miner":
				return MINER;
			default:
				return null;
		}
	}
}
