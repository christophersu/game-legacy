package com.boardgame.game;

class CombatToken extends AbstractActionToken {
	protected int strength;
	
	CombatToken(boolean isSpecial, TokenString tokenString, int strength) {
		super(isSpecial, tokenString);
		
		this.strength = strength;
	}
}
