package com.boardgame.game;

import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class AssistToken extends CombatToken {
	AssistToken(boolean isSpecial, TokenString tokenString, int strength) {
		super(isSpecial, tokenString, strength, null);
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
	boolean isValidTargeting(Location source, Location target) {
		return true;
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return true;
	}
}
