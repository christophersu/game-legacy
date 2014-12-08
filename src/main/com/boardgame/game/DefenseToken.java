package com.boardgame.game;

import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class DefenseToken extends CombatToken {
	DefenseToken(boolean isSpecial, TokenString tokenString, int strength) {
		super(isSpecial, tokenString, strength, null);
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
}
