package com.mattdrees.dropboxcalories;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import lombok.Data;

@Data
public class Item implements Comparable<Item>
{
	
	public final String name;
	public final int calories;
	
	public Item(String name, int calories) {
		Preconditions.checkNotNull(name);
		this.name = name;
		this.calories = calories;
	}
	
	@Override
	public int compareTo(Item other) {
		return Ints.compare(calories, other.calories);
	}

}
