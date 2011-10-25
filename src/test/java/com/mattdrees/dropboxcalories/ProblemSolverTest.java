package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.ExecutionException;

import org.testng.annotations.Test;

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



}
