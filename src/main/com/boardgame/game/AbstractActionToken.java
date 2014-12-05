package com.boardgame.game;

class AbstractActionToken {
	protected final boolean isSpecial;
	private final TokenString tokenString;
	
	enum TokenString {
		BAD_MOVE, 
		NORMAL_MOVE, 
		MOVE_S, 
		INVEST, 
		INVEST_S, 
		BLITZ, 
		BLITZ_S, 
		DEFENSE, 
		DEFENSE_S, 
		ASSIST, 
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
