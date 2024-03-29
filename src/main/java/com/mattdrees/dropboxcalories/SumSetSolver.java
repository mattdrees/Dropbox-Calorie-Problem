package com.mattdrees.dropboxcalories;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.Callable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class SumSetSolver implements Callable<SortedSet<Integer>>{

	private final int magnitude;
	private final List<Item> items;
	
	private final RoundBuildingStrategy strategy;
	
	final BiMap<Item, Round> rounds;

	public SumSetSolver(Set<Item> items, int magnitude, RoundBuildingStrategy strategy) {
		this.strategy = strategy;
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
		    if (item.calories <= magnitude)
		        //otherwise, there's no point to considering the item
		    {
    			Round newRound = strategy.buildFromPreviousRound(previousRound, item, magnitude);
    			rounds.put(item, newRound);
    			previousRound = newRound;
		    }
		}
		return previousRound == null ? ImmutableSortedSet.<Integer>of() : previousRound.sums;
	}

	

	public Set<Item> getItemsSummingTo(Integer targetSum) {
		Set<Item> solutionItems = Sets.newHashSet();
		
		//TODO: this is ugly.  Need to make nicer.
		int workingSum = targetSum;
		List<Item> reversedItems = Lists.reverse(items);
		int previousItemIndex = 0;
        for (Item item : reversedItems)
		{
			if (item.calories == workingSum)
			{
				solutionItems.add(item);
				return solutionItems;
			}
			else
			{
			    assert rounds.get(item).sums.contains(workingSum);
	            previousItemIndex++;
	            assert previousItemIndex < reversedItems.size();
			    Item previousItem = reversedItems.get(previousItemIndex);
			    Round previousRound = rounds.get(previousItem);
				
				if (!previousRound.sums.contains(workingSum))
				/* this means that the workingSum cannot be achieved without the current item */
                {
					solutionItems.add(item);
					workingSum -= item.calories;
				}
			}
		}
		throw new AssertionError("Shouldn't be able to get here; working sum: " + workingSum);
	}

}
