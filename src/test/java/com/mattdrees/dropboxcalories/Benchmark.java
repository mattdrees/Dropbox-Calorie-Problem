package com.mattdrees.dropboxcalories;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.caliper.Param;
import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * Benchmark results posted to 
 * http://microbenchmarks.appspot.com/run/matt.drees@gmail.com/com.mattdrees.dropboxcalories.Benchmark
 */
public class Benchmark extends SimpleBenchmark {

	public enum ExecutorServiceStrategy
    {
	    CACHED_THREAD_POOL {
            @Override
            ExecutorService build()
            {
                return Executors.newCachedThreadPool();
            }
        },
	    SINGLE_THREAD {
            @Override
            ExecutorService build()
            {
                return Executors.newSingleThreadExecutor();
            }
        };
	    
	    abstract ExecutorService build();
    }

    Problem largeProblem;
	Problem mediumProblem;
	Problem smallProblem;
	
	@Param RoundBuildingStrategy roundStrategy;
    
	@Param ExecutorServiceStrategy executorStrategy;
	
	private ExecutorService executorService;
	
	
	
	@Override
	protected void setUp() throws Exception {
	    executorService = executorStrategy.build();
	    
	    buildMediumProblem();
	    buildSmallProblem();
        buildLargeProblem();
	}

    private void buildMediumProblem()
    {
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
    }
    
    private void buildSmallProblem()
    {
        smallProblem = new Problem();
        smallProblem.items.add(new Item("red-bull", 140));
        smallProblem.items.add(new Item("hopscotch", -140));
    }

    private void buildLargeProblem()
    {
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
	
    @Override
    protected void tearDown() throws Exception
    {
        executorService.shutdown();
    }
    
	public void timeLargeProblemWithLargeSolution(int reps) throws InterruptedException, ExecutionException
	{
		ProblemSolver solver = new ProblemSolver(largeProblem, roundStrategy, executorService);
		for (int i = 0; i < reps; i++)
		{
			solver.solve();
		}
	}
	
	public void timeMediumProblemWithSolution(int reps) throws InterruptedException, ExecutionException
	{
		ProblemSolver solver = new ProblemSolver(mediumProblem, roundStrategy, executorService);
		for (int i = 0; i < reps; i++)
		{
			solver.solve();
		}
	}
	
	public void timeSmallProblemWithSolution(int reps) throws InterruptedException, ExecutionException
	{
		ProblemSolver solver = new ProblemSolver(smallProblem, roundStrategy, executorService);
		for (int i = 0; i < reps; i++)
		{
			solver.solve();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Runner.main(Benchmark.class, args);
	}

}
