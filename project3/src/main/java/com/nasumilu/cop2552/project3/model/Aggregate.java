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
package com.nasumilu.cop2552.project3.model;

import java.text.NumberFormat;

import com.nasumilu.cop2552.project3.ExerciseActivity;

/**
 * @author Michael Lucas
 *
 */
public class Aggregate {

	private Float time = 0.0f;
	private Float calories = 0.0f;
	private Integer count = 0;

	private NumberFormat numberFormat = null;
	
	public Aggregate() { 
		this(NumberFormat.getNumberInstance());
	}
	
	public Aggregate(NumberFormat format) {
		this.numberFormat = format;
	}
	
	public void add(ExerciseActivity activity) {
		this.count++;
		this.time += activity.getAmount();
		this.calories += activity.getCaloriesBurned();
	}
	
	public NumberFormat getNumberFormat() {
		return this.numberFormat;
	}

	public Integer getCount() {
		return this.count;
	}
	
	public Float getAvgTime() {
		return this.time/(float) this.count;
	}
	
	public Float getAvgCalories() {
		return this.calories/(float) this.count;
	}
	
	/**
	 * @return the time
	 */
	public Float getTime() {
		return time;
	}

	/**
	 * @return the calories
	 */
	public Float getCalories() {
		return calories;
	}
	
}
