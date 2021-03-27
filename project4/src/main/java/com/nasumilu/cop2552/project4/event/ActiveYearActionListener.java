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

import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.nasumilu.cop2552.project4.model.Hurricane;
import com.nasumilu.cop2552.project4.model.Menu;

/**
 * An ActionListener which handles calculating and displaying the most active
 * hurricane year(s) found in a set.
 * <br>
 * Handles the <strong>Display Most Active Year</strong> action, {@link ActionListener#MOST_ACTIVE_YEAR}. 
 * @author Michael Lucas
 */
public class ActiveYearActionListener implements ActionListener {

	/**
	 * Classes resource bundle
	 */
	private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(ActiveYearActionListener.class.getName());

	/**
	 * A set of Hurricane object to find the most active year(s).
	 */
	private final Set<Hurricane> HURRICANES;
	/**
	 * variable to cache the resulting string
	 */
	private String results;

	public ActiveYearActionListener(Set<Hurricane> hurricanes) {
		this.HURRICANES = hurricanes;
	}

	/**
	 * This action will find the most active year when the argument menu's selected
	 * value is equal to ActionListener.MOST_ACTIVE_YEAR
	 * 
	 * The results is lazy loaded and cached, subsequent class just presents the cached
	 * result string.
	 */
	@Override
	public void performAction(Menu menu) {
		// if the most active year is not selected, not responsible
		if (menu.getSelected() != ActionListener.MOST_ACTIVE_YEAR) {
			return;
		}
		if (null == results) {
			// responsible
			final StringBuilder builder = new StringBuilder();
			builder.append(RESOURCES.getString("TITLE"));
			builder.append("\n\n");
			builder.append(RESOURCES.getString("HEADING"));
			builder.append("\n\n");

			// Count groupedByYear
			Map<Integer, Long> groupByYear = HURRICANES.stream().collect(
					Collectors.groupingBy(h -> h.getLandfall().getYear(), TreeMap::new, Collectors.counting()));

			// Find the max and filter the count group
			groupByYear.entrySet().stream()
				.max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
				.ifPresentOrElse(maxEntry -> {
					Iterator<Entry<Integer, Long>> iterator = groupByYear.entrySet()
							.stream()
							.filter(entry -> entry.getValue() == maxEntry.getValue())
							.iterator();

						builder.append(String.format(RESOURCES.getString("OUTPUT_FORMAT"),
								this.pluralizeIterator(iterator), maxEntry.getValue()));
						this.results = builder.toString();
					},
					// The search set is empty
					() -> {
						this.results = RESOURCES.getString("NO_RESULTS");
					});
		}
		JOptionPane.showMessageDialog(null, this.results, RESOURCES.getString("TITLE"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Makes an attempt to pulralize the result. Needs more testing!
	 * 
	 * @todo Need test case(s)
	 * @param iterator
	 * @return
	 */
	private String pluralizeIterator(Iterator<Entry<Integer, Long>> iterator) {
		StringBuilder builder = new StringBuilder();
		int count = 0;
		while (iterator.hasNext()) {
			builder.append(iterator.next().getKey());
			builder.append(", ");
			count++;
		}
		int index = builder.lastIndexOf(",");
		builder.deleteCharAt(index);
		index = builder.lastIndexOf(",");
		if (count == 2) {
			builder.replace(index, index + 1, " and");
		}

		if (count > 2) {
			builder.replace(index, index + 1, ", and");
		}

		return builder.toString();
	}

}
