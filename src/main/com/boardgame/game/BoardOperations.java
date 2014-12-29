package com.boardgame.game;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import com.boardgame.game.Location.Terrain;

class BoardOperations {	
	private static final int MIN_SUPPLY_CONSIDERATION = 2;
	
	static boolean checkSupplyForMove(GameState gameState, Location source, 
			Location target, int numUnits) {
		Set<Location> supplyConstrainedLocations = new HashSet<>();
		
		for (Location location : gameState.getLocations()) {
			if (location.getOwner() == source.getOwner() && 
					location.getNumUnits() >= MIN_SUPPLY_CONSIDERATION) {
				supplyConstrainedLocations.add(location);	
			}
		}
		
		Set<Location> sortedLocations = new TreeSet<>(new Comparator<Location>() {
			@Override
			public int compare(Location o1, Location o2) {
				//descending order
				return o2.getNumUnits() - o1.getNumUnits();
			}
		});
		
		sortedLocations.addAll(supplyConstrainedLocations);
		
		Faction faction = source.getOwner();
		int supplyPosition = gameState.getFactionsToSupplyPositions().get(faction);
		//assuming sorted descending
		List<Integer> supplyLimits = gameState.getSupplyLimits().get(supplyPosition);
		
		int supplyIndex = 0;
		
		for (Location location : sortedLocations) {
			if (supplyIndex == supplyLimits.size()) {
				return false;
			}
			
			assert supplyIndex <= supplyLimits.size();
			
			int armySize = location.getNumUnits();
			
			if (location.equals(source)) {
				armySize -= numUnits;
			}
			
			if (location.equals(target)) {
				armySize += numUnits;
			}
			
			if (armySize > supplyLimits.get(supplyIndex)) {
				return false;
			}
			
			supplyIndex++;
		}
		
		return true;
	}
	
	static Set<Location> findAdjacentAndShipAccessibleLocations(Location origin) {
		assert origin != null;
		assert origin.getOwner() != null;
		assert origin.getTerrain() == Terrain.LAND;
		
		Set<Location> accessibleLocations = new HashSet<>();
		
		Queue<Location> unvisitedLocations = new LinkedList<>();
		unvisitedLocations.addAll(origin.getAdjacentLocations());
		
		while (!unvisitedLocations.isEmpty()) {
			Location current = unvisitedLocations.remove();
			accessibleLocations.add(current);
			
			//boat transport
			if (current.getTerrain() == Terrain.SEA && current.hasUnits()) {
				for (Location adjacent : current.getAdjacentLocations()) {
					if (!accessibleLocations.contains(adjacent) && 
							!unvisitedLocations.contains(adjacent)) {
						unvisitedLocations.add(adjacent);
					}
				}
			}
		}
		
		return accessibleLocations;
	}
}
