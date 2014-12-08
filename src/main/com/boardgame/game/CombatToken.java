package com.boardgame.game;

class CombatToken extends AbstractActionToken {
	protected int strength;
	
	CombatToken(boolean isSpecial, TokenString tokenString, int strength, 
			Integer priority) {
		super(isSpecial, tokenString, priority);
		
		this.strength = strength;
	}
}
