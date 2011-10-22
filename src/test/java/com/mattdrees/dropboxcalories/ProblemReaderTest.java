package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;

public class ProblemReaderTest {
	
	ProblemReader reader = new ProblemReader();
	
	@Test
	public void testParseSimple() throws IOException
	{
		String testInput = "2\n" + 
				"red-bull 140\n" + 
				"coke 110\n";
		BufferedReader testReader  = new BufferedReader(new StringReader(testInput));
		Problem problem = reader.readFrom(testReader);
		
		Set<Item> correctItems = Sets.newHashSet(
				new Item("red-bull", 140), 
				new Item("coke", 110));
		assertThat(problem.items, equalTo(correctItems));
	}

}
