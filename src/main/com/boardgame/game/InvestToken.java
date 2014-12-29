package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.boardgame.game.Location.Terrain;

final class InvestToken extends AbstractActionToken {
	private static final int PRIORITY = 2;
	
	InvestToken(boolean isSpecial) {
		super(isSpecial, PRIORITY);
	}

	@Override
	boolean isValidTargeting(Location source, Location target) {
		assert source != null;
		assert target != null;
		
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

	@Override
	boolean actSpecifically(Game game, Location tokenLocation, Location target,
			Collection<AbstractUnit> unitsInvolved) {
		assert tokenLocation.equals(target);
		
		Player player = game.getGameState().getFactionsToPlayers().get(target.getOwner());
		
		player.moveCashFromPoolToHand(tokenLocation.getInvest() + 1);

		throw new UnsupportedOperationException("Need to implement unit creation");
	}
	
	@Override
	TokenString getTokenString() {
		return getIsSpecial() ? TokenString.INVEST_S : TokenString.INVEST;
	}
}
