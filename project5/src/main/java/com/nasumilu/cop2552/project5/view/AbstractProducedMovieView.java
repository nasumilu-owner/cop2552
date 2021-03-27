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
package com.nasumilu.cop2552.project5.view;

import com.nasumilu.cop2552.project5.model.Movie;
import com.nasumilu.cop2552.project5.model.ProducedMovies;

/**
 * An AbstractProducedMovieView provides the shared accessor and mutator methods for a 
 * {@link ProducedMovieView}
 * 
 * @author Michael Lucas
 */
public abstract class AbstractProducedMovieView implements ProducedMovieView {

	/**
	 * The {@link ProducedMovies} for this view.
	 */
	protected ProducedMovies producedMovies;
	
	/**
	 * Constructs an AbstractProducedMovieView with an empty {@link ProducedMovies} instance.
	 */
	public AbstractProducedMovieView() {
		this(new ProducedMovies());
	};
	
	/**
	 * Constructs an AbstractProducedMovieView with a {@link ProducedMovies} instance.
	 * @param producedMovies the ProducedMovie to view
	 */
	public AbstractProducedMovieView(ProducedMovies producedMovies) {
		this.producedMovies = producedMovies;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProducedMovies getProducedMovies() {
		return this.producedMovies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGetProducedMovies(ProducedMovies producedMovies) {
		this.producedMovies = producedMovies;
	}
	
	protected abstract void display(Movie.Genre genre);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display() {
		this.producedMovies.getGenres()
			.forEach(genre -> this.display(genre));
	}

}
