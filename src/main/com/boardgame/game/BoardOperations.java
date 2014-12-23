package com.boardgame.game;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
}
