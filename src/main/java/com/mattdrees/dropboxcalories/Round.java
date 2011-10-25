package com.mattdrees.dropboxcalories;

import java.util.Iterator;
import java.util.SortedSet;

public class Round implements Iterable<Integer>
{

	public final SortedSet<Integer> sums;

	public Round(SortedSet<Integer> sumsSet) {
		this.sums = sumsSet;
	}

	@Override
	public Iterator<Integer> iterator() {
		return sums.iterator();
	}

}
