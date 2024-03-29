package com.mattdrees.dropboxcalories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.base.Stopwatch;

public class Main
{
    
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
    {
        Stopwatch internalExecutionStopwatch = new Stopwatch();
        internalExecutionStopwatch.start();
        ProblemReader reader = new ProblemReader();
        Problem problem = reader.readFrom(new BufferedReader(new InputStreamReader(System.in)));
        
        Stopwatch solutionStopwatch = new Stopwatch();
        solutionStopwatch.start();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Solution solution;
        try
        {
            ProblemSolver solver = new ProblemSolver(problem, RoundBuildingStrategy.BITSET_STRATEGY,  executorService);
            solution = solver.solve();
        }
        finally
        {
            executorService.shutdown();
        }
        solutionStopwatch.stop();
        
        SolutionWriter writer = new SolutionWriter();
        PrintWriter printWriter = new PrintWriter(System.out);
        writer.writeSolution(solution, printWriter);
        printWriter.flush();
        internalExecutionStopwatch.stop();
        System.err.println("Internal execution time: " + internalExecutionStopwatch.toString());
        System.err.println("Solution execution time: " + solutionStopwatch.toString());
    }

}
