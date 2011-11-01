package com.mattdrees.dropboxcalories;

import java.io.BufferedReader;
import java.io.IOException;

public class ProblemReader 
{
	
	public Problem readFrom(BufferedReader reader) throws IOException
	{
		int count = Integer.parseInt(reader.readLine());
		Problem problem = new Problem();
		for (int i = 0; i < count; i++)
		{
			String line = reader.readLine();
			boolean wasNewItem = problem.items.add(parseItem(line));
			if (!wasNewItem)
			{
			    throw new IllegalArgumentException("invalid problem; " + line + " shows up twice");
			}
		}
		return problem;
	}

	private Item parseItem(String line) {
		String[] pieces = line.split(" ");
		String name = pieces[0];
		int calorieImpact = Integer.parseInt(pieces[1]);
		return new Item(name, calorieImpact);
	}

}
