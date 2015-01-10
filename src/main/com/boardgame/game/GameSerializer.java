package com.boardgame.game;

import java.io.IOException;

import com.cedarsoftware.util.io.JsonWriter;;

public class GameSerializer {
	static String serializeGameStateToJson(GameState gs) {
		try {
			return JsonWriter.objectToJson(gs);
		} catch (IOException e) {
			// TODO Throw better error here.
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		GameState gs = GameStateLoader.load(GameStateLoader.GameType.STANDARD_6);
		System.out.println(serializeGameStateToJson(gs));
	}
}
