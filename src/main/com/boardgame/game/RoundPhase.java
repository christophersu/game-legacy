package com.boardgame.game;


abstract class RoundPhase {
	abstract void prepare(Game game);

	boolean getCanPlaceToken() {
		return false;
	}
	
	boolean getCanRemoveToken() {
		return false;
	}
	
	boolean getCanSwitchToken() {
		return false;
	}

	final static class EventDisplayCards extends RoundPhase {
		@Override
		void prepare(Game game) {
			throw new UnsupportedOperationException();
		}
	}
	
	final static class EventResolveCards extends RoundPhase {
		@Override
		void prepare(Game game) {
			throw new UnsupportedOperationException();
		}
	}
	
	final static class PlanPlaceTokens extends RoundPhase {
		@Override
		void prepare(Game game) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		boolean getCanPlaceToken() {
			return true;
		}
	}
	
	final static class PlanRevealTokens extends RoundPhase {
		@Override
		void prepare(Game game) {
			game.buildActionLocationsQueue();
		}
	}
	
	final static class PlanSightPower extends RoundPhase {
		@Override
		void prepare(Game game) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		boolean getCanSwitchToken() {
			return true;
		}
	}
	
	final static class ActionPhase extends RoundPhase {
		@Override
		void prepare(Game game) {
			throw new UnsupportedOperationException();
		}
	}
}
