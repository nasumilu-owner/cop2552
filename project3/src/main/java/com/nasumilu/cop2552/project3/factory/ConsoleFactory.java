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
import java.util.Scanner;

import com.nasumilu.cop2552.project3.ExerciseActivity;
import com.nasumilu.cop2552.project3.ExerciseActivity.Intensity;

/**
 * @author Michael Lucas
 *
 */
public class ConsoleFactory extends AbstractExerciseActivityFactory implements AutoCloseable {

	private final Scanner KEYBOARD;
	
	public ConsoleFactory() {
		this.KEYBOARD = new Scanner(System.in);
	}

	@Override
	protected Optional<ExerciseActivity> promptForActivity(List<ExerciseActivity> activities) {
		ExerciseActivity activity = null;
		String menuFormat = "%d - %s%n";
		String quitChar = MESSAGES.message("console.menu.quit");
		do {
			for (int i = 0; i < activities.size(); i++) {
				System.out.printf(menuFormat, i, activities.get(i).getName());
			}
			System.out.print(MESSAGES.message("console.activity.prompt", quitChar));
			String input = this.KEYBOARD.nextLine();
			if (input.equalsIgnoreCase(quitChar)) {
				return Optional.empty();
			}
			try {
				int choice = Integer.parseInt(input);
				activity = activities.get(choice);
			} catch (NumberFormatException | IndexOutOfBoundsException ex) {
				System.out.println(MESSAGES.message("console.activity.error",
						activities.size() - 1, quitChar, input));
			}
		} while (null == activity);
		return Optional.of(activity);
	}

	@Override
	protected Optional<ExerciseActivity> promptForIntensity(ExerciseActivity activity) {
		Intensity[] intensities = Intensity.values();
		Intensity intensity = null;
		String menuFormat = "%d - %s%n";
		String cancelChar = MESSAGES.message("console.menu.cancel");
		do {
			for(Intensity i : intensities) {
				System.out.printf(menuFormat, i.ordinal(), i);
			}
			System.out.print(MESSAGES.message("console.intensity.prompt", activity.getName().toLowerCase(), cancelChar));
			String input = this.KEYBOARD.nextLine();
			if(input.equalsIgnoreCase(cancelChar)) {
				return Optional.empty();
			}
			try {
				int choice = Integer.parseInt(input);
				intensity = intensities[choice];
			} catch (NumberFormatException | IndexOutOfBoundsException ex) {
				System.out.println(MESSAGES.message("console.intensity.error",
						intensities.length - 1, cancelChar, input));
			}
			
		} while(null == intensity);
		activity.setIntensity(intensity);
		return Optional.of(activity);
	}

	@Override
	protected Optional<ExerciseActivity> promptForAmount(ExerciseActivity activity) {
		Float amount = null;
		String cancelChar = MESSAGES.message("console.menu.cancel");
		do {
			System.out.print(MESSAGES.message("console.amount.prompt", activity.getName().toLowerCase()));
			String input = this.KEYBOARD.nextLine();
			if(input.equalsIgnoreCase(cancelChar)) {
				return Optional.empty();
			}
			try {
				amount = Float.parseFloat(input);
				if (amount < 1) {
					System.out.println(MESSAGES.message("console.amount.error.outofrange", amount));
					amount = null;
				}
			} catch(NumberFormatException ex) {
				System.out.println(MESSAGES.message("console.amount.error.notanumber", 
						cancelChar, 
						input.isBlank() ? MESSAGES.message("console.amount.error.nothing") : input));
			}
		} while(null == amount);
		activity.setAmount(amount);
		return Optional.of(activity);
	}

	@Override
	public void close() throws Exception {
		this.KEYBOARD.close();
	}

}
