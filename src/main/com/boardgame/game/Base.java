package com.boardgame.game;

/**
 * Represents a base on the map.
 *
 */
class Base {
	private final int size;
	private final int defense;
	
	private boolean isDefenseActive;
	
	/**
	 * Creates a new base of the given size and with the given defense strength
	 * @param size  the size of the base, positive
	 * @param defense  the defense strength of the base, nonegative
	 * @throws IllegalArgumentException if size <= 0
	 * @throws IllegalArgumentException if defense < 0
	 */
	Base(int size, int defense) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size was not positive.");
		}
		
		if (defense < 0) {
			throw new IllegalArgumentException("Defense was negative.");
		}
		
		this.size = size;
		this.defense = defense;
		this.isDefenseActive = true;
	}
	
	/**
	 * Sets whether the base's defense is active or not.
	 * @param active  whether or not the defense will be set active
	 */
	void setIsDefenseActive(boolean active) {
		this.isDefenseActive = active;
	}
	
	int getSize() {
		return size;
	}
	
	int getDefense() {
		return defense;
	}
	
	boolean getIsDefenseActive() {
		return isDefenseActive;
	}
}
