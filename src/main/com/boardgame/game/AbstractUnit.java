package com.boardgame.game;

class AbstractUnit {
	static AbstractUnit create(String unitString) {
		switch (unitString) {
			case "Infantry" :
				return new InfantryUnit();
			default : 
				String message = "Unknown unit: " + unitString;
				throw new AssertionError(message, null);
		}
	}
}
