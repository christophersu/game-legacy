package com.boardgame.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class OneToOneMap<K, V> {
	private Map<K, V> keyToValue;
	private Map<V, K> valueToKey;
	
	public OneToOneMap() {
		keyToValue = new HashMap<>();
		valueToKey = new HashMap<>();
	}
	
	void put(K key, V value) {
		keyToValue.put(key, value);
		valueToKey.put(value, key);
	}
	
	V getValue(K key) {
		return keyToValue.get(key);
	}
	
	K getKey(V value) {
		return valueToKey.get(value);
	}
	
	boolean containsKey(K key) {
		return keyToValue.containsKey(key);
	}
	
	boolean containsValue(V value) {
		return valueToKey.containsValue(value);
	}
	
	int size() {
		assert keyToValue.size() == valueToKey.size();
		return keyToValue.size();
	}

	Set<V> values() {
		return valueToKey.keySet();
	}
}
