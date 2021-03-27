package com.nasumilu.cop2552.project2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PatientReaderTest {
	
	private static PatientReader reader;
	
	@BeforeAll
	static void setUp() throws IOException {
		reader = new PatientReader(new FileReader("src/test/resources/patient.txt"));
	}

	@Test
	void testReadNextPatient() throws IOException {
		reader.forEach(p -> assertTrue(p instanceof Patient));
		assertFalse(reader.hasNext());
	}

}
