package com.mattdrees.dropboxcalories;

import java.util.concurrent.ExecutionException;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

public class Benchmark extends SimpleBenchmark {

	Problem largeProblem;
	Problem mediumProblem;
	Problem smallProblem;
	
	
	@Override
	protected void setUp() throws Exception {
	    mediumProblem = new Problem();
        mediumProblem.items.add(new Item("free-lunch", 802));
        mediumProblem.items.add(new Item("mixed-nuts", 421));
        mediumProblem.items.add(new Item("orange-juice", 143));
        mediumProblem.items.add(new Item("heavy-ddr-session", -302));
        mediumProblem.items.add(new Item("cheese-snacks", 137));
        mediumProblem.items.add(new Item("cookies", 316));
        mediumProblem.items.add(new Item("mexican-coke", 150));
        mediumProblem.items.add(new Item("dropballers-basketball", -611));
        mediumProblem.items.add(new Item("coding-six-hours", -466));
        mediumProblem.items.add(new Item("riding-scooter", -42));
        mediumProblem.items.add(new Item("rock-band", -195));
        mediumProblem.items.add(new Item("playing-drums", -295));
        

	    smallProblem = new Problem();
        smallProblem.items.add(new Item("red-bull", 140));
        smallProblem.items.add(new Item("pushup", -10));
        smallProblem.items.add(new Item("hopscotch", -130));
        
        largeProblem = new Problem();
        for (int i = 0; i < 23; i++)
        {
        	largeProblem.items.add(new Item("food" + i, 27));
        }
        for (int i = 0; i < 27; i++)
        {
        	largeProblem.items.add(new Item("excercise" + i, -23));
        }
	}
	
	public void timeLargeProblemWithNoSolution(int reps) throws InterruptedException, ExecutionException
	{
		ProblemSolver solver = new ProblemSolver(largeProblem);
		for (int i = 0; i < reps; i++)
		{
			solver.solve();
		}
	}
	
	public void timeMediumProblemWithSolution(int reps) throws InterruptedException, ExecutionException
	{
		ProblemSolver solver = new ProblemSolver(mediumProblem);
		for (int i = 0; i < reps; i++)
		{
			solver.solve();
		}
	}
	
	public void timeSmallProblemWithSolution(int reps) throws InterruptedException, ExecutionException
	{
		ProblemSolver solver = new ProblemSolver(smallProblem);
		for (int i = 0; i < reps; i++)
		{
			solver.solve();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Runner.main(Benchmark.class, args);
	}

}
