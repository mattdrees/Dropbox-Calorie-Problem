package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

public class ProblemSolverTest {
	
	Problem problem;
	
	
	@Test
	public void testSimpleNoSolution() throws InterruptedException, ExecutionException
	{
		problem = new Problem();
        problem.items.add(new Item("red-bull", 140));
        problem.items.add(new Item("pushup", -10));
        problem.items.add(new Item("skeeball", -150));
        
		ProblemSolver solver = new ProblemSolver(problem);
		Solution solution = solver.solve();
		
		assertThat(solution, equalTo(Solution.NO_SOLUTION));
	}
	
	@Test
	public void testSimpleProblemWithSolution() throws InterruptedException, ExecutionException
	{
	    problem = new Problem();
        problem.items.add(new Item("red-bull", 140));
        problem.items.add(new Item("pushup", -10));
        problem.items.add(new Item("hopscotch", -130));
        
	    ProblemSolver solver = new ProblemSolver(problem);
	    Solution solution = solver.solve();
	    
	    assertThat(solution, not(equalTo(Solution.NO_SOLUTION)));
	    assertThat(solution.items, equalTo(problem.items));
	}
	
	// from the second test case at http://www.dropbox.com/jobs/challenges#the-dropbox-diet
	@Test
	public void testMediumProblemWithSolution() throws InterruptedException, ExecutionException
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
	    
	    ProblemSolver solver = new ProblemSolver(problem);
	    Solution solution = solver.solve();
	    
	    Set<Item> expectedSolution = ImmutableSet.of(
	        new Item("coding-six-hours", -466),
	        new Item("cookies", 316),
	        new Item("mexican-coke", 150)
	        );
	    
	    assertThat(solution, not(equalTo(Solution.NO_SOLUTION)));
	    assertThat(solution.items, equalTo(expectedSolution));
	}


    @Test
    public void testLargeProblemWithLargeSolution() throws InterruptedException, ExecutionException
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
        
        ProblemSolver solver = new ProblemSolver(problem);
        Solution solution = solver.solve();
        
        assertThat(solution, not(equalTo(Solution.NO_SOLUTION)));
        assertThat(solution.items, equalTo(problem.items));
    }
    

}
