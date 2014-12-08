package com.boardgame.game;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.boardgame.game.AbstractActionToken.TokenString;
import com.boardgame.game.AbstractUnit.UnitString;

final class GameState {
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
	private boolean hasCombatBonusBeenUsed;
	private boolean hasSightPowerBeenUsed;
	private final Map<UnitString, AbstractUnit> unitStringsToUnits;
	private final Map<TokenString, AbstractActionToken> tokenStringsToTokens; 
	
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
			boolean hasCombatBonusBeenUsed, boolean hasSightPowerBeenUsed,
			Map<UnitString, AbstractUnit> unitStringsToUnits,
			Map<TokenString, AbstractActionToken> tokenStringsToTokens) {
		assert locations != null;
		assert factionsToPlayers != null;
		assert turnOrder != null;
		assert tieBreakingOrder != null;
		assert specialTokenOrder != null;
		assert specialTokensPerPosition != null;
		assert factionsToSupplies != null;
		assert factionsToNumBases != null;
		assert eventCards1Stack != null;
		assert eventCards1Discard != null;
		assert eventCards2Stack != null;
		assert eventCards2Discard != null;
		assert eventCards3Stack != null;
		assert eventCards3Discard != null;
		assert threatCardsStack != null;
		assert threatCardsDiscard != null;
		assert combatCards != null;
		assert threatLevel >= 0;
		assert round >= 0;
		assert unitStringsToUnits != null;
		assert tokenStringsToTokens != null;
		
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
		
		this.unitStringsToUnits = unitStringsToUnits;
		this.tokenStringsToTokens = tokenStringsToTokens;
	}
	
	int getNumFactions() {
		return factionsToPlayers.size();
	}
	
	Set<Faction> getFactions() {
		return factionsToPlayers.keySet();
	}
	
	List<Location> getLocations() {
		return locations;
	}
	
	Map<Faction, Player> getFactionsToPlayers() {
		return factionsToPlayers;
	}

	List<Faction> getTurnOrder() {
		return turnOrder;
	}

	List<Faction> getTieBreakingOrder() {
		return tieBreakingOrder;
	}

	List<Faction> getSpecialTokenOrder() {
		return specialTokenOrder;
	}
	
	Faction getFactionInSpecialTokenOrderPosition(int position) {
		assert position >= 0 && position < specialTokenOrder.size();
		return specialTokenOrder.get(position);
	}
	
	Faction getFactionInTurnOrderPosition(int position) {
		assert position >= 0 && position < specialTokenOrder.size();
		return turnOrder.get(position);
	}
	
	Faction getFactionInTieBreakingOrderPosition(int position) {
		assert position >= 0 && position < specialTokenOrder.size();
		return tieBreakingOrder.get(position);
	}
	
	int getTurnOrderPosition(Faction faction) {
		assert faction != null;
		assert turnOrder.contains(faction);
		
		return turnOrder.indexOf(faction);
	}

	List<Integer> getSpecialTokensPerPosition() {
		return specialTokensPerPosition;
	}

	Map<Faction, Integer> getFactionsToSupplies() {
		return factionsToSupplies;
	}

	Map<Faction, Integer> getFactionsToNumBases() {
		return factionsToNumBases;
	}

	int getThreatLevel() {
		return threatLevel;
	}

	int getRound() {
		return round;
	}

	Queue<AbstractEventCard> getEventCards1Stack() {
		return eventCards1Stack;
	}

	Queue<AbstractEventCard> getEventCards1Discard() {
		return eventCards1Discard;
	}

	Queue<AbstractEventCard> getEventCards2Stack() {
		return eventCards2Stack;
	}

	Queue<AbstractEventCard> getEventCards2Discard() {
		return eventCards2Discard;
	}

	Queue<AbstractEventCard> getEventCards3Stack() {
		return eventCards3Stack;
	}

	Queue<AbstractEventCard> getEventCards3Discard() {
		return eventCards3Discard;
	}

	Queue<AbstractThreatCard> getThreatCardsStack() {
		return threatCardsStack;
	}

	Queue<AbstractThreatCard> getThreatCardsDiscard() {
		return threatCardsDiscard;
	}

	List<AbstractCombatCard> getCombatCards() {
		return combatCards;
	}

	boolean getHasCombatBonusBeenUsed() {
		return hasCombatBonusBeenUsed;
	}

	boolean getHasSightPowerBeenUsed() {
		return hasSightPowerBeenUsed;
	}
	
	void setHasSightPowerBeenUsed(boolean hasSightPowerBeenUsed) {
		this.hasSightPowerBeenUsed = hasSightPowerBeenUsed;
	}
	
	Map<UnitString, AbstractUnit> getUnitStringsToUnits() {
		return unitStringsToUnits;
	}
	
	Map<TokenString, AbstractActionToken> getTokenStringsToTokens() {
		return tokenStringsToTokens;
	}
	
	int getNumSpecialTokensForFaction(Faction faction) {
		assert faction != null;
		
		int specialTokenPosition = specialTokenOrder.indexOf(faction);
		return specialTokensPerPosition.get(specialTokenPosition);
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
		private Map<UnitString, AbstractUnit> unitStringsToUnits;
		private Map<TokenString, AbstractActionToken> tokenStringsToTokens;
		
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
		
		Builder setUnitStringsToUnits(Map<UnitString, AbstractUnit> unitStringsToUnits) {
			this.unitStringsToUnits = unitStringsToUnits;
			return this;
		}
		
		Builder setTokenStringsToTokens(Map<TokenString, AbstractActionToken> tokenStringsToTokens) {
			this.tokenStringsToTokens = tokenStringsToTokens;
			return this;
		}
		
		GameState build() {
			return new GameState(locations, factionsToPlayers, turnOrder, 
					tieBreakingOrder, specialTokenOrder, specialTokensPerPosition, 
					factionsToSupplies, factionsToNumBases, threatLevel, round, 
					eventCards1Stack, eventCards1Discard, eventCards2Stack,
					eventCards2Discard, eventCards3Stack, eventCards3Discard, 
					threatCardsStack, threatCardsDiscard, combatCards,
					hasCombatBonusBeenUsed, hasSightPowerBeenUsed, 
					unitStringsToUnits, tokenStringsToTokens);
		}
	}
}
