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

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.nasumilu.cop2552.project4.io.HurricaneIO;
import com.nasumilu.cop2552.project4.model.Hurricane;
import com.nasumilu.cop2552.project4.model.Menu;

/**
 * An ActionListener used to sort a Hurricane set.
 * <br>
 * This ActionListener is responsible for:
 * <ul>
 * 	<li>Sorting a set of Hurricane objects by name. ({@link ActionListener#SORT_BY_NAME})</li>
 *  <li>Sorting a set of Hurricane objects by category. ({@link ActionListener#SORT_BY_CATEGORY})</li>
 *  <li>Sorting a set of Hurricane objects by year they made land fall. ({@link ActionListener#SORT_BY_YEAR})</li>
 *  <li>Sorting a set of Hurricane objects by month they made land fall. ({@link ActionListener#SORT_BY_MONTH})</li>
 * </ul>
 * 
 * @author Michael Lucas
 */
public class SortActionListener implements ActionListener {

	// DateTimeFormatter used to format the land fall LocalDate
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	// Map of Comparator used to sort a set of Hurricane objects
	private static final Map<Integer, Comparator<Hurricane>> COMPARATORS = new HashMap<>();
	// Map of file names used to output the results
	private static final Map<Integer, String> FILE_NAMES = new HashMap<>();
	// The classes ResourceBundle
	private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(SortActionListener.class.getName());
	
	// Directory path where the sorted output is stored
	private static final String WINDOWS_FILE_PREFIX = "C:\\SFC\\COP2552\\Project4";
	
	// Initialize the Comparators and output file names
	static {
		COMPARATORS.put(SORT_BY_NAME, Comparator.comparing(Hurricane::getName));
		FILE_NAMES.put(SORT_BY_NAME, "SortByName.txt");
		
		COMPARATORS.put(SORT_BY_CATEGORY, Comparator.comparing(Hurricane::getCategory));
		FILE_NAMES.put(SORT_BY_CATEGORY, "SortByCategory.txt");
		
		COMPARATORS.put(SORT_BY_YEAR, Comparator.comparing(h -> h.getLandfall().getYear()));
		FILE_NAMES.put(SORT_BY_YEAR, "SortByYear.txt");
		
		COMPARATORS.put(SORT_BY_MONTH, Comparator.comparing(h -> h.getLandfall().getMonth()));
		FILE_NAMES.put(SORT_BY_MONTH, "SortByMonth.txt");
	}
	
	// The set of Hurricane objects to sort.
	private final Set<Hurricane> HURRICANES;
	
	/**
	 * The possible sort order
	 * 
	 * @author Michael Lucas
	 */
	public enum Order {
		/**
		 * Ascending sort order
		 */
		ASC,
		/**
		 * Descending sort order
		 */
		DESC;
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return RESOURCES.getString(this.name());
		}
	}
	
	/**
	 * Constructs a SortActionListener which acts upon the set of hurricane
	 * @param hurricanes a set of hurricanes
	 */
	public SortActionListener(Set<Hurricane> hurricanes) {
		this.HURRICANES = hurricanes;
	}

	/**
	 * This action sorts a set of Hurricane objects when the {@link Menu#getSelected()} 
	 * value is equal to {@link ActionListener#SORT_BY_NAME}, {@link ActionListener#SORT_BY_CATEGORY},
	 * {@link ActionListener#SORT_BY_YEAR}, or {@link ActionListener#SORT_BY_MONTH}.
	 * <br>
	 * Unlike other ActionListeners this does not cache the results, subsequent class will perform 
	 * the sort each time.
	 */
	@Override
	public void performAction(Menu menu) {
		final int selected = menu.getSelected();
		final Comparator<Hurricane> COMPARATOR = COMPARATORS.get(selected);
		if(null == COMPARATOR) {
			return;
		}
		this.promptForOrder().ifPresent(order -> {
			final StringBuilder builder = new StringBuilder();
			JTextArea text = new JTextArea(30, 30);
			text.setEditable(false);
			builder.append(RESOURCES.getString("TITLE"));
			builder.append("\n\n");
			builder.append(String.format(RESOURCES.getString("HEADING"), menu.getSelectedValue(), order));
			builder.append("\n\n");
			builder.append(RESOURCES.getString("ROW_HEAD"));
			builder.append("\n");
			final String rowFormat = RESOURCES.getString("ROW_FORMAT");
			this.HURRICANES.stream().sorted(order == Order.DESC ? COMPARATOR.reversed() : COMPARATOR).forEach(h -> {
				builder.append(String.format(rowFormat, h.getName(), h.getCategory(), DATE_FORMAT.format(h.getLandfall())));
				builder.append("\n");
			});
			text.setText(builder.toString());
			String filename = WINDOWS_FILE_PREFIX + File.separator + FILE_NAMES.get(selected);
			// write text to file
			HurricaneIO.writeFile(filename, text.getText());
			JOptionPane.showMessageDialog(null, text, RESOURCES.getString("TITLE"),
					JOptionPane.INFORMATION_MESSAGE);
		});
	}
	

	/**
	 * Gets the sort order from the user
	 * @return
	 */
	private Optional<Order> promptForOrder() {
		Order[] orders = Order.values();
		Order selected = (Order) JOptionPane.showInputDialog(null, RESOURCES.getString("ORDER_MSG"), RESOURCES.getString("ORDER_TITLE"),
				JOptionPane.INFORMATION_MESSAGE, null, orders, orders[0]);
		return Optional.ofNullable(selected);
	}
	
}
