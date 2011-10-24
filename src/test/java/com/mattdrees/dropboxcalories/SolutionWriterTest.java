package com.mattdrees.dropboxcalories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;

public class SolutionWriterTest {

	SolutionWriter writer = new SolutionWriter();
	
	@Test
	public void testWritingSimpleSolution()
	{
		Set<Item> items = Sets.newHashSet(
			new Item("coding-six-hours", -466),
			new Item("cookies", 316),
			new Item("mexican-coke", 150));
		
		StringWriter stringWriter = new StringWriter();
		writer.writeSolution(Solution.of(items), new PrintWriter(stringWriter));
		
		String output = stringWriter.toString();
		assertThat(output, equalTo(
				"coding-six-hours\n" + 
				"cookies\n" + 
				"mexican-coke\n"));
	}
}
