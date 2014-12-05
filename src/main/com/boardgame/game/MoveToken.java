package com.boardgame.game;

final class MoveToken extends CombatToken {
	MoveToken(boolean isSpecial, TokenString tokenString, int strength) {
		super(isSpecial, tokenString, strength);
	}
}
