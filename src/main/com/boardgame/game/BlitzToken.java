package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class BlitzToken extends AbstractActionToken {
	BlitzToken(boolean isSpecial, TokenString tokenString) {
		super(isSpecial, tokenString, 0);
	}

	@Override
	boolean isValidTargeting(Location source, Location target) {
		assert source != null;
		assert target != null;
		
		Faction sourceOwner = source.getOwner();
		
		assert sourceOwner != null;
		
		boolean targetHasOtherOwner = sourceOwner != target.getOwner();
		
		return targetHasOtherOwner && target.hasToken() && 
				target.getActionToken().isBlitzable(isSpecial);
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return true;
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
				validTargetTerrains.add(Terrain.LAND);
				break;
		}
		
		return validTargetTerrains;
	}

	@Override
	boolean actSpecifically(Game game, Location tokenLocation, Location target, 
			Collection<AbstractUnit> unitsInvolved) {
		game.removeTokenHelper(target);
		game.removeTokenHelper(tokenLocation);
		
		return true;
	}
}

