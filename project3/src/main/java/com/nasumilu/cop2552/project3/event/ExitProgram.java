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
package com.nasumilu.cop2552.project3.event;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.nasumilu.cop2552.project3.ExerciseActivityManager;
import com.nasumilu.cop2552.project3.Messages;
import com.nasumilu.cop2552.project3.model.AggregateModel;

/**
 * Required by the project. The ExitProgram listens for the
 * ExerciseActivityManager and outputs the its AggregateModel to a JTable.
 * 
 * @author Michael Lucas
 */
public class ExitProgram implements ActivityManagerListener {

	private static final Messages MESSAGES = Messages.getMessagesFor(ExitConsoleProgram.class);
	
	@Override
	public void activityManagerClosing(ExerciseActivityManager manager) {
		AggregateModel model = manager.getModel();

		if (model.isEmpty()) {
			JOptionPane.showMessageDialog(null, MESSAGES.message("ExitProgram.ModelEmpty"),
					MESSAGES.message("ExitProgram.DialogTitle"), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] columns = model.getColumnNames();
		String[][] data = model.getColumnData();

		JTable table = new JTable(data, columns);
		table.setEnabled(false);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		panel.add(table, BorderLayout.CENTER);
		JDialog dialog = new JDialog((Frame) null, MESSAGES.message("ExitProgram.DialogTitle"), true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setSize(800, 500);
		dialog.add(panel);
		dialog.setVisible(true);
	}

}
