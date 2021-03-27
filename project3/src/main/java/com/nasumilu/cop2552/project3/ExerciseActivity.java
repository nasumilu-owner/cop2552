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

/**
 * ExerciseActivity represents some type of physical activity which has an
 * intensity and measurable about of time.
 * 
 * @author Michael Lucas
 */
public interface ExerciseActivity {

	/**
	 * Possible ExersiceActivity intensities.
	 * 
	 * @author Michael Lucas
	 */
	public enum Intensity {
				
		/**
		 * Moderate intensity
		 */
		MODERATE,
		/**
		 * Vigorous intensity
		 */
		VIGOROUS;
			
		private static final Messages MESSAGES = Messages.getMessagesFor(ExerciseActivity.class);
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return MESSAGES.message("Intensity."+this.name());
		}
	}

	/**
	 * Gets the common name of the <code>ExerciseActivity</code>
	 * 
	 * @return the activity's common name
	 */
	public String getName();

	/**
	 * Gets the <code>ExerciseActivity.Intensity</code> of the
	 * <code>ExerciseActivity</code>
	 * 
	 * @return the activity's level of intensity
	 */
	public Intensity getIntensity();

	/**
	 * Sets the
	 * <code>ExerciseActivity.Intensity</code> of the <code>ExerciseActivity</code>.
	 * 
	 * @param intensity the intensity level for the activity
	 */
	public void setIntensity(Intensity intensity);

	/**
	 * Gets the amount of time this activity was performed.
	 * 
	 * @return a numeric value of time that this <code>ExerciseActivity</code> was
	 *         performed
	 */
	public Float getAmount();

	/**
	 * Sets the amount of time this activity was performed.
	 * 
	 * The value is expected to be greater than (&gt;) zero.
	 * 
	 * @param amount a numeric value of time that the activity was performed.
	 * @throws IllegalArgumentException when the value is less than or equal
	 *                                  (&lt;=) to zero.
	 */
	public void setAmount(Float amount);
	
	/**
	 * Gets the calories consumption rate for the <code>ExerciseActivity</code>
	 * level of intensity. 
	 * 
	 * If the <code>ExerciseActivity</code> DOES NOT have an Intensity (i.e. it is null)
	 * then this MUST return null.
	 * 
	 * @return numeric consumption rate when activity's intensity is set; otherwise
	 * null.
	 */
	public Float getCalorieConsumptionRate();

	/**
	 * Gets the amount of calories burned for the activity's time and intensity.
	 * 
	 * If either intensity or time are null this MUST return 0.
	 * 
	 * @return the calculated number of calories burned performing the activity
	 *         based upon the time and intensity.
	 */
	public Float getCaloriesBurned();

}
