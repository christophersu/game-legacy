package com.boardgame.game;

import java.util.LinkedList;
import java.util.Queue;

import com.boardgame.game.RoundPhase.ActionBlitz;
import com.boardgame.game.RoundPhase.ActionInvest;
import com.boardgame.game.RoundPhase.ActionMove;
import com.boardgame.game.RoundPhase.EventDisplayCards;
import com.boardgame.game.RoundPhase.EventResolveCards;
import com.boardgame.game.RoundPhase.PlanPlaceTokens;
import com.boardgame.game.RoundPhase.PlanRevealTokens;
import com.boardgame.game.RoundPhase.PlanSightPower;

class RoundState {
	private Queue<RoundPhase> roundPhases;
	
	RoundState() {
		roundPhases = new LinkedList<>();
		roundPhases.add(new PlanPlaceTokens());
		roundPhases.add(new PlanRevealTokens());
		roundPhases.add(new PlanSightPower());
		roundPhases.add(new ActionBlitz());
		roundPhases.add(new ActionMove());
		roundPhases.add(new ActionInvest());
		roundPhases.add(new EventDisplayCards());
		roundPhases.add(new EventResolveCards());
	}
	
	RoundPhase getRoundPhase() {
		return roundPhases.peek();
	}
	
	void moveToNextPhase(Game game) {
		assert game != null;
		
		RoundPhase nextPhase = roundPhases.remove();
		nextPhase.prepare(game);
		roundPhases.add(nextPhase);
	}
}
