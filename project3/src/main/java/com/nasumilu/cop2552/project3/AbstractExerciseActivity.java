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

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * AbstractExerciseActivity implements all of the ExerciseActivity interface,
 * provides setter methods, and add PropertyChangeSupport.
 * 
 * Its a plain old java bean.
 * 
 * @author Michael Lucas
 */
public abstract class AbstractExerciseActivity implements ExerciseActivity, Serializable {

	/**
	 * @see Serializable
	 */
	private static final long serialVersionUID = 1L;
	protected static final Messages MESSAGES = Messages.getMessagesFor(AbstractExerciseActivity.class);
	
	/**
	 * Add PropertyChangeSupport.
	 */
	protected final PropertyChangeSupport PROPERTY_CHANGE_SUPPORT = new PropertyChangeSupport(this);

	/**
	 * The intensity of the ExerciseActivity
	 */
	protected Intensity intensity;

	/**
	 * The time amount the ExerciseActivty was performed.
	 */
	private Float amount;

	/**
	 * {@inheritDoc}
	 */
	public Intensity getIntensity() {
		return this.intensity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setIntensity(Intensity intensity) {
		Intensity oldValue = this.intensity;
		this.intensity = intensity;
		this.PROPERTY_CHANGE_SUPPORT.firePropertyChange("intensity", oldValue, intensity);
	}

	/**
	 * {@inheritDoc}
	 */
	public Float getAmount() {
		return this.amount;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAmount(Float amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					String.format("Exercise activity amount MUST be greather than 0, found %f", amount));
		}
		Float oldValue = this.amount;
		this.amount = amount;
		this.PROPERTY_CHANGE_SUPPORT.firePropertyChange("amount", oldValue, amount);
	}

	/**
	 * {@inheritDoc}
	 */
	public Float getCalorieConsumptionRate() {
		Float value = null;
		if (Intensity.MODERATE.equals(this.intensity)) {
			value = getModerateRate();
		} else if (Intensity.VIGOROUS.equals(this.intensity)) {
			value = getVigorousRate();
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	public Float getCaloriesBurned() {
		Float rate = this.getCalorieConsumptionRate();
		return rate != null && this.amount != null ? this.amount * rate : 0;
	}

	/**
	 * Gets the rate calories are consumed at a moderate intensity.
	 * 
	 * @return the moderate intensity calorie consumption rate
	 */
	protected abstract Float getModerateRate();

	/**
	 * Gets the rate calories are consumed at a vigorous intensity.
	 * 
	 * @return the vigorous intensity calorie consumption rate
	 */
	protected abstract Float getVigorousRate();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
