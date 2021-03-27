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
package com.nasumilu.cop2552.project4.view;

import javax.swing.JOptionPane;

import com.nasumilu.cop2552.project4.event.ActionListener;
import com.nasumilu.cop2552.project4.model.Menu;

/**
 * Simple class that displays a Menu object for user input.
 * 
 * @author Michael Lucas
 */
public class MenuView {

	private Menu menu;
	
	public MenuView() {}
	
	public MenuView(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void display() {
		Object[] names = (Object[]) this.menu.getActions().values().toArray();
		String selected = (String) JOptionPane.showInputDialog(null, "Message", "Title",
				JOptionPane.INFORMATION_MESSAGE, null, names, names[0]);
		
		// Cancel button clicked dispatch the exit action
		if(null == selected) {
			menu.setSelected(ActionListener.EXIT);
		}
		// dispatch the selected action
		menu.setSelectedValue(selected);
	}
	
}
