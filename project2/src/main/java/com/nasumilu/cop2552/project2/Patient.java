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
package com.nasumilu.cop2552.project2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Patient class is a simple data structure which contains the base information
 * need about a patient as an old-school Java Bean.
 * 
 * @author Michael Lucas
 */
public class Patient implements Comparable<Patient>, Serializable {

	/**
	 * Serializable version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Old school PropertyChangeSupport for quick and easy property change events
	 */
	private transient final PropertyChangeSupport CHANGE_SUPPORT = new PropertyChangeSupport(this);

	/**
	 * Patient id property
	 */
	private String id;
	/**
	 * Patient name property
	 */
	private String name;
	/**
	 * Patient dob property
	 */
	private String dob;
	/**
	 * Patient year added property
	 */
	private Integer yearAdded;

	/**
	 * Constructs an empty Patient.
	 * 
	 * An empty Patient is one with no id, name, dob, and its year added is set to
	 * the current year of {@link LocalDate#now()}
	 */
	public Patient() {
		this(null, null, null, null);
	}
	
	/**
	 * Constructs a Patient with an id, name, dob, and year added.
	 * 
	 * If the year added is null it is initialized to the current year of
	 * {@link LocalDate#now()}.
	 * 
	 * @param id        the patient's id number
	 * @param name      the patient's name last name, first name
	 * @param dob       the patient's date of birth formatted as a two digit month,
	 *                  two digit day, and four digit year (MMddyyyy)
	 * @param yearAdded the year the patient was added; if null this is set to the
	 *                  current year
	 */
	public Patient(String id, String name, String dob, Integer yearAdded) {
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.yearAdded = Optional.ofNullable(yearAdded).orElse(LocalDate.now().getYear());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("%s\n%s\n%s\n%d", this.id, this.name, this.dob, this.yearAdded);
	}

	/**
	 * Getter for the year the patient was added.
	 * 
	 * @return the yearAdded
	 */
	public Integer getYearAdded() {
		return yearAdded;
	}

	/**
	 * Setter for the year the patient was added.
	 * 
	 * @param yearAdded the yearAdded to set
	 */
	public void setYearAdded(Integer yearAdded) {
		Integer oldValue = this.yearAdded;
		this.yearAdded = yearAdded;
		this.CHANGE_SUPPORT.firePropertyChange("yearAdded", oldValue, yearAdded);
	}

	/**
	 * Getter for the patient's id number.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for the patient's id number.
	 * 
	 * @param id the id to set
	 */
	public void setId(String id) {
		String oldValue = this.id;
		this.id = id;
		this.CHANGE_SUPPORT.firePropertyChange("id", oldValue, id);
	}

	/**
	 * Getter for the patient's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the patient's name comma separated last name, first name.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		this.CHANGE_SUPPORT.firePropertyChange("name", oldValue, name);
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Getter for the patient's date of birth as a string formated to two digit
	 * month, two digit day, and four digit year.
	 *
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		String oldValue = this.dob;
		this.dob = dob;
		this.CHANGE_SUPPORT.firePropertyChange("dob", oldValue, dob);
	}

	/**
	 * Set the date of birth with a LocalDate object
	 * 
	 * @param dob the patient's date of birth (MMddyyyy)
	 */
	public void setDob(LocalDate dob) {
		this.setDob(dob.format(DateTimeFormatter.ofPattern("MMddyyyy")));
	}

	/**
	 * {@inheritDoc}
	 */
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		CHANGE_SUPPORT.addPropertyChangeListener(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		CHANGE_SUPPORT.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		CHANGE_SUPPORT.removePropertyChangeListener(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		CHANGE_SUPPORT.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Patient other) {
		return this.id.compareTo(other.id);
	}

}
