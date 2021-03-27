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
package com.nasumilu.cop2552.project5.model;

import java.io.Serializable;

/**
 * The Production Staff is a simple data structure which represents a 
 * movie's production staff by name. (Director &amp; Composer)
 * 
 * <p>This class implements a fluent mutator interface so method chaining is possible.</p>
 * <strong>For Example</strong>
 * <br>
 * <pre><code>
 * ProductionStaff staff = (new ProductionStaff()).setDirector("George Lucas")
 * 		.setComposer("Quincy Jones");
 * </code></pre>
 * 
 * @author Michael Lucas
 */
public class ProductionStaff implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Movie director's name
	private String director;
	// Movie composer's name
	private String composer;
	
	/**
	 * Constructs an empty <code>ProductionStaff</code> instance.
	 * 
	 * In other words, without a director or composer.
	 */
	public ProductionStaff() { }
	
	/**
	 * Constructs a <code>ProductionStaff</code> instance with a director
	 * and composer.
	 * 
	 * @param director the director's name
	 * @param composer the composer's name
	 */
	public ProductionStaff(String director, String composer) {
		this.director = director;
		this.composer = composer;
	}
	
	/**
	 * Gets the name of the director
	 * 
	 * @return the director's name
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * Sets the name of the director
	 * 
	 * @param director the director's name
	 * @return the <code>ProductionStaff</code> whose director's name was set (fluent)
	 */
	public ProductionStaff setDirector(String director) {
		this.director = director;
		return this;
	}
	/**
	 * Gets the name of the composer
	 * 
	 * @return the composer's name
	 */
	public String getComposer() {
		return composer;
	}
	
	/**
	 * Sets the name of the composer
	 * 
	 * @param composer the composer's name
	 * @return the <code>ProductionStaff</code> whose composer's name was set (fluent)
	 */
	public ProductionStaff setComposer(String composer) {
		this.composer = composer;
		return this;
	}
	
}
