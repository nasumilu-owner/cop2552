package com.nasumilu.cop2552.project5;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.nasumilu.cop2552.project5.io.MovieReader;
import com.nasumilu.cop2552.project5.model.ProducedMovies;
import com.nasumilu.cop2552.project5.view.ConsoleProcudedMovieView;
import com.nasumilu.cop2552.project5.view.ProducedMovieView;
import com.nasumilu.cop2552.project5.view.SwingProcudedMovieView;

/**
 * Launcher is the main entry point.
 * 
 * Possible case incentive arguments are:
 * <ul>
 * <li>console - Displays movie data in the console</li>
 * <li>swing - Displays movie data using javax.swing</li>
 * </ul>
 * <br>
 * <strong>Run in swing mode with Maven use:</strong><br>
 * <pre><code>
 * $ mvn clean compile exec:java@swing
 * </code></pre>
 * <strong>Run in console mode with Maven use:</strong><br>
 * <pre><code>
 * $ mvn clean compile exec:java@console
 * </code></pre> 
 */
public class Launcher implements Runnable {

	/**
	 * Possible view modes
	 * 
	 * @author Michael Lucas
	 */
	private enum Mode {
		/**
		 * Displays the movie data using javax.swing
		 */
		SWING,
		/**
		 * Display the movie data using System.out
		 */
		CONSOLE;
	}

	/**
	 * The view mode
	 */
	private final Mode mode;

	/**
	 * Default Launcher loads movie data and views in Mode.SWING
	 */
	private Launcher() {
		this(Mode.SWING);
	}

	/**
	 * Loads movie data and shows it in a view Mode
	 * 
	 * @param mode the view mode
	 */
	private Launcher(Mode mode) {
		this.mode = mode;
	}

	/**
	 * Main entry point
	 * 
	 * Possible case incentive arguments are:
	 * <ul>
	 * <li>console - Displays movie data in the console</li>
	 * <li>swing - Displays movie data using javax.swing</li>
	 * </ul>
	 * 
	 * @param args an array of arguments
	 */
	public static void main(String[] args) {
		Mode m = Mode.SWING;
		if (args.length > 0) {
			try {
				m = Mode.valueOf(args[0].toUpperCase());
			} catch (IllegalArgumentException ex) {
				System.err.println("Unable to run in mode, " + args[0]);
				System.exit(-1);
			}
		}
		Launcher runnable = new Launcher(m);
		(new Thread(runnable)).start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// Gets a file from a JFileChooser
		// if present initializes a view and displays
		MovieReader.prompt().ifPresent(reader -> {
			try {
				// Get the ProducedMovieView class
				Class<? extends ProducedMovieView> viewClass;
				if (this.mode == Mode.SWING) {
					viewClass = SwingProcudedMovieView.class;
				} else {
					viewClass = ConsoleProcudedMovieView.class;
				}
				// read the file contents
				ProducedMovies movies = reader.read();
				// init the view
				ProducedMovieView view = viewClass.getConstructor(ProducedMovies.class).newInstance(movies);
				// display
				view.display();
				
			} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				// IOExcepetion like file not found
				if (ex instanceof IOException) {
					System.err.println("Unable to reader data!");
				} else {
					// Security issue init the view class
					System.err.println("Unable to instialize the view!");
				}
				System.exit(-1);
			}
		});
	}
}
