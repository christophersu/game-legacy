package com.boardgame.game;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class GameState {
	private final List<Location> locations;
	private final Map<Faction, Player> factionsToPlayers;
	private final List<Faction> turnOrder;
	private final List<Faction> tieBreakingOrder;
	private final List<Faction> specialTokenOrder;
	private final List<Integer> specialTokensPerPosition;
	private final Map<Faction, Integer> factionsToSupplies;
	private final Map<Faction, Integer> factionsToNumBases;
	private final int threatLevel;
	private final int round;
	private final Queue<AbstractEventCard> eventCards1Stack;
	private final Queue<AbstractEventCard> eventCards1Discard;
	private final Queue<AbstractEventCard> eventCards2Stack;
	private final Queue<AbstractEventCard> eventCards2Discard;
	private final Queue<AbstractEventCard> eventCards3Stack;
	private final Queue<AbstractEventCard> eventCards3Discard;
	private final Queue<AbstractThreatCard> threatCardsStack;
	private final Queue<AbstractThreatCard> threatCardsDiscard;
	private final List<AbstractCombatCard> combatCards;
	private final boolean hasCombatBonusBeenUsed;
	private final boolean hasSightPowerBeenUsed;
	
	private GameState(List<Location> locations, 
			Map<Faction, Player> factionsToPlayers, List<Faction> turnOrder, 
			List<Faction> tieBreakingOrder, List<Faction> specialTokenOrder,
			List<Integer> specialTokensPerPosition, 
			Map<Faction, Integer> factionsToSupplies, 
			Map<Faction, Integer> factionsToNumBases,
			int threatLevel, int round, 
			Queue<AbstractEventCard> eventCards1Stack,
			Queue<AbstractEventCard> eventCards1Discard, 
			Queue<AbstractEventCard> eventCards2Stack,
			Queue<AbstractEventCard> eventCards2Discard,
			Queue<AbstractEventCard> eventCards3Stack,
			Queue<AbstractEventCard> eventCards3Discard, 
			Queue<AbstractThreatCard> threatCardsStack,
			Queue<AbstractThreatCard> threatCardsDiscard, 
			List<AbstractCombatCard> combatCards,
			boolean hasCombatBonusBeenUsed, boolean hasSightPowerBeenUsed) {
		if (locations == null || factionsToPlayers == null || turnOrder == null || 
				tieBreakingOrder == null || specialTokenOrder == null ||
				specialTokensPerPosition == null || 
				factionsToSupplies == null || factionsToNumBases == null ||
				eventCards1Stack == null || eventCards1Discard == null || 
				eventCards2Stack == null || eventCards2Discard == null || 
				eventCards3Stack == null || eventCards3Discard == null || 
				threatCardsStack == null || threatCardsDiscard == null || 
				combatCards == null) {
			throw new IllegalArgumentException("A parameter is null.");
		}
		
		if (threatLevel < 0 || round < 0) {
			throw new IllegalArgumentException("A parameter is negative.");
		}
		
		this.locations = locations;
		this.factionsToPlayers = factionsToPlayers;
		this.turnOrder = turnOrder;
		this.tieBreakingOrder = tieBreakingOrder;
		this.specialTokenOrder = specialTokenOrder;
		this.specialTokensPerPosition = specialTokensPerPosition;
		this.factionsToSupplies = factionsToSupplies;
		this.factionsToNumBases = factionsToNumBases;
		this.threatLevel = threatLevel;
		this.round = round;
		this.eventCards1Stack = eventCards1Stack;
		this.eventCards1Discard = eventCards1Discard;
		this.eventCards2Stack = eventCards2Stack;
		this.eventCards2Discard = eventCards2Discard;
		this.eventCards3Stack = eventCards3Stack;
		this.eventCards3Discard = eventCards3Discard;
		this.threatCardsStack = threatCardsStack;
		this.threatCardsDiscard = threatCardsDiscard;
		this.combatCards = combatCards;
		this.hasCombatBonusBeenUsed = hasCombatBonusBeenUsed;
		this.hasSightPowerBeenUsed = hasSightPowerBeenUsed;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	
	public Map<Faction, Player> getFactionsToPlayers() {
		return factionsToPlayers;
	}

	public List<Faction> getTurnOrder() {
		return turnOrder;
	}

	public List<Faction> getTieBreakingOrder() {
		return tieBreakingOrder;
	}

	public List<Faction> getSpecialTokenOrder() {
		return specialTokenOrder;
	}

	public List<Integer> getSpecialTokensPerPosition() {
		return specialTokensPerPosition;
	}

	public Map<Faction, Integer> getFactionsToSupplies() {
		return factionsToSupplies;
	}

	public Map<Faction, Integer> getFactionsToNumBases() {
		return factionsToNumBases;
	}

	public int getThreatLevel() {
		return threatLevel;
	}

	public int getRound() {
		return round;
	}

	public Queue<AbstractEventCard> getEventCards1Stack() {
		return eventCards1Stack;
	}

	public Queue<AbstractEventCard> getEventCards1Discard() {
		return eventCards1Discard;
	}

	public Queue<AbstractEventCard> getEventCards2Stack() {
		return eventCards2Stack;
	}

	public Queue<AbstractEventCard> getEventCards2Discard() {
		return eventCards2Discard;
	}

	public Queue<AbstractEventCard> getEventCards3Stack() {
		return eventCards3Stack;
	}

	public Queue<AbstractEventCard> getEventCards3Discard() {
		return eventCards3Discard;
	}

	public Queue<AbstractThreatCard> getThreatCardsStack() {
		return threatCardsStack;
	}

	public Queue<AbstractThreatCard> getThreatCardsDiscard() {
		return threatCardsDiscard;
	}

	public List<AbstractCombatCard> getCombatCards() {
		return combatCards;
	}

	public boolean isHasCombatBonusBeenUsed() {
		return hasCombatBonusBeenUsed;
	}

	public boolean isHasSightPowerBeenUsed() {
		return hasSightPowerBeenUsed;
	}
	
	static class Builder {
		private List<Location> locations;
		private Map<Faction, Player> factionsToPlayers;
		private List<Faction> turnOrder;
		private List<Faction> tieBreakingOrder;
		private List<Faction> specialTokenOrder;
		private List<Integer> specialTokensPerPosition;
		private Map<Faction, Integer> factionsToSupplies;
		private Map<Faction, Integer> factionsToNumBases;
		private int threatLevel;
		private int round;
		private Queue<AbstractEventCard> eventCards1Stack;
		private Queue<AbstractEventCard> eventCards1Discard;
		private Queue<AbstractEventCard> eventCards2Stack;
		private Queue<AbstractEventCard> eventCards2Discard;
		private Queue<AbstractEventCard> eventCards3Stack;
		private Queue<AbstractEventCard> eventCards3Discard;
		private Queue<AbstractThreatCard> threatCardsStack;
		private Queue<AbstractThreatCard> threatCardsDiscard;
		private List<AbstractCombatCard> combatCards;
		private boolean hasCombatBonusBeenUsed;
		private boolean hasSightPowerBeenUsed;
		
		Builder() {
			
		}
		
		Builder setLocations(List<Location> locations) {
			this.locations = locations;
			return this;
		}
		
		Builder setFactionsToPlayers(Map<Faction, Player> factionsToPlayers) {
			this.factionsToPlayers = factionsToPlayers;
			return this;
		}
		
		Builder setTurnOrder(List<Faction> turnOrder) {
			this.turnOrder = turnOrder;
			return this;
		}
		
		Builder setTieBreakingOrder(List<Faction> tieBreakingOrder) {
			this.tieBreakingOrder = tieBreakingOrder;
			return this;
		}
		
		Builder setSpecialTokenOrder(List<Faction> specialTokenOrder) {
			this.specialTokenOrder = specialTokenOrder;
			return this;
		}
		
		Builder setSpecialTokensPerPosition(List<Integer> specialTokensPerPosition) {
			this.specialTokensPerPosition = specialTokensPerPosition;
			return this;
		}
		
		Builder setFactionsToSupplies(Map<Faction, Integer> factionsToSupplies) {
			this.factionsToSupplies = factionsToSupplies;
			return this;
		}
		
		Builder setFactionsToNumBases(Map<Faction, Integer> factionsToNumBases) {
			this.factionsToNumBases = factionsToNumBases;
			return this;
		}
		
		Builder setThreatLevel(int threatLevel) {
			this.threatLevel = threatLevel;
			return this;
		}
		
		Builder setRound(int round) {
			this.round = round;
			return this;
		}
		
		Builder setEventCards1Stack(Queue<AbstractEventCard> eventCards1Stack) {
			this.eventCards1Stack = eventCards1Stack;
			return this;
		}
		
		Builder setEventCards1Discard(Queue<AbstractEventCard> eventCards1Discard) {
			this.eventCards1Discard = eventCards1Discard;
			return this;
		}
		
		Builder setEventCards2Stack(Queue<AbstractEventCard> eventCards2Stack) {
			this.eventCards2Stack = eventCards2Stack;
			return this;
		}
		
		Builder setEventCards2Discard(Queue<AbstractEventCard> eventCards2Discard) {
			this.eventCards2Discard = eventCards2Discard;
			return this;
		}
		
		Builder setEventCards3Stack(Queue<AbstractEventCard> eventCards3Stack) {
			this.eventCards3Stack = eventCards3Stack;
			return this;
		}
		
		Builder setEventCards3Discard(Queue<AbstractEventCard> eventCards3Discard) {
			this.eventCards3Discard = eventCards3Discard;
			return this;
		}
		
		Builder setThreatCardsStack(Queue<AbstractThreatCard> threatCardsStack) {
			this.threatCardsStack = threatCardsStack;
			return this;
		}
		
		Builder setThreatCardsDiscard(Queue<AbstractThreatCard> threatCardsDiscard) {
			this.threatCardsDiscard = threatCardsDiscard;
			return this;
		}
		
		Builder setCombatCards(List<AbstractCombatCard> combatCards) {
			this.combatCards = combatCards;
			return this;
		}
		
		Builder setHasCombatBonusBeenUsed(boolean hasCombatBonusBeenUsed) {
			this.hasCombatBonusBeenUsed = hasCombatBonusBeenUsed;
			return this;
		}
		
		Builder setHasSightPowerBeenUsed(boolean hasSightPowerBeeenUsed) {
			this.hasSightPowerBeenUsed = hasSightPowerBeeenUsed;
			return this;
		}
		
		GameState build() {
			return new GameState(locations, factionsToPlayers, turnOrder, 
					tieBreakingOrder, specialTokenOrder, specialTokensPerPosition, 
					factionsToSupplies, factionsToNumBases, threatLevel, round, 
					eventCards1Stack, eventCards1Discard, eventCards2Stack,
					eventCards2Discard, eventCards3Stack, eventCards3Discard, 
					threatCardsStack, threatCardsDiscard, combatCards,
					hasCombatBonusBeenUsed, hasSightPowerBeenUsed);
		}
	}
}
