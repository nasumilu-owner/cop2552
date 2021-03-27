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

import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.nasumilu.cop2552.project4.model.Hurricane;
import com.nasumilu.cop2552.project4.model.Menu;

/**
 * ActionListener used to calculate the average category of a set of Hurricane objects.
 * <br>
 * Handles the <strong>Display Average Cateogry</strong> action, {@link ActionListener#AVG_CATEGORY}.
 * @author Michael Lucas
 */
public class AvgCategoryActionListener implements ActionListener {

	private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(AvgCategoryActionListener.class.getName());
	
	private final Set<Hurricane> HURRICANES;
	private String result;

	public AvgCategoryActionListener(Set<Hurricane> hurricanes) {
		this.HURRICANES = hurricanes;
	}
	
	/**
	 * This action calculates the average category strength of a Hurricane set.
	 * 
	 * The results are lazy loaded into a string and cached, subsequent calls to
	 * this method will just present those results.
	 */
	@Override
	public void performAction(Menu menu) {
		if(menu.getSelected() != ActionListener.AVG_CATEGORY) {
			return;
		}
		if(null == this.result) {
			Double avg = this.HURRICANES.stream().collect(Collectors.averagingInt(Hurricane::getCategory));
			StringBuilder builder = new StringBuilder();
			builder.append(RESOURCES.getString("TITLE"));
			builder.append("\n\n");
			builder.append(RESOURCES.getString("HEADING"));
			builder.append("\n\n");
			builder.append(String.format(RESOURCES.getString("AVG_FORMAT"), avg));
			this.result = builder.toString();
		}
		JOptionPane.showMessageDialog(null, this.result, RESOURCES.getString("TITLE"), JOptionPane.INFORMATION_MESSAGE);
	}

}
