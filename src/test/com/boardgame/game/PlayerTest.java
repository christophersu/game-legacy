package com.boardgame.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.boardgame.game.Player.Faction;

public class PlayerTest {
	private Player player;
	
	@Before
	public void initializeTest() {
		player = new Player(Faction.RED, new HashSet<>(), new HashSet<>(), 0, 
					20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullFaction() {
		new Player(null, new HashSet<>(), new HashSet<>(), 0, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullCards() {
		new Player(Faction.RED, null, new HashSet<>(), 0, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullUnits() {
		new Player(Faction.RED, new HashSet<>(), null, 0, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeCash() {
		new Player(Faction.RED, new HashSet<>(), new HashSet<>(), -1, 20);
	}
	
	@Test
	public void testNullCombatCardElement() {
		Set<AbstractCombatCard> cards = new HashSet<>();
		cards.add(null);
		new Player(Faction.RED, cards, new HashSet<>(), 0, 20);
	}
	
	@Test
	public void testNullUnitElement() {
		Set<AbstractUnit> cards = new HashSet<>();
		cards.add(null);
		new Player(Faction.RED, new HashSet<>(), cards, 0, 20);
	}
	
	@Test
	public void testCashDoesNotExceedMax() {
		player.addCash(21);
		assertEquals(player.getCash(), 20);
	}
	
	@Test
	public void testCashDoesNotGoNegative() {
		player.removeCash(1);
		assertTrue(player.getCash() >= 0);
	}
	
	@Test
	public void testMoveCardToDiscard() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testMoveDiscardedCardToDiscard() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testUseUnit() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testUseUsedUnit() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testAddUnit() {
		throw new UnsupportedOperationException();
	}
}
