package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.mattdrees.dropboxcalories.Benchmark.ExecutorServiceStrategy;

public class ProblemSolverTest {
	
	Problem problem;
	
	@DataProvider(name = "parameters")
	public Object[][] createParameters() {
	    int combinations = RoundBuildingStrategy.values().length * ExecutorServiceStrategy.values().length;
	    
	    Object[][] parameters = new Object[combinations][2];
	    
	    int index = 0;
	    for (RoundBuildingStrategy roundStrategy : RoundBuildingStrategy.values())
	    {
	        for (ExecutorServiceStrategy executorStrategy : ExecutorServiceStrategy.values())
	        {
	            parameters[index] = new Object[]{roundStrategy, executorStrategy};
	            index++;
	        }
	    }
	    
	   return parameters; 
	 }
	
	@Test(dataProvider = "parameters")
	public void testSimpleNoSolution(RoundBuildingStrategy roundStrategy, ExecutorServiceStrategy executorStrategy) throws InterruptedException, ExecutionException
	{
		problem = new Problem();
        problem.items.add(new Item("red-bull", 140));
        problem.items.add(new Item("pushup", -10));
        problem.items.add(new Item("skeeball", -150));
        
		ProblemSolver solver = new ProblemSolver(problem, roundStrategy, executorStrategy.build());
		Solution solution = solver.solve();
		
		assertThat(solution, equalTo(Solution.NO_SOLUTION));
	}
	
	@Test(dataProvider = "parameters")
	public void testSimpleProblemWithSolution(RoundBuildingStrategy roundStrategy, ExecutorServiceStrategy executorStrategy) throws InterruptedException, ExecutionException
	{
	    problem = new Problem();
        problem.items.add(new Item("red-bull", 140));
        problem.items.add(new Item("pushup", -10));
        problem.items.add(new Item("hopscotch", -130));

        ProblemSolver solver = new ProblemSolver(problem, roundStrategy, executorStrategy.build());
	    Solution solution = solver.solve();
	    
	    assertThat(solution, not(equalTo(Solution.NO_SOLUTION)));
	    assertThat(solution.items, equalTo(problem.items));
	}
	
	// from the second test case at http://www.dropbox.com/jobs/challenges#the-dropbox-diet
	@Test(dataProvider = "parameters")
	public void testMediumProblemWithSolution(RoundBuildingStrategy roundStrategy, ExecutorServiceStrategy executorStrategy) throws InterruptedException, ExecutionException
	{
	    problem = new Problem();
        problem.items.add(new Item("free-lunch", 802));
        problem.items.add(new Item("mixed-nuts", 421));
        problem.items.add(new Item("orange-juice", 143));
        problem.items.add(new Item("heavy-ddr-session", -302));
        problem.items.add(new Item("cheese-snacks", 137));
        problem.items.add(new Item("cookies", 316));
        problem.items.add(new Item("mexican-coke", 150));
        problem.items.add(new Item("dropballers-basketball", -611));
        problem.items.add(new Item("coding-six-hours", -466));
        problem.items.add(new Item("riding-scooter", -42));
        problem.items.add(new Item("rock-band", -195));
        problem.items.add(new Item("playing-drums", -295));

        ProblemSolver solver = new ProblemSolver(problem, roundStrategy, executorStrategy.build());
	    Solution solution = solver.solve();
	    
	    Set<Item> expectedSolution = ImmutableSet.of(
	        new Item("coding-six-hours", -466),
	        new Item("cookies", 316),
	        new Item("mexican-coke", 150)
	        );
	    
	    assertThat(solution, not(equalTo(Solution.NO_SOLUTION)));
	    assertThat(solution.items, equalTo(expectedSolution));
	}


    @Test(dataProvider = "parameters")
    public void testLargeProblemWithLargeSolution(RoundBuildingStrategy roundStrategy, ExecutorServiceStrategy executorStrategy) throws InterruptedException, ExecutionException
    {
        problem = new Problem();
        for (int i = 0; i < 23; i++)
        {
            problem.items.add(new Item("food" + i, 27));
        }
        for (int i = 0; i < 27; i++)
        {
            problem.items.add(new Item("excercise" + i, -23));
        }

        ProblemSolver solver = new ProblemSolver(problem, roundStrategy, executorStrategy.build());
        Solution solution = solver.solve();
        
        assertThat(solution, not(equalTo(Solution.NO_SOLUTION)));
        assertThat(solution.items, equalTo(problem.items));
    }
    

}
