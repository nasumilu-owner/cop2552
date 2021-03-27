package com.nasumilu.cop2552.project3;

import java.util.Locale;

import com.nasumilu.cop2552.project3.event.ExitConsoleProgram;
import com.nasumilu.cop2552.project3.event.ExitProgram;
import com.nasumilu.cop2552.project3.factory.SwingFactory;

/**
 * Start the ExerciseActivtyManager
 */
public class Launcher {
	
	/**
	 * the intended application's entry point
	 * @param args "en" | "es" (default: currentl locale)
	 */
	public static void main(String[] args) {
		if(args.length == 1) {
			Locale.setDefault(Locale.forLanguageTag(args[0]));
		}
		ExerciseActivityManager manager = new ExerciseActivityManager(new SwingFactory());
		manager.addActivityManagerListener(new ExitConsoleProgram());
		manager.addActivityManagerListener(new ExitProgram());
		(new Thread(manager)).start();
	}
	
}
