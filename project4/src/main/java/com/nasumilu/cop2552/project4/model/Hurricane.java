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
package com.nasumilu.cop2552.project4.model;

import java.time.LocalDate;

/**
 * JavaBean which represents a named hurricane which made land fall (date) on the US coast and 
 * its category at the time. 
 * 
 * @author Michael Lucas
 */
public class Hurricane {

	/**
	 * The storm's name
	 */
	private String name;
	
	/**
	 * The storm's category
	 */
	private int category = -1;
	
	/**
	 * The date the storm made land fall
	 */
	private LocalDate landfall;
	
	/**
	 * Constructs an empty Hurricane object with a null name, 
	 * null land fall date and -1 category.
	 */
	public Hurricane() { }
	/**
	 * Constructs a Hurricane object with a name, category, and land fall
	 * date.
	 * 
	 * @param name the storm's name
	 * @param category the storm's Saffir-Simpson category
	 * @param landfall the date the storm made land fall
	 */
	public Hurricane(String name, int category, LocalDate landfall) {
		this.name = name;
		this.category = category;
		this.landfall = landfall;
	}
	
	/**
	 * Gets the storm's land fall date
	 * 
	 * @return the land fall date
	 */
	public LocalDate getLandfall() {
		return this.landfall;
	}
	
	/**
	 * Sets the storm's land fall date.
	 * 
	 * @param landfall the land fall date
	 */
	public void setLandfall(LocalDate landfall) {
		this.landfall = landfall;
	}
	
	/**
	 * Gets the storm's Saffir-Simpson category value
	 * 
	 * @return the Saffir-Simpson category value
	 */
	public int getCategory() {
		return this.category;
	}
	
	/**
	 * Sets the storm's category
	 * 
	 * @param category the Saffir-Simpson category value
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	
	/**
	 * Sets the storm's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the storm's name
	 * 
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
