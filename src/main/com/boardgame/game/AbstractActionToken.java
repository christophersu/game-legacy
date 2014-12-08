package com.boardgame.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

abstract class AbstractActionToken {
	protected final boolean isSpecial;
	private final TokenString tokenString;
	private final Integer priority;

	private final Map<Terrain, Set<Terrain>> sourceTerrainsToValidTargetTerrains;
	
	enum TokenString {
		BLANK,
		BAD_MOVE, 
		NORMAL_MOVE, 
		MOVE_S, 
		INVEST_A, 
		INVEST_B, 
		INVEST_S, 
		BLITZ_A,  
		BLITZ_B, 
		BLITZ_S, 
		DEFENSE_A, 
		DEFENSE_B, 
		DEFENSE_S, 
		ASSIST_A,  
		ASSIST_B, 
		ASSIST_S
	}
	
	AbstractActionToken(boolean isSpecial, TokenString tokenString, 
			Integer priority) {
		assert tokenString != null;
		assert priority >= 0;
		
		this.isSpecial = isSpecial;
		this.tokenString = tokenString;
		this.priority = priority;
		this.sourceTerrainsToValidTargetTerrains = 
				findSourceTerrainsToValidTargetTerrains();
	}
	
	private Map<Terrain, Set<Terrain>> findSourceTerrainsToValidTargetTerrains() {
		Map<Terrain, Set<Terrain>> sourceTerrainsToValidTargetTerrains = 
				new HashMap<>();
		
		for (Terrain terrain : Terrain.values()) {
			Set<Terrain> targetTerrains = getValidTargetTerrains(terrain);
			sourceTerrainsToValidTargetTerrains.put(terrain, targetTerrains);
			
		}
		
		return sourceTerrainsToValidTargetTerrains;
	}
	
	protected abstract Set<Terrain> getValidTargetTerrains(Terrain terrain);
	
	Integer getPriority() {
		return priority;
	}
	
	Set<Location> findValidLocationTargets(Location sourceLocation) {
		assert sourceLocation != null;
		
		Set<Location> validLocationTargets = new HashSet<>();
		
		for (Location adjacentLocation : sourceLocation.getAdjacentLocations()) {
			Terrain sourceTerrain = sourceLocation.getTerrain();
			Set<Terrain> validTerrains = 
					sourceTerrainsToValidTargetTerrains.get(sourceTerrain);
			assert validTerrains != null;
			Terrain adjacentTerrain = adjacentLocation.getTerrain();
			
			if (validTerrains.contains(adjacentTerrain) &&
					isValidTargeting(sourceLocation, adjacentLocation)) {
				validLocationTargets.add(adjacentLocation);
			}
		}
		
		return validLocationTargets;
	}
	
	abstract boolean isValidTargeting(Location source, Location target);
	
	abstract boolean isBlitzable(boolean isBlitzSpecial);
	
	@Override
	public String toString() {
		return tokenString.toString();
	}
}
