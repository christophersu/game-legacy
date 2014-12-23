package com.boardgame.game;

import java.util.Stack;

class RoundPhase {
	enum Phase {
		//conditional
		
		COMBAT_END(null),
		COMBAT_RESOLUTION(COMBAT_END),
		COMBAT_BONUS(COMBAT_RESOLUTION),
		COMBAT_REVEAL(COMBAT_BONUS) {
			@Override
			void onBegin(Game game) {
				//reveal
			}
		},
		COMBAT_CARD(COMBAT_REVEAL),
		COMBAT_ASSIST(COMBAT_CARD),
		COMBAT_BEGIN(COMBAT_ASSIST),
		
		BID_END(null),
		BID_RESOLVE(BID_END) {
			@Override
			void onBegin(Game game) {
				//end bid, return
			}
		},
		BID_REVEAL(BID_RESOLVE) {
			@Override
			void onBegin(Game game) {
				//show bids
			}
		},
		BID_CHOOSE(BID_REVEAL),
		BID_START(BID_CHOOSE),
		
		THREAT_END(null),
		THREAT_RESOLVE(THREAT_END) {
			@Override
			void onBegin(Game game) {
				//set top of threat stack visible
			}
		},
		THREAT_DISPLAY(THREAT_RESOLVE) {
			@Override
			void onBegin(Game game) {
				//set top of threat stack visible
			}
		},
		THREAT_START(THREAT_DISPLAY) {
			@Override
			void onBegin(Game game) {
				//set top of threat stack visible
				//branch to bid
			}
		},
		
		//main
		ROUND_END(null) {
			@Override
			void onBegin(Game game) {
				//check if game is over
			}
		},
		ACTION_END(ROUND_END),
		ACTION_REMOVE_TOKENS(ACTION_END) {
			@Override
			void onBegin(Game game) {
				//remove other tokens
			}
		},
		ACTION_RESOLVE_TOKENS(ACTION_REMOVE_TOKENS),
		ACTION_BEGIN(ACTION_RESOLVE_TOKENS),
		PLAN_END(ACTION_BEGIN),
		PLAN_SIGHT_POWER(PLAN_END),
		PLAN_DISPLAY_TOKENS(PLAN_SIGHT_POWER) {
			@Override
			void onBegin(Game game) {
				//set tokens visible
			}
		},
		PLAN_PLACE_TOKENS(PLAN_DISPLAY_TOKENS),
		PLAN_BEGIN(PLAN_PLACE_TOKENS),
		EVENT_END(PLAN_BEGIN),
		EVENT_RESOLVE_2(EVENT_END) {
			@Override
			void onBegin(Game game) {
				//do card 2
			}
		},
		EVENT_RESOLVE_1(EVENT_RESOLVE_2) {
			@Override
			void onBegin(Game game) {
				//do card 1
			}
		},
		EVENT_RESOLVE_0(EVENT_RESOLVE_1) {
			@Override
			void onBegin(Game game) {
				//do card 0
			}
		},
		EVENT_DISPLAY(EVENT_RESOLVE_0) {
			@Override
			void onBegin(Game game) {
				//set tops of stacks visible, calculate threat total
			}
		},
		EVENT_BEGIN(EVENT_DISPLAY),
		ROUND_BEGIN(EVENT_BEGIN),
		PRE_ROUND(ROUND_BEGIN),
		PRE_GAME(PLAN_BEGIN);
		
		private final Phase nextPhase;
		
		Phase(Phase nextPhase) {
			this.nextPhase = nextPhase;
		}
		
		void onBegin(Game game) {
			//do nothing
		}
	}
	
	private Stack<Phase> returnPhases;
	private Phase currentPhase;
	
	private RoundPhase() {
		returnPhases = new Stack<>();
		currentPhase = Phase.ROUND_BEGIN;
	}
	
	RoundPhase(boolean isFirstRound) {
		this();
		
		if (isFirstRound) {
			currentPhase = Phase.PRE_GAME;
		}
		else {
			currentPhase = Phase.PRE_ROUND;
		}
	}
	
	void jump(Game game, Phase destination) {
		assert game != null;
		assert destination != null;
		assert currentPhase.nextPhase != null;
		
		returnPhases.push(currentPhase.nextPhase);
		nextHelper(game, destination);
	}
	
	Phase nextReturn() {
		assert !returnPhases.isEmpty();
		
		currentPhase = returnPhases.pop();
		
		assert currentPhase != null;
		
		return currentPhase;
	}
	
	//transitions to the next phase and returns it
	Phase next(Game game) {
		assert currentPhase.nextPhase != null;
		nextHelper(game, currentPhase.nextPhase);
		return currentPhase;
	}
	
	boolean hasStarted() {
		return currentPhase == Phase.PRE_GAME || 
				currentPhase == Phase.PRE_ROUND;
	}
	
	boolean isInPhase(Phase phase) {
		assert phase != null;
		
		return currentPhase == phase;
	}
	
	private void nextHelper(Game game, Phase next) {
		assert game != null;
		assert next != null;
		
		currentPhase = next;
		currentPhase.onBegin(game);
	}
}