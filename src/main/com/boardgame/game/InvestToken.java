package com.boardgame.game;

import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class InvestToken extends AbstractActionToken {
	InvestToken(boolean isSpecial, TokenString tokenString) {
		super(isSpecial, tokenString, 2);
	}

	@Override
	boolean isValidTargeting(Location source, Location target) {
		return source.equals(target);
	}

	@Override
	boolean isBlitzable(boolean isBlitzSpecial) {
		return true;
	}

	@Override
	protected Set<Terrain> getValidTargetTerrains(Terrain terrain) {
		return new HashSet<>();
	}
}
