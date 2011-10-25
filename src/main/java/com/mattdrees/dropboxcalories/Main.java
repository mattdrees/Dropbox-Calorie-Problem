package com.mattdrees.dropboxcalories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Stopwatch;

public class Main
{
    
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
    {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        ProblemReader reader = new ProblemReader();
        Problem problem = reader.readFrom(new BufferedReader(new InputStreamReader(System.in)));
        
        ProblemSolver solver = new ProblemSolver(problem);
        Solution solution = solver.solve();
        
        SolutionWriter writer = new SolutionWriter();
        PrintWriter printWriter = new PrintWriter(System.out);
        writer.writeSolution(solution, printWriter);
        printWriter.flush();
        stopwatch.stop();
        System.err.println("Internal execution time: " + stopwatch.toString());
    }

}
