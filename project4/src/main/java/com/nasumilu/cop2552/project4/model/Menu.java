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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nasumilu.cop2552.project4.event.ActionListener;


/**
 * Class represents a map of menu options, called actions. The map of actions have
 * a unique numerical id or key and are presented in ascending order based upon that
 * key. 
 * 
 * Basically, its a very simple selection model
 * 
 * @author Michael Lucas
 */
public class Menu {

	/**
	 * Map of actions
	 */
	private final Map<Integer, String> ACTIONS;
	/**
	 * Set of ActionListeners
	 */
	private final Set<ActionListener> ACTION_LISTENERS = new HashSet<>();
	
	/**
	 * Currently selected action
	 */
	private int selected;
	
	/**
	 * Constructs an empty Menu
	 */
	public Menu() {
		this(ActionListener.getDefaultActions());
	}
	
	/**
	 * Constructs a Menu with actions
	 * @param actions a Map of actions
	 */
	public Menu(Map<Integer, String> actions) {
		this.ACTIONS = actions;
	}
	
	/**
	 * Sets the current selected action id
	 * @param id an action's id
	 */
	public void setSelected(int id) {
		if(this.ACTIONS.containsKey(id)) {
			this.selected = id;
			this.fireActionListener();
		}
	}
	
	/**
	 * Gets the currently selected id
	 * @return the action's currently selected id
	 */
	public int getSelected() {
		return this.selected;
	}
	
	/**
	 * Gets the currently selected value
	 * @return the action's <code>String</code> value
	 */
	public String getSelectedValue() {
		return this.ACTIONS.get(this.selected);
	}
	
	/**
	 * Sets the currently selected value
	 * @param value an action's <code>String</code> value
	 */
	public void setSelectedValue(String value) {
		this.ACTIONS.entrySet()
			.stream()
			.filter(entry -> entry.getValue().equals(value))
			.findFirst()
			.ifPresent(entry -> this.setSelected(entry.getKey()));
	}
	
	/**
	 * Internal for dispatching/firing an ActionListener
	 */
	private void fireActionListener() {
		for(ActionListener listener : this.ACTION_LISTENERS) {
			// very basic chain-of-responsibility
			listener.performAction(this);
		}
	}
	
	/**
	 * Gets a Map of Menu actions
	 * @return a map actions
	 */
	public Map<Integer, String> getActions() {
		return this.ACTIONS;
	}
	
	/**
	 * Add an ActionListener to the Menu
	 * @param listener an <code>ActionListener</code>
	 */
	public void addActionListener(ActionListener listener) {
		this.ACTION_LISTENERS.add(listener);
	}
	
	/**
	 * Removes an ActionListener from the Menu
	 * @param listener the <code>ActionListener</code>
	 */
	public void removeActionListener(ActionListener listener) {
		this.ACTION_LISTENERS.remove(listener);
	}
	
}
