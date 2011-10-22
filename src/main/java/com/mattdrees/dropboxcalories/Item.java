package com.mattdrees.dropboxcalories;

import lombok.Data;

@Data
public class Item {
	
	public final String name;
	public final int calories;
	
	public Item(String name, int calories) {
		this.name = name;
		this.calories = calories;
	}

}
