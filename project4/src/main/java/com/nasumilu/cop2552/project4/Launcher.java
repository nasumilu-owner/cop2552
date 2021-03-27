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
package com.nasumilu.cop2552.project4;

import java.util.Set;

import com.nasumilu.cop2552.project4.event.ActionListener;
import com.nasumilu.cop2552.project4.event.ActiveYearActionListener;
import com.nasumilu.cop2552.project4.event.AvgCategoryActionListener;
import com.nasumilu.cop2552.project4.event.GroupByActionListener;
import com.nasumilu.cop2552.project4.event.SortActionListener;
import com.nasumilu.cop2552.project4.io.HurricaneIO;
import com.nasumilu.cop2552.project4.model.Hurricane;
import com.nasumilu.cop2552.project4.model.Menu;
import com.nasumilu.cop2552.project4.view.MenuView;

/**
 * Very simple application which demonstrates Java's stream API and Comparator
 * class.
 * 
 * The general flow of this is a basic chain-of-responsibility. Observers
 * registered as ActionListeners handle dispatched menu selection events. Its
 * simple because, a more robust chain-of-responsibility would have a way to stop
 * propagation once the event was handled. In this case all listeners are fired but
 * should check as to whether the action is their responsibility.
 * 
 * @author Michael Lucas
 */
public class Launcher implements Runnable {

	public static void main(String[] args) {
		(new Thread(new Launcher())).start();
	}

	@Override
	public void run() {
		// Build the menu
		Menu menu = new Menu();

		// read the hurricane data
		final Set<Hurricane> hurricanes = HurricaneIO.readFile();

		// register the action listeners
		menu.addActionListener(new SortActionListener(hurricanes));
		menu.addActionListener(new GroupByActionListener(hurricanes));
		menu.addActionListener(new AvgCategoryActionListener(hurricanes));
		menu.addActionListener(new ActiveYearActionListener(hurricanes));

		// Action listener for an exit action
		menu.addActionListener(m -> {
			if (m.getSelected() == ActionListener.EXIT) {
				System.exit(0);
			}
		});

		// Create the view and display
		MenuView view = new MenuView(menu);
		while (true) {
			view.display();
		}
	}
}
