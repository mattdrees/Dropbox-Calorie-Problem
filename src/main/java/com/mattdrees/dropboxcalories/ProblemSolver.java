package com.mattdrees.dropboxcalories;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;


public class ProblemSolver {

	final Problem problem;

	public ProblemSolver(Problem problem) {
		this.problem = problem;
	}
	
	public Solution solve() throws InterruptedException, ExecutionException
	{
		Solution trivial = findTrivialSolution();
		if (trivial != null)
			return trivial;
		
		Set<Item> negativeItems = Sets.newHashSet(Iterables.filter(problem.items, new Predicate<Item>() {
			public boolean apply(Item input) {
				return input.calories < 0;
			}
		}));
		
		Set<Item> positiveItems = Sets.newHashSet(Iterables.filter(problem.items, new Predicate<Item>() {
			public boolean apply(Item input) {
				return input.calories > 0;
			}
		}));
		
		int negativeSumMagnitude = - sum(negativeItems);
		int positiveSumMagnitude = sum(positiveItems);
		
		int smallerMagnitude = Math.min(negativeSumMagnitude, positiveSumMagnitude);
		
		SumSetSolver negativeSolver = new SumSetSolver(invert(negativeItems), smallerMagnitude);
		SumSetSolver positiveSolver = new SumSetSolver(positiveItems, smallerMagnitude);
		
		Integer commonSum = findCommonSum(negativeSolver, positiveSolver);
		if (commonSum == null)
			return Solution.NO_SOLUTION;
		
		Set<Item> negativeSolutionItems = negativeSolver.getItemsSummingTo(commonSum);
		Set<Item> positiveSolutionItems = positiveSolver.getItemsSummingTo(commonSum);
		
		return Solution.of(Sets.union(negativeSolutionItems, positiveSolutionItems));
	}

	/**
	 * the problem approach won't find solutions of a single item of 0 calories.  I'm not such an item is valid for
	 * input to the problem, but just in case, I'll handle it.
	 */
	private Solution findTrivialSolution() {
		for (Item item : problem.items)
		{
			if (item.calories == 0)
				return Solution.of(ImmutableSet.of(item));
		}
		return null;
	}

	private Integer findCommonSum(SumSetSolver negativeSolver,
			SumSetSolver positiveSolver) throws InterruptedException,
			ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<SortedSet<Integer>> negativeSumsFuture = executorService.submit(negativeSolver);
		Future<SortedSet<Integer>> positiveSumsFuture = executorService.submit(positiveSolver);
		
		SortedSet<Integer> negativeSums = negativeSumsFuture.get();
		SortedSet<Integer> positiveSums = positiveSumsFuture.get();
		
		Integer commonSum = findCommonSum(negativeSums, positiveSums);
		return commonSum;
	}

	private Integer findCommonSum(SortedSet<Integer> negativeSums, SortedSet<Integer> positiveSums) {
		Iterator<Integer> negativeIterator = negativeSums.iterator();
		Iterator<Integer> positiveIterator = positiveSums.iterator();
		
		if (!negativeIterator.hasNext())
			return null;
		if (!positiveIterator.hasNext())
			return null;
		
		Integer currentNegative = negativeIterator.next();
		Integer currentPositive = positiveIterator.next();
		
		while (true)
		{
			if (currentNegative.equals(currentPositive))
				return currentNegative;
			else if (currentNegative.compareTo(currentPositive) < 0)
			{
				if (!negativeIterator.hasNext())
					return null;
				else
					currentNegative = negativeIterator.next();
			}
			else //currentNegative.compareTo(currentPositive) > 0
			{
				if (!positiveIterator.hasNext())
					return null;
				else
					currentPositive = positiveIterator.next();
			}
		}
	}

	private Set<Item> invert(Set<Item> negativeItems) {
		Set<Item> invertedItems = Sets.newHashSet();
		for (Item item : negativeItems)
		{
			invertedItems.add(new Item(item.name, -item.calories));
		}
		return invertedItems;
	}

	private int sum(Set<Item> items) {
		int sum = 0;
		for (Item item: items)
		{
			sum += item.calories;
		}
		return sum;
	}
	
}
