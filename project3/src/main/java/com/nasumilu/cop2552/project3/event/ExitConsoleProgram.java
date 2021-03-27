package com.nasumilu.cop2552.project3.event;

import java.util.Arrays;

import com.nasumilu.cop2552.project3.ExerciseActivityManager;
import com.nasumilu.cop2552.project3.Messages;
import com.nasumilu.cop2552.project3.model.AggregateModel;

/**
 * Listen for the ExerciseActivityManager to close an outputs the 
 * AggregateModel mode to the console.
 * 
 * @author Michael Lucas
 */
public class ExitConsoleProgram implements ActivityManagerListener {

	
	private static final Messages MESSAGES = Messages.getMessagesFor(ExitConsoleProgram.class);
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activityManagerClosing(ExerciseActivityManager manager) {
		AggregateModel model = manager.getModel();
		if (model.isEmpty()) {
			System.out.println(MESSAGES.message("ExitProgram.ModelEmpty"));
			return;
		}
		String format = MESSAGES.message("ConsoleExitProgram.rowFormat");
		String[] headers = model.getColumnNames();
		String heading = String.format(format, headers[0], headers[1], headers[2], headers[3], headers[4], headers[5]);
		System.out.println("+" + "-".repeat(heading.length() - 2) + "+");
		System.out.println(heading);
		System.out.println("+" + "-".repeat(heading.length() - 2) + "+");
		String[][] columnData = model.getColumnData();
		Arrays.stream(columnData)
			.limit(columnData.length - 1)
			.forEach(v -> System.out.println(String.format(format, v[0], v[1], v[2], v[3], v[4], v[5])));
		System.out.println("+" + "-".repeat(heading.length() - 2) + "+");
		String[] totals = columnData[columnData.length - 1];
		System.out.println(String.format(format, totals[0], totals[1], totals[2], totals[3], totals[4], totals[5]));
		System.out.println("+" + "-".repeat(heading.length() - 2) + "+");
	}

}
