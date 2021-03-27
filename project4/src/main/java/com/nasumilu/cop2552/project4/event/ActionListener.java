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
package com.nasumilu.cop2552.project4.event;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.nasumilu.cop2552.project4.model.Menu;

/**
 * ActionListener defines a single action which is triggered on a selected menu
 * index.
 * <br>
 * This provides a lightweight event system and does <strong>NOT</strong> stop propagation
 * when an action is handled. 
 * <br>
 * <strong>All implementations need to return void when not responsible!</strong>
 * 
 * TODO: Create an ActionEvent which singles stop propagation 
 * @author Michael Lucas
 */
@FunctionalInterface
public interface ActionListener {

	/**
	 * Sort Storm by Name Action Key
	 */
	public static final int SORT_BY_NAME = 1;
	/**
	 * Sort Storm by Category Action Key
	 */
	public static final int SORT_BY_CATEGORY = 2;
	/**
	 * Sort Storm by Year Action Key
	 */
	public static final int SORT_BY_YEAR = 3;
	/**
	 * Sort Storm by Month Action Key
	 */
	public static final int SORT_BY_MONTH = 4;

	/**
	 * Display Average Storm Category Action Key
	 */
	public static final int AVG_CATEGORY = 5;

	/**
	 * Display the Most Active Year(s) Action Key
	 */
	public static final int MOST_ACTIVE_YEAR = 6;

	/**
	 * Display Total by Category Action Key
	 */
	public static final int GROUP_BY_CATEGORY = 7;
	/**
	 * Display Total by Year Action Key
	 */
	public static final int GROUP_BY_YEAR = 8;

	/**
	 * Exit Action Key
	 */
	public static final int EXIT = 9;

	/**
	 * Get the default action map from the ActionListener static final fields using
	 * java.lang.reflect package
	 * 
	 * <strong>IMPORTANT:</strong> only public static final Fields declared in the
	 * ActionListener interface are used. Those fields MUST be primitive integers.
	 * 
	 * @return a Map of default actions 
	 */
	public static Map<Integer, String> getDefaultActions() {
		final ResourceBundle RESOURCES = ResourceBundle.getBundle(ActionListener.class.getName());
		final Map<Integer, String> actions = new HashMap<>();
		Arrays.stream(ActionListener.class.getFields()).forEach(field -> {
			int modifer = field.getModifiers();
			if (field.getType().getTypeName().equals("int") && Modifier.isStatic(modifer)
					&& Modifier.isFinal(modifer)) {
				try {
					actions.put(field.getInt(null), RESOURCES.getString(field.getName()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
		});

		return actions;
	}

	/**
	 * Invoked when the @param menu has a selected value.
	 * 
	 * @param menu the Menu whose has a value which was just selected.
	 */
	public void performAction(Menu menu);

}
