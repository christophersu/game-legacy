package com.boardgame.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.boardgame.game.Location.BaseStrength;
import com.boardgame.game.Location.Terrain;

public class LocationTest {
	private Location location;
	
	@Before
	public void initialize() {
		location = new Location("location name", Terrain.LAND, 
				BaseStrength.NORMAL, 1, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullName() {
		new Location(null, Terrain.LAND, BaseStrength.NORMAL, 1, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullTerrain() {
		new Location("location name", null, BaseStrength.NORMAL, 1, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullBaseStrength() {
		new Location("location name", Terrain.LAND, null, 1, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNegativeSupply() {
		new Location("location name", Terrain.LAND, BaseStrength.NORMAL, -1, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeInvest() {
		new Location("location name", Terrain.LAND, BaseStrength.NORMAL, 1, -2);
	}
	
	@Test
	public void testZeroSupply() {
		new Location("location name", Terrain.LAND, BaseStrength.NORMAL, 0, 2);
	}
	
	@Test
	public void testZeroInvest() {
		new Location("location name", Terrain.LAND, BaseStrength.NORMAL, 1, 0);
	}
	
	@Test
	public void testNoUnits() {
		assertFalse(location.hasUnits());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullUnit() {
		location.addUnit(null);
	}
	
	@Test
	public void testNullOwner() {
		assertNull(location.getOwner());
	}
	
	@Test(expected=IllegalStateException.class) 
	public void testAddUnitNoOwner() {
		location.addUnit(new InfantryUnit());
	}
	
	@Test
	public void testAddOneUnit() {
		location.changeOwner(new Player());
		location.addUnit(new InfantryUnit());
		assertTrue(location.hasUnits());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testChangeOwnerWithUnitsLeft() {
		location.changeOwner(new Player());
		location.addUnit(new InfantryUnit());
		location.changeOwner(null);
	}
	
	@Test
	public void testRemoveNonExistentUnit() {
		location.changeOwner(new Player());
		assertFalse(location.removeUnit(new InfantryUnit()));
	}
	
	@Test
	public void testRemoveAddedUnit() {
		AbstractUnit unit = new InfantryUnit();
		location.changeOwner(new Player());
		location.addUnit(unit);
		assertTrue(location.removeUnit(unit));
		assertFalse(location.hasUnits());
		assertNull(location.getOwner());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPlaceNullToken() {
		location.placeActionToken(null);
	}
	
	@Test
	public void testPlaceToken() {
		assertNull(location.getActionToken());
		location.changeOwner(new Player());
		AbstractActionToken token = new BlitzToken(true);
		location.placeActionToken(token);
		assertEquals(token, location.getActionToken());
	}
}
