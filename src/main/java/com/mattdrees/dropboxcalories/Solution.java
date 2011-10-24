package com.mattdrees.dropboxcalories;

import java.util.Set;

import com.google.common.base.Preconditions;

public class Solution {
	
	public final Set<Item> items;
	
	public static final Solution NO_SOLUTION = new Solution(null);

	private Solution(Set<Item> items) {
		this.items = items;
	}
	
	public static Solution of(Set<Item> items)
	{
		Preconditions.checkNotNull(items);
		return new Solution(items);
	}
	
}
