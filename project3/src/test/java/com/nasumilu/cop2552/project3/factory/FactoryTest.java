package com.nasumilu.cop2552.project3.factory;

import com.nasumilu.cop2552.project3.ExerciseActivityManager;
import com.nasumilu.cop2552.project3.event.ExitConsoleProgram;
import com.nasumilu.cop2552.project3.event.ExitProgram;

import junit.framework.TestCase;

public class FactoryTest extends TestCase {

	public void testConsoleCreate() {
		ExerciseActivityManager manager = new ExerciseActivityManager();
		manager.addActivityManagerListener(new ExitConsoleProgram());
		manager.run();
	}
	
	public void testSwingCreate() {
		ExerciseActivityManager manager = new ExerciseActivityManager(new SwingFactory());
		manager.addActivityManagerListener(new ExitProgram());
		manager.run();
	}

}
