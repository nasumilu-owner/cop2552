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
package com.nasumilu.cop2552.project3.factory;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import com.nasumilu.cop2552.project3.ExerciseActivity;
import com.nasumilu.cop2552.project3.ExerciseActivity.Intensity;

/**
 * @author Michael Lucas
 *
 */
public class SwingFactory extends AbstractExerciseActivityFactory {

	@Override
	protected Optional<ExerciseActivity> promptForActivity(List<ExerciseActivity> activities) {
		ExerciseActivity[] choices = new ExerciseActivity[activities.size()];
		activities.toArray(choices);
		ExerciseActivity choice = (ExerciseActivity) JOptionPane.showInputDialog(null,
				MESSAGES.message("swing.activity.prompt"), MESSAGES.message("swing.activity.title"),
				JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		return Optional.ofNullable(choice);
	}

	@Override
	protected Optional<ExerciseActivity> promptForIntensity(ExerciseActivity activity) {
		Intensity[] values = Intensity.values();
		Intensity choice = (Intensity) JOptionPane.showInputDialog(null,
				MESSAGES.message("swing.intensity.prompt", activity.getName().toLowerCase()),
				MESSAGES.message("swing.intensity.title"), JOptionPane.QUESTION_MESSAGE, null, values, values[0]);
		if (null != choice) {
			activity.setIntensity(choice);
			return Optional.of(activity);
		}
		return Optional.empty();
	}

	@Override
	protected Optional<ExerciseActivity> promptForAmount(ExerciseActivity activity) {
		Float amount = null;
		do {
			String input = JOptionPane.showInputDialog(null,
					MESSAGES.message("swing.amount.prompt", activity.getName().toLowerCase()),
					MESSAGES.message("swing.amount.title"), JOptionPane.INFORMATION_MESSAGE);
			if (input == null) {
				return Optional.empty();
			}
			try {
				amount = Float.parseFloat(input);
				if (amount < 1) {
					int option = JOptionPane.showConfirmDialog(null,
							MESSAGES.message("swing.amount.error.outofrange", amount),
							MESSAGES.message("swing.amount.error.title"), JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.ERROR_MESSAGE);
					if(JOptionPane.CANCEL_OPTION == option) {
						return Optional.empty();
					}
					amount = null;
				}
			} catch (NumberFormatException e) {
				int option = JOptionPane.showConfirmDialog(null,
						MESSAGES.message("swing.amount.error.notanumber", input.isBlank() ? MESSAGES.message("swing.amount.error.nothing") : input),
						MESSAGES.message("swing.amount.error.title"), JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.ERROR_MESSAGE);
				if (option == JOptionPane.CANCEL_OPTION) {
					return Optional.empty();
				}
			}
		} while (amount == null);
		activity.setAmount(amount);
		return Optional.of(activity);
	}

}
