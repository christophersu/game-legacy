package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

abstract class AbstractActionToken {
	static final int UNUSABLE_MARKER = Integer.MAX_VALUE;
	
	private final boolean isSpecial;
	private final int priority;

	private final Multimap<Terrain, Terrain> sourcesToValidTargets;
	
	enum TokenString {
		BLANK, 
		BAD_MOVE, 
		NORMAL_MOVE, 
		MOVE_S, 
		INVEST, 
		INVEST_S, 
		BLITZ, 
		BLITZ_S,
		DEFENSE, 
		DEFENSE_S, 
		ASSIST, 
		ASSIST_S
	}
	
	AbstractActionToken(boolean isSpecial, int priority) {
		this.isSpecial = isSpecial;
		this.priority = priority;
		this.sourcesToValidTargets = findSourceTerrainsToValidTargetTerrains();
	}
	
	private Multimap<Terrain, Terrain> findSourceTerrainsToValidTargetTerrains() {
		Multimap<Terrain, Terrain> sourcesToValidTargets = HashMultimap.create();
		
		for (Terrain source : Terrain.values()) {
			Set<Terrain> validTargets = getValidTargetTerrains(source);
			assert validTargets != null;
			
			sourcesToValidTargets.putAll(source, validTargets);
		}
		
		return sourcesToValidTargets;
	}
	
	abstract Set<Terrain> getValidTargetTerrains(Terrain source);
	
	Set<Location> findValidLocationTargets(Location sourceLocation) {
		assert sourceLocation != null;
		
		Set<Location> validLocationTargets = new HashSet<>();
		
		for (Location adjacentLocation : findAccessibleTargets(sourceLocation)) {
			Terrain sourceTerrain = sourceLocation.getTerrain();
			Collection<Terrain> validTerrains = sourcesToValidTargets.get(sourceTerrain);

			Terrain adjacentTerrain = adjacentLocation.getTerrain();
			
			if (validTerrains.contains(adjacentTerrain) &&
					isValidTargeting(sourceLocation, adjacentLocation)) {
				validLocationTargets.add(adjacentLocation);
			}
		}
		
		return validLocationTargets;
	}
	
	Set<Location> findAccessibleTargets(Location source) {
		return source.getAdjacentLocations();
	}
	
	boolean act(Game game, Location source, Location target, Collection<AbstractUnit> unitsInvolved) {
		assert game != null;
		assert source != null;
		assert target != null;
		assert unitsInvolved == null;
		assert source.getActionToken() == this;
		
		boolean success = false;
		
		Collection<Terrain> validTargets = sourcesToValidTargets.get(source.getTerrain());
		
		if (validTargets.contains(target.getTerrain()) && isValidTargeting(source, target)) {
			success = actSpecifically(game, source, target, unitsInvolved);
		}
		
		return success;
	}
	
	boolean isUsableDuringCombat() {
		return false;
	}
	
	abstract boolean actSpecifically(Game game, Location source, Location target, Collection<AbstractUnit> unitsInvolved);
	
	abstract boolean isValidTargeting(Location source, Location target);
	
	abstract boolean isBlitzable(boolean isBlitzSpecial);
	
	boolean getIsSpecial() {
		return isSpecial;
	}
	
	int getPriority() {
		return priority;
	}
	
	boolean isUsable() {
		return priority != UNUSABLE_MARKER;
	}
	
	int getCombatBonus() {
		return 0;
	}
	
	abstract TokenString getTokenString();
	
	@Override
	public String toString() {
		return getTokenString().toString();
	}
}
