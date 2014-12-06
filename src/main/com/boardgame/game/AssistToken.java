package com.boardgame.game;

final class AssistToken extends CombatToken {
	AssistToken(boolean isSpecial, TokenString tokenString, int strength) {
		super(isSpecial, tokenString, strength, Integer.MAX_VALUE);
	}
}
