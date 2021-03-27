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
package com.nasumilu.cop2552.project3.factory;

import java.util.List;
import java.util.Optional;

import com.nasumilu.cop2552.project3.ExerciseActivity;
import com.nasumilu.cop2552.project3.Messages;

/**
 * @author Michael Lucas
 */
public abstract class AbstractExerciseActivityFactory implements ExerciseActivityFactory {
		
	protected final Messages MESSAGES = Messages.getMessagesFor(AbstractExerciseActivityFactory.class);
	
	/**
	 * This {@link ExerciseActivityFactory#create(List)} method is implemented 
	 * to first initialize an {@link ExerciseActivity}, then populate its 
	 * Intensity, and last populate the its amount of time 
	 * performed.
	 * 
	 * At any point the user chooses to not populate the Intensity or time amount 
	 * an empty Optional is returned. 
	 */
	public Optional<ExerciseActivity> create(List<ExerciseActivity> activities) {
		Optional<ExerciseActivity> activity = this.promptForActivity(activities);
		if(activity.isPresent()) {
			activity = this.promptForIntensity(activity.get());
		}
		if(activity.isPresent()) {
			activity = this.promptForAmount(activity.get());
		}
		return activity;
	}
	
	/**
	 * Prompts and initializes an ExerciseActivity from valid user input
	 * 
	 * @param activities list of possible activities instances
	 * @return the chosen ExerciseActivity; or empty on quit
	 */
	protected abstract Optional<ExerciseActivity> promptForActivity(List<ExerciseActivity> activities);
	
	/**
	 * Prompt and populates the ExerciseActivity argument's Intensity from valid user input
	 * 
	 * @param activity the activity to populate its intensity from user input
	 * @return the ExerciseActivity with its intensity populated; or empty on cancel
	 */
	protected abstract Optional<ExerciseActivity> promptForIntensity(ExerciseActivity activity);
	
	/**
	 * Prompts and populates the ExerciseActivity argument's amount from valid user input.
	 * 
	 * @param activity the activity to populate its amount from user input
	 * @return the ExerciseActivity with its amount populates; or empty on cancel
	 */
	protected abstract Optional<ExerciseActivity> promptForAmount(ExerciseActivity activity);

}
