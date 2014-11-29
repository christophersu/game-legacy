package com.boardgame.game;

/**
 * Represents a base on the map.
 *
 */
final class Base {
	private static final int DEFAULT_DEFENSE = 0;
	private static final boolean DEFAULT_IS_DEFENSE_ACTIVE = true;
	
	private final int size;
	private final int defense;
	
	private boolean isDefenseActive;
	
	/**
	 * Creates a new base of the given size and with default defense strength
	 * @param size  the size of the base, positive
	 * @throws IllegalArgumentException if size <= 0
	 */
	Base(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size was not positive.");
		}
		
		this.size = size;
		this.defense = DEFAULT_DEFENSE;
		this.isDefenseActive = DEFAULT_IS_DEFENSE_ACTIVE;
	}
	
	/**
	 * Creates a new base with the same properties as the given base, except the
	 * defense is the given defense.
	 * @param base  the base off which properties for the new base will be
	 * taken, not null
	 * @param defense  the defense of the new base, nonnegative
	 * @throws IllegalArgumentException if base is null
	 * @throws IllegalArgumentException if defense is negative
	 */
	Base(Base base, int defense) {
		if (base == null) {
			throw new IllegalArgumentException("Base was null.");
		}
		
		if (defense < 0) {
			throw new IllegalArgumentException("Defense was negative.");
		}
		
		this.size = base.size;
		this.defense = defense;
		this.isDefenseActive = base.isDefenseActive;
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
