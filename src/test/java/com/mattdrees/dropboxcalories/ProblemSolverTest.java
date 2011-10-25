package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.ExecutionException;

import org.testng.annotations.Test;

public class ProblemSolverTest {
	
	Problem problem;
	
	
	@Test
	public void testSimple() throws InterruptedException, ExecutionException
	{
		buildSimpleProblem();
		ProblemSolver solver = new ProblemSolver(problem);
		Solution solution = solver.solve();
		
		assertThat(solution, equalTo(Solution.NO_SOLUTION));
	}


	private void buildSimpleProblem() {
		problem = new Problem();
		problem.items.add(new Item("red-bull", 140));
		problem.items.add(new Item("pushup", -10));
		problem.items.add(new Item("pushup", -130));
	}
	

}
