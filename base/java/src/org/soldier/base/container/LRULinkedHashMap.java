package org.soldier.base.container;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wileywang
 *
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	private static final float _DEFAULT_LOAD_FACTOR = 0.75f;
	
	private int capacity;
	
	public LRULinkedHashMap(int capacity) {
		super(capacity, _DEFAULT_LOAD_FACTOR, true);
		this.capacity = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > capacity;
	}
	
}
