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

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.nasumilu.cop2552.project4.model.Hurricane;
import com.nasumilu.cop2552.project4.model.Menu;

/**
 * An ActionListener used to handle all grouped by aggregate of a Hurricane set.
 * <br>
 * Handles the following actions:
 * <ul>
 * 	<li><strong>Display Total by Category</strong> action. ({@link ActionListener#GROUP_BY_CATEGORY})</li>
 *  <li><strong>Display Total by Year</strong> action. ({@link ActionListener#GROUP_BY_YEAR})</li>
 * </ul>
 * 
 * @author Michael Lucas
 */
public class GroupByActionListener implements ActionListener {

	// Map of Collector used to group the Hurricane set
	private static final Map<Integer, Collector<Hurricane, ?, Map<Integer, Long>>> COLLECTORS = new HashMap<>();
	// This classes ResourceBundle
	private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(GroupByActionListener.class.getName());

	// Initialize the set of Collectors
	static {
		COLLECTORS.put(GROUP_BY_CATEGORY,
				Collectors.groupingBy(Hurricane::getCategory, TreeMap::new, Collectors.counting()));
		COLLECTORS.put(GROUP_BY_YEAR,
				Collectors.groupingBy(h -> h.getLandfall().getYear(), TreeMap::new, Collectors.counting()));
	}

	/**
	 * The set of Hurricane objects to group
	 */
	private final Set<Hurricane> HURRICANES;
	/**
	 * Map to cache the group by results
	 */
	private Map<Integer, String> results = new HashMap<>();

	/**
	 * Constructs a GroupByAcitonListener
	 * @param hurricanes a set of <code>Hurricane</code> objects to group
	 */
	public GroupByActionListener(Set<Hurricane> hurricanes) {
		this.HURRICANES = hurricanes;
	}

	/**
	 * Action groups a set of Hurricane objects by category or year they made land fall.
	 * <br>
	 * The grouping action and resulting string is lazy loaded and cached. Subsequent calls
	 * will present the cached result String.
	 */
	@Override
	public void performAction(Menu menu) {
		final int selected = menu.getSelected();
		final Collector<Hurricane, ?, Map<Integer, Long>> COLLECTOR = COLLECTORS.get(selected);
		if (null == COLLECTOR) {
			return;
		}
		if (!this.results.containsKey(selected)) {
			final StringBuilder builder = new StringBuilder();
			builder.append(RESOURCES.getString("TITLE"));
			builder.append("\n\n");
			builder.append(RESOURCES.getString("HEADING." + selected));
			builder.append("\n\n");
			builder.append(String.format(RESOURCES.getString("TOTALS"), this.HURRICANES.size()));
			builder.append("\n\n");
			builder.append(RESOURCES.getString("ROW_HEAD." + selected));
			builder.append("\n");
			final String rowFormat = RESOURCES.getString("ROW_FORMAT." + selected);
			this.HURRICANES.stream().collect(COLLECTOR).forEach((k, v) -> {
				builder.append(String.format(rowFormat, k, v));
				builder.append("\n");
			});
			this.results.put(selected, builder.toString());
		}
		JTextArea text = new JTextArea(30, 30);
		text.setEditable(false);
		text.setText(this.results.getOrDefault(selected, RESOURCES.getString("NO_RESULTS")));
		JOptionPane.showMessageDialog(null, text, RESOURCES.getString("TITLE"), JOptionPane.INFORMATION_MESSAGE);
	}

}
