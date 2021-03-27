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
package com.nasumilu.cop2552.project5.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.nasumilu.cop2552.project5.model.Movie;
import com.nasumilu.cop2552.project5.model.ProducedMovies;

/**
 * A very basic input/reader class which reads a well-known file of movie data.
 * 
 * @author Michael Lucas
 */
public class MovieReader {

	/**
	 * The File of of movie data
	 */
	private File filename;
	
	/**
	 * Constructs a MovieReader that read the data from {@link File} argument <code>filename</code>
	 * @param filename a file of movie data in a well-known format
	 */
	private MovieReader(File filename) { 
		this.filename = filename;
	}
	
	/**
	 * Reads the movie data from a file into a {@link ProducedMovies} object.
	 * @return a <code>ProducedMovies</code> object
	 * @throws IOException if I/O error occured
	 */
	public ProducedMovies read() throws IOException {
		Scanner in = new Scanner(this.filename);
		ProducedMovies movies = new ProducedMovies();
		movies.setDirector(in.nextLine())
			.setComposer(in.nextLine());
		movies.addAll(this.readMovies(in));
		in.close();
		return movies;
	}
	
	/**
	 * Utility method used to read the comma delimiter portion of the movie data 
	 * file.
	 * 
	 * @param scanner the input to read movie data
	 * @return a list of <code>Movie</code> objects
	 */
	private List<Movie> readMovies(Scanner scanner) {
		ArrayList<Movie> movies = new ArrayList<>();
		while(scanner.hasNext()) {
			String[] line = scanner.nextLine().split(",");
			Movie m = (new Movie()).setTitle(line[0].trim())
					.setYearReleased(Integer.parseInt(line[1].trim()))
					.setGenre(Movie.Genre.fromName(line[2].trim()))
					.setRating(Movie.Rating.fromName(line[3].trim()));
			movies.add(m);
		}
		return movies;
	}
	
	/**
	 * Optionally, constructs a MovieReader by prompting the user for a file using JFileChooser.
	 * @return optional <code>MovieReader</code> that is present when a valid file is selected; otherwise not present.
	 */
	public static Optional<MovieReader> prompt() {
		MovieReader reader = null;
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Movie Data File", "txt");
		chooser.setFileFilter(filter);
		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) {
			reader = new MovieReader(chooser.getSelectedFile());
		}
		return Optional.ofNullable(reader);
	}
	
}
