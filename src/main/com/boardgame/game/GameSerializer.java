package com.boardgame.game;

import java.io.IOException;

import com.cedarsoftware.util.io.JsonWriter;;

public class GameSerializer {
	String serializeGameStateToJson(Game game) {
		try {
			return JsonWriter.objectToJson(game.getGameState());
		} catch (IOException e) {
			// TODO Throw better error here.
			e.printStackTrace();
			return null;
		}
	}
}
