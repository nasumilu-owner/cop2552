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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import com.nasumilu.cop2552.project3.event.ActivityManagerListener;
import com.nasumilu.cop2552.project3.factory.ConsoleFactory;
import com.nasumilu.cop2552.project3.factory.ExerciseActivityFactory;
import com.nasumilu.cop2552.project3.model.AggregateModel;

/**
 * @author Michael Lucas
 */
public class ExerciseActivityManager implements Runnable {

	private ExerciseActivityFactory factory;
	private AggregateModel model;
	private List<ActivityManagerListener> listeners;
		
	public ExerciseActivityManager() {
		this(new ConsoleFactory());
	}
	
	public ExerciseActivityManager(ExerciseActivityFactory factory) {
		this.factory = factory;
		this.model = new AggregateModel();
		this.listeners = new ArrayList<>();
	}
	
	/**
	 * Adds an ActivityManagerListener to this ExerciseActivityManager
	 * @param listener the ActivityManagerListener to add
	 */
	public void addActivityManagerListener(ActivityManagerListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeActivityManagerListener(ActivityManagerListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Utility method used to fire the registered ActivityManagerListener
	 */
	protected void fireActivityManagerClosing() {
		this.listeners.forEach(l -> l.activityManagerClosing(this));
	}
	
	/**
	 * @return the model
	 */
	public AggregateModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(AggregateModel model) {
		this.model = model;
	}

	public List<ExerciseActivity> getExerciseActivites() {
		List<ExerciseActivity> activities = new ArrayList<>();
		Iterable<ExerciseActivity> services = ServiceLoader.load(ExerciseActivity.class);
		services.forEach(activities::add);
		return activities;
	}

	@Override
	public void run() {
		Optional<ExerciseActivity> activity;
		do {
			activity = this.factory.create(this.getExerciseActivites());
			activity.ifPresent(a -> this.addActivity(a));
		} while(activity.isPresent());
		this.fireActivityManagerClosing();
	}

	public ExerciseActivityFactory getFactory() {
		return factory;
	}

	public void setFactory(ExerciseActivityFactory factory) {
		this.factory = factory;
	}
	
	public void addActivity(ExerciseActivity activity) {
		this.model.addActivity(activity);
	}
	
}
