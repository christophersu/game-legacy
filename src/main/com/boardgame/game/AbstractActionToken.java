package com.boardgame.game;

abstract class AbstractActionToken implements Comparable<AbstractActionToken> {
	protected final boolean isSpecial;
	private final TokenString tokenString;
	private final int priority;
	
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
	
	AbstractActionToken(boolean isSpecial, TokenString tokenString, 
			int priority) {
		assert tokenString != null;
		assert priority >= 0;
		
		this.isSpecial = isSpecial;
		this.tokenString = tokenString;
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		return tokenString.toString();
	}
	
	@Override
	public int compareTo(AbstractActionToken other) {
		return this.priority - other.priority;
	}
}
