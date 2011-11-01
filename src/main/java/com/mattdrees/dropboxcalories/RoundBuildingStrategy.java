package com.mattdrees.dropboxcalories;

import java.util.TreeSet;

public enum RoundBuildingStrategy
{
    TREE_SET_STRATEGY {
        @Override
        public Round buildFromPreviousRound(Round previousRound, Item item, int magnitude)
        {

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
    },
    BITSET_STRATEGY {
        @Override
        public Round buildFromPreviousRound(Round previousRound, Item item, int magnitude)
        {
            
            Round newRound = new Round(new BitSetSortedIntegerSet(magnitude));
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
    }
    
    ;
    
    
    public abstract Round buildFromPreviousRound(Round previousRound, Item item, int magnitude);
    
    
}
