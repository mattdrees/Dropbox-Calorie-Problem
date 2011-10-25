package com.mattdrees.dropboxcalories;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class SumSetSolver implements Callable<SortedSet<Integer>>{

	private final int magnitude;
	private final List<Item> items;
	
	private final BiMap<Item, Round> rounds;

	public SumSetSolver(Set<Item> items, int magnitude) {
		this.items = Lists.newArrayList(items);
		Collections.sort(this.items);
		this.magnitude = magnitude;
		rounds = HashBiMap.create(items.size());
	}

	@Override
	public SortedSet<Integer> call() throws Exception {
		Round previousRound = null;
		for (Item item : items)
		{
			Round newRound = buildFromPreviousRound(previousRound, item);
			rounds.put(item, newRound);
			previousRound = newRound;
		}
		return previousRound.sums;
	}

	
	
	private Round buildFromPreviousRound(Round previousRound, Item item) {
		Round newRound = new Round(new TreeSet<Integer>());
		newRound.sums.add(item.calories);
		if (previousRound == null)
		{
			return newRound;
		}
		for (int sum : previousRound)
		{
			newRound.sums.add(sum);
			int newSum = sum + item.calories;
			if (newSum <= magnitude)
			{
				newRound.sums.add(newSum);
			}
		}
		
		return newRound;
	}

	public Set<Item> getItemsSummingTo(Integer commonSum) {
		Set<Item> solutionItems = Sets.newHashSet();
		
		//TODO: this is ugly.  Need to make nicer.
		int workingSum = commonSum;
		Round previousRound = null;
		Item previousItem = null;
		for (Item item : Lists.reverse(items))
		{
			
			if (item.calories == workingSum)
			{
				solutionItems.add(item);
				return solutionItems;
			}
			else
			{
				if (previousRound != null) {
					Round smallerRound = rounds.get(item);
					if (smallerRound.sums.contains(workingSum))
					{
						//previous item is not needed in the solution set
					}
					else
					{
						solutionItems.add(previousItem);
						workingSum -= previousItem.calories;
					}
				}
				
				previousRound = rounds.get(item);
				previousItem = item;
			}
		}
		throw new AssertionError("Shouldn't be able to get here; working sum: " + workingSum);
	}

}
