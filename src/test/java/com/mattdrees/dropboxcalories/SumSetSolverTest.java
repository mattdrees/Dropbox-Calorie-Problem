package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

public class SumSetSolverTest
{
    @Test
    public void testGetItemsSummingTo()
    {
        
        int notused = 10;
        Item gumdrop = new Item("gumdrop", 10);
        Item yorkPatty = new Item("york patty", 25);
        Set<Item> items = ImmutableSet.of(gumdrop, yorkPatty);
        SumSetSolver solver = new SumSetSolver(items, notused);
        
        solver.rounds.put(gumdrop, new Round(ImmutableSortedSet.of(10)));
        solver.rounds.put(yorkPatty, new Round(ImmutableSortedSet.of(10, 25, 35)));
        
        assertThat(solver.getItemsSummingTo(35), equalTo(items));
        
        Set<Item> yorkPattySet = ImmutableSet.of(yorkPatty);
        assertThat(solver.getItemsSummingTo(25), equalTo(yorkPattySet));
        
        Set<Item> gumdropSet = ImmutableSet.of(gumdrop);
        assertThat(solver.getItemsSummingTo(10), equalTo(gumdropSet));
    }

}
