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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.nasumilu.cop2552.project3.ExerciseActivity;
import com.nasumilu.cop2552.project3.Messages;

/**
 * 
 * @author Michael Lucas
 *
 */
public class AggregateModel {
	
	private static final Messages MESSAGES = Messages.getMessagesFor(AggregateModel.class);
	
	private Map<String, Aggregate> data;

	public AggregateModel() {
		this.data = new HashMap<>();
	}

	/**
	 * @return the size of the model (number of data items)
	 * @see java.util.Map#size()
	 */
	public int size() {
		return data.size();
	}

	/**
	 * @return true if the model is empty; false otherwise
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	public void addActivity(ExerciseActivity activity) {
		String key = activity.getName();
		if (!this.data.containsKey(key)) {
			this.data.put(key, new Aggregate());
		}
		this.data.get(key).add(activity);
	}

	public String[] getColumnNames() {
		return new String[] { MESSAGES.message("model.activity"), MESSAGES.message("model.count"),
				MESSAGES.message("model.time"), MESSAGES.message("model.avg_time"),
				MESSAGES.message("model.calories"), MESSAGES.message("model.avg_calories") };
	}

	public String[][] getColumnData() {
		int size = this.data.size();
		String[][] columnData = new String[size + 1][6];
		Integer totalCount = 0;
		Float totalAmount = 0f;
		Float totalCalories = 0f;
		int i = 0;

		NumberFormat format = new DecimalFormat(MESSAGES.message("model.decimalFormat"));

		for (String k : this.data.keySet()) {
			Aggregate v = this.data.get(k);
			totalCount += v.getCount();
			totalAmount += v.getTime();
			totalCalories += v.getCalories();

			columnData[i] = new String[] { k, format.format(v.getCount()), format.format(v.getTime()),
					format.format(v.getAvgTime()), format.format(v.getCalories()), format.format(v.getAvgCalories()) };
			i++;
		}
		columnData[i] = new String[] { MESSAGES.message("model.total"), format.format(totalCount),
				format.format(totalAmount), format.format(totalAmount / totalCount), format.format(totalCalories),
				format.format(totalCalories / totalCount) };
		return columnData;
	}

}
