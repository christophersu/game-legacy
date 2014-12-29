package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class DefenseToken extends AbstractCombatToken {
	private static final int PRIORITY = AbstractActionToken.UNUSABLE_MARKER;
	
	DefenseToken(boolean isSpecial, int strength) {
		super(isSpecial, strength, PRIORITY);
	}

	@Override
	protected Set<Terrain> getValidTargetTerrains(Terrain terrain) {
		return new HashSet<>();
	}

	@Override
	boolean isValidTargeting(Location source, Location target) {
		return source.equals(target);
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return isBlitzSpecial;
	}

	@Override
	boolean actSpecifically(Game game, Location tokenLocation, Location target,
			Collection<AbstractUnit> unitsInvolved) {
		return true;
	}
	
	@Override
	TokenString getTokenString() {
		return getIsSpecial() ? TokenString.DEFENSE_S : TokenString.DEFENSE;
	}
}
