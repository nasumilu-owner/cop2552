/**
 * Copyright 2021 Michael Lucas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nasumilu.cop2552.project3;

import java.util.HashMap;
import java.util.Map;

import com.nasumilu.cop2552.project3.ExerciseActivity.Intensity;

import junit.framework.TestCase;

/**
 * Test that all classes implementing ExerciseActivity available to the
 * ServiceLoader behave as expected.
 * 
 * @author Michael Lucas
 */
public class ExerciseActivityTest extends TestCase {

	protected static final Map<Class<? extends AbstractExerciseActivity>, Number> MODERATE_RATES = new HashMap<>();
	protected static final Map<Class<? extends AbstractExerciseActivity>, Number> VIGOROUS_RATES = new HashMap<>();
	static {
		// Hard code the expected moderate intensity rates
		MODERATE_RATES.put(Bicycle.class, 10f);
		MODERATE_RATES.put(Run.class, 12f);
		MODERATE_RATES.put(Swim.class, 6.8f);
		MODERATE_RATES.put(Walk.class, 5f);
		MODERATE_RATES.put(Weights.class, 3.7f);

		// Hard code the expected vigorous intensity rates
		VIGOROUS_RATES.put(Bicycle.class, 14.3f);
		VIGOROUS_RATES.put(Run.class, 17f);
		VIGOROUS_RATES.put(Swim.class, 14.8f);
		VIGOROUS_RATES.put(Walk.class, 5.6f);
		VIGOROUS_RATES.put(Weights.class, 5.9f);
	}

	/**
	 * Test that the classes are return the correct calorie consumption rates
	 */
	public void testGetCalorieConsumptionRate() {
		ExerciseActivityManager manager = new ExerciseActivityManager();
		manager.getExerciseActivites().forEach(a -> {
			// Intensity not set must be null
			assertNull(a.getCalorieConsumptionRate());

			// Check the moderate intensity rate
			a.setIntensity(Intensity.MODERATE);
			assertEquals(MODERATE_RATES.get(a.getClass()), a.getCalorieConsumptionRate());

			// Check the vigorous intensity rate
			a.setIntensity(Intensity.VIGOROUS);
			assertEquals(VIGOROUS_RATES.get(a.getClass()), a.getCalorieConsumptionRate());
		});
	}

	public void testGetCaloriesBurned() {
		ExerciseActivityManager manager = new ExerciseActivityManager();
		manager.getExerciseActivites().forEach(a -> {
			// both amount and intensity are null
			assertEquals(0f, a.getCaloriesBurned());

			a.setAmount(10.f);

			// only intensity is null
			assertEquals(0f, a.getCaloriesBurned());

			// Check the moderate intensity
			a.setIntensity(Intensity.MODERATE);
			Number expected = 10f * a.getCalorieConsumptionRate().floatValue();
			assertEquals(expected, a.getCaloriesBurned());

			// Check the vigorous intensity
			a.setIntensity(Intensity.VIGOROUS);
			expected = 10f * a.getCalorieConsumptionRate().floatValue();
			assertEquals(expected, a.getCaloriesBurned());
		});
	}

	public void testSetGetAmount() {
		ExerciseActivityManager manager = new ExerciseActivityManager();
		manager.getExerciseActivites().forEach(a -> {
			assertNull(a.getAmount());
			// Check to ensure the class does not allow an amount less than or equal to zero
			try {
				a.setAmount(0f);
				fail(String.format("%s allowed an amount <= 0", a.getClass().getName()));
			} catch (IllegalArgumentException e) {
				// test passed
			}
			// Test the get/set amount method
			Float expected = 10.5f;
			a.setAmount(expected);
			assertEquals(expected, a.getAmount());
		});
	}
	
	public void testSetGetIntensity() {
		ExerciseActivityManager manager = new ExerciseActivityManager();
		manager.getExerciseActivites().forEach(a -> {
			assertNull(a.getIntensity());
			a.setIntensity(Intensity.MODERATE);
			assertEquals(Intensity.MODERATE, a.getIntensity());
		});
	}

}
