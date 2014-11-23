package com.boardgame.game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a location on the game board.
 * 
 */
abstract class Location {
	private final String name;
	private final Terrain terrain;
	private final BaseStrength baseStrength;
	private final int supply;
	private final int invest;
	
	private final Collection<AbstractUnit> units;
	private Player owner;
	private AbstractActionToken actionToken;
	
	enum Terrain {
		LAND,
		SEA,
		PORT
	}
	
	enum BaseStrength {
		NONE,
		NORMAL,
		STRONG
	}
	
	/**
	 * Creates a new location with the given name, given supply, and given 
	 * invest potential.
	 * @param name  the name of this location, not null
	 * @param terrain  the terrain of this location, not null
	 * @param baseStrength  the strength of a base at this location, not null
	 * @param supply  the amount this location contributes to the owner's 
	 * supply, nonnegative
	 * @param invest  the amount of investment potential for the owner, 
	 * nonnegative
	 */
	Location(String name, Terrain terrain, BaseStrength baseStrength, 
			int supply, int invest) {
		assert(name != null);
		assert(terrain != null);
		assert(baseStrength != null);
		assert(supply >= 0);
		assert(invest >= 0);
		
		this.name = name;
		this.terrain = terrain;
		this.baseStrength = baseStrength;
		this.supply = supply;
		this.invest = invest;
		
		units = new ArrayList<AbstractUnit>();
		owner = null;
		actionToken = null;
	}
	
	/**
	 * Adds the given unit to this location. The location must be owned by 
	 * somebody.
	 * @param unit  the unit to be added, not null
	 */
	void addUnit(AbstractUnit unit) {
		assert(unit != null);
		assert(owner != null);
		
		boolean result = units.add(unit);
		
		assert(result);
	}
	
	/**
	 * Removes the given unit from this location
	 * @param unit  the unit to be removed, not null
	 * @return whether or not the unit was removed
	 */
	boolean removeUnit(AbstractUnit unit) {
		assert(unit != null);
		
		boolean result = units.remove(unit);
		
		if (units.isEmpty()) {
			owner = null;
		}
		
		return result;
	}
	
	/**
	 * Returns whether or not this location has units on it
	 * @return whether or not this location has units on it
	 */
	boolean hasUnits() {
		return !units.isEmpty();
	}
	
	/**
	 * Sets the owner of this location to be the given owner
	 * @param owner  the next owner of this location, null means no owner
	 */
	void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * Returns the owner of this location
	 * @return the owner of this location, null means no owner
	 */
	Player getOwner() {
		return owner;
	}
	
	/**
	 * Places the action token at this location to the given one. The location
	 * must be owned by somebody.
	 * @param actionToken  the action token that will be at this location, not
	 * null
	 */
	void placeActionToken(AbstractActionToken actionToken) {
		assert(actionToken != null);
		assert(owner != null);
		
		this.actionToken = actionToken;
	}
	
	/**
	 * Returns the action token at this location, or null if there is none
	 * @return the action token at this location, or null if there is none
	 */
	AbstractActionToken getActionToken() {
		return this.actionToken;
	}
	
	/**
	 * Removes the action token at this location, if there is one.
	 */
	void removeActionToken() {
		actionToken = null;
	}
	
	/**
	 * Returns the name of this location, not null
	 * @return the name of this location, not null
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Returns the terrain of this location, not null
	 * @return the terrain of this location, not null
	 */
	Terrain getTerrain() {
		return terrain;
	}
	
	/**
	 * Returns the strength of the base at this location, not null
	 * @return the strength of the base at this location, not null
	 */
	BaseStrength getBaseStrength() {
		return baseStrength;
	}
	
	/**
	 * Returns the supply of this location, nonnegative
	 * @return the supply of this location, nonnegative
	 */
	int getSupply() {
		return supply;
	}
	
	/**
	 * Returns the invest potential of this location, nonnegative
	 * @return the invest potential of this location, nonnegative
	 */
	int getInvest() {
		return invest;
	}
}
