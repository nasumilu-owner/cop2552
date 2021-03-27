package com.nasumilu.cop2552.project2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class PatientTest {

	@Test
	public void testGetSetId() {
		Patient p = new Patient();
		String expected = "0123-123";
		p.addPropertyChangeListener("id", evt -> {
			assertEquals("id", evt.getPropertyName());
			assertNull(evt.getOldValue());
			assertEquals(expected, evt.getNewValue());
		});
		
		assertNull(p.getId());
		p.setId(expected);
		assertTrue(expected.equals(p.getId()));
	}
	
	@Test
	public void testGetSetName() {
		Patient p = new Patient();
		String expected = "Smith, John";
		p.addPropertyChangeListener("name", evt -> {
			assertEquals("name", evt.getPropertyName());
			assertNull(evt.getOldValue());
			assertEquals(expected, evt.getNewValue());
		});
		assertNull(p.getName());
		p.setName(expected);
		assertTrue(expected.equals(p.getName()));
	}
	
	@Test
	public void testGetSetDob() {
		Patient p = new Patient();
		String expected = "01012000";
		p.addPropertyChangeListener("dob", evt -> {
			assertEquals("dob", evt.getPropertyName());
			assertNull(evt.getOldValue());
			assertEquals(expected, evt.getNewValue());
		});
		assertNull(p.getDob());
		p.setDob(expected);
		assertTrue(expected.equals(p.getDob()));		
	}
	
	@Test 
	public void testGetSetDobLocalDate() {
		Patient p = new Patient();
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
		String expected = now.format(formatter);
		p.addPropertyChangeListener("dob", evt -> {
			assertEquals("dob", evt.getPropertyName());
			assertNull(evt.getOldValue());
			assertEquals(expected, evt.getNewValue());
		});
		
		p.setDob(now);
		assertEquals(expected, p.getDob());
	}
	
	@Test
	public void testCompareTo() {
		Patient p1 = new Patient("0123-123", null, null, null);
		Patient p2 = new Patient("0123-123", null, null, null);
		assertEquals(0, p1.compareTo(p2));
		p1.setId("1000-000");
		assertEquals(1, p1.compareTo(p2));
		p1.setId("0000-500");
		assertEquals(-1, p1.compareTo(p2));
		
	}

	@Test
	public void testGetSetYearAdded() {
		Patient p = new Patient();
		Integer expected = 2002;
		p.addPropertyChangeListener("yearAdded", evt -> {
			assertEquals("yearAdded", evt.getPropertyName());
			assertNotNull(evt.getOldValue());
			assertEquals(expected, evt.getNewValue());
		});
		assertEquals(LocalDate.now().getYear(), p.getYearAdded());
		p.setYearAdded(expected);
		assertEquals(expected, p.getYearAdded());
	}
	
}
