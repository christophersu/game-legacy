package com.boardgame.game;

import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class MoveToken extends CombatToken {
	MoveToken(boolean isSpecial, TokenString tokenString, int strength) {
		super(isSpecial, tokenString, strength, 1);
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
	boolean isValidTargeting(Location source, Location target) {
		return true;
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return false;
	}
}
