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
package com.nasumilu.cop2552.project5.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * Data class that extends the <code>ProductionStaff</code> class to contain a
 * list of movies directed and composed mapped by the year they were released.
 * 
 * You could think of this as a one-to-many relationship. One
 * <code>ProductionStaff</code> to many <code>Movie</code>s.
 * 
 * The mapped list of <code>Movie</code> objects is `live`. In other words, if a <code>Movie</code>
 * object's genre is changed then the its container <code>ProducedMovie</code> will update the
 * relationship by removing it from a mapped <code>Movie.Genre</code>, if any, <code>List</code>
 * of <code>Movie</code>s; If there is no mapped <code>Movie</code> list then one is created and
 * added. 
 * 
 * See the {@link ProducedMovies#propertyChange(PropertyChangeEvent)} for details on how the 
 * relationship is updated.
 * 
 * @author Michael Lucas
 */
public class ProducedMovies extends ProductionStaff implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	// Each movie is stored in a list mapped by its genre
	private Map<Movie.Genre, List<Movie>> movies;

	/**
	 * Constructs a empty <code>ProducedMovies</code> without a director, composer,
	 * or list of movies
	 */
	public ProducedMovies() {
		super("None", "None");
		this.movies = new TreeMap<Movie.Genre, List<Movie>>();
	}

	/**
	 * Adds a <code>Collection</code> of movies to the movies produced.
	 * 
	 * @param movie the {@link Movie}s to add
	 */
	public void addAll(Collection<Movie> movie) {
		movie.stream().forEach(m -> this.addMove(m));
	}

	/**
	 * Gets a set of distinct {@link Movie.Genre}s currently mapped.
	 * 
	 * @return a set of {@link Movie.Genre}s
	 */
	public Set<Movie.Genre> getGenres() {
		return this.movies.keySet();
	}

	/**
	 * Adds a {@link Movie} to the map of movies produced and composed.
	 * 
	 * @param movie the {@link Movie} to add
	 */
	public void addMove(final Movie movie) {
		// Add a PropertyChangeListener so the mapped relationship
		// will update if the Movie's genre is updated.
		// Of course these mapped relationships could be derived JIT but the
		// project requires that the data is grouped by its Genre in an ArrayList.
		movie.addPropertyChangeListener("genre", this);
		final Movie.Genre genre = movie.getGenre();
		this.getMovies(genre).ifPresentOrElse(mList -> mList.add(movie), () -> {
			List<Movie> mList = new ArrayList<>();
			mList.add(movie);
			this.movies.put(genre, mList);
		});
	}

	/**
	 * Gets a List of movies for for a particular {@link Movie.Genre}
	 * 
	 * @param genre the {@link Movie.Genre}
	 * @return an optional list of {@link Movie}s if mapped; otherwise empty
	 */
	public Optional<List<Movie>> getMovies(Movie.Genre genre) {
		return Optional.ofNullable(this.movies.get(genre));
	}

	/**
	 * Removes a {@link Movie} from the mapped list of movies, if exists.
	 * 
	 * @param movie the {@link Movie} to remove
	 */
	public void removeMovie(final Movie movie) {
		Movie.Genre genre = movie.getGenre();
		this.getMovies(genre).ifPresent(mList -> {
			if (mList.removeIf(m -> m.equals(movie))) {
				// Remove this PropertyChangeListener because this
				// Movie is no longer related to this ProducedMovies.
				movie.removePropertyChangeListener("genre", this);
			}
		});
	}

	/**
	 * Gets the Map of {@link Movie}s
	 * 
	 * @return the movies
	 */
	public Map<Movie.Genre, List<Movie>> getMovies() {
		return movies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final Movie source = (Movie) evt.getSource();
		Movie.Genre oldValue = (Movie.Genre) evt.getOldValue();
		Movie.Genre newValue = (Movie.Genre) evt.getNewValue();
		this.getMovies(oldValue).ifPresent(movies -> movies.remove(source));

		this.getMovies(newValue).ifPresentOrElse(movies -> movies.add((Movie) evt.getSource()), () -> {
			List<Movie> mList = new ArrayList<>();
			mList.add(source);
			this.movies.put(newValue, mList);
		});
	}

}
