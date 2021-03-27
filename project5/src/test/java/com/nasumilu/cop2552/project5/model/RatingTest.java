package com.nasumilu.cop2552.project5.model;

import java.util.Arrays;

import junit.framework.TestCase;

public class RatingTest extends TestCase {

	public void testToString() {
		final String[] expected = new String[] {
				"G", "PG", "PG-13", "R", "NC-17", "NR", "UR"
		};
		Arrays.stream(Movie.Rating.values()).forEach(rating -> assertEquals(rating.toString(), expected[rating.ordinal()]));
		Arrays.stream(expected).forEach(name -> assertTrue(Movie.Rating.fromName(name) instanceof Movie.Rating));
	}

}
