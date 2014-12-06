package com.boardgame.game;

abstract class AbstractActionToken {
	protected final boolean isSpecial;
	private final TokenString tokenString;
	
	enum TokenString {
		BLANK,
		BAD_MOVE, 
		NORMAL_MOVE, 
		MOVE_S, 
		INVEST_A, 
		INVEST_B, 
		INVEST_S, 
		BLITZ_A,  
		BLITZ_B, 
		BLITZ_S, 
		DEFENSE_A, 
		DEFENSE_B, 
		DEFENSE_S, 
		ASSIST_A,  
		ASSIST_B, 
		ASSIST_S
	}
	
	AbstractActionToken(boolean isSpecial, TokenString tokenString) {
		this.isSpecial = isSpecial;
		this.tokenString = tokenString;
	}
	
	@Override
	public String toString() {
		return tokenString.toString();
	}
}
