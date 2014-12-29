package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class AssistToken extends AbstractCombatToken {
	private static final int PRIORITY = AbstractActionToken.UNUSABLE_MARKER;
	
	AssistToken(boolean isSpecial, int strength) {
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
				validTargetTerrains.add(Terrain.LAND);
				break;
		}
		
		return validTargetTerrains;
	}
	
	@Override
	boolean isUsableDuringCombat() {
		return true;
	}

	@Override
	boolean isValidTargeting(Location source, Location target) {
		return true;
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return true;
	}

	@Override
	boolean actSpecifically(Game game, Location tokenLocation, Location target,
			Collection<AbstractUnit> unitsInvolved) {
		throw new UnsupportedOperationException();
		//make sure unitsInvolved is empty or contains every non routed unit
	}
	
	@Override
	TokenString getTokenString() {
		return getIsSpecial() ? TokenString.ASSIST_S : TokenString.ASSIST;
	}
}
