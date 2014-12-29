package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class MoveToken extends AbstractCombatToken {
	private static final int PRIORITY = 1;
	
	MoveToken(boolean isSpecial, int strength) {
		super(isSpecial, strength, PRIORITY);
	}

	@Override
	protected Set<Terrain> getValidTargetTerrains(Terrain terrain) {
		assert terrain != null;
		
		Set<Terrain> validTargetTerrains = new HashSet<>();
		
		switch (terrain) {
			case LAND :
				validTargetTerrains.add(Terrain.LAND);
				break;
			case PORT :
				validTargetTerrains.add(Terrain.SEA);
				break;
			case SEA :
				validTargetTerrains.add(Terrain.SEA);
				break;
		}
		
		return validTargetTerrains;
	}
	
	@Override
	Set<Location> findAccessibleTargets(Location source) {
		if (source.getTerrain() == Terrain.LAND) {
			return BoardOperations.findAdjacentAndShipAccessibleLocations(source);	
		}
	
		return super.findAccessibleTargets(source);
	}

	@Override
	boolean isValidTargeting(Location source, Location target) {
		return true;
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return false;
	}

	@Override
	boolean actSpecifically(Game game, Location tokenLocation, Location target, 
			Collection<AbstractUnit> unitsInvolved) {
		Faction sourceOwner = tokenLocation.getOwner();
		Faction targetOwner = target.getOwner();
		
		boolean success = true;
		
		for (AbstractUnit unit : unitsInvolved) {
			if (unit.getIsRouted()) {
				success = false;
			}
		}
		
		if (success && !game.isCombatOccurring()) {
			if (sourceOwner != targetOwner && target.hasUnits()) {
				game.beginCombat(unitsInvolved, tokenLocation, target);
				success = true;
			}
			else {
				success = game.move(tokenLocation, target, unitsInvolved);
			}
		}
		
		return success;
	}
	
	@Override
	TokenString getTokenString() {
		if (getIsSpecial()) {
			return TokenString.MOVE_S;
		}
		
		return getStrength() < 0 ? TokenString.BAD_MOVE : TokenString.NORMAL_MOVE;
	}
}
