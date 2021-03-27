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

import java.util.Comparator;

import com.nasumilu.cop2552.project5.model.Movie;
import com.nasumilu.cop2552.project5.model.ProducedMovies;

/**
 * @author Michael Lucas
 *
 */
public class ConsoleProcudedMovieView extends AbstractProducedMovieView {

	/**
	 * {@inheritDoc}
	 */
	public ConsoleProcudedMovieView(ProducedMovies producedMovies) {
		super(producedMovies);
	}

	@Override
	protected void display(Movie.Genre genre) {
		int col1Width = this.producedMovies.getMovies().values().stream().mapToInt(mList -> {
			return mList.stream().mapToInt(m -> m.getTitle().length()).max().orElse(0);
		}).max().orElse(15);
		String format = "| %-" + (col1Width) + "s| %-15s| %-12s| %n";
		String heading = String.format(format, "Movie Title", "Year Released", "Rating");
		int rowLength = heading.length();
		System.out.format("%1$s%2$s%1$s%n", "+", "-".repeat(rowLength - 4));
		System.out.format("| Director: %-"+(rowLength - 15)+"s|%n", this.producedMovies.getDirector());
		System.out.format("| Composer: %-"+(rowLength - 15)+"s|%n", this.producedMovies.getComposer());
		System.out.format("| Genre: %-"+(rowLength - 12)+"s|%n", genre);
		System.out.format("%1$s%2$s%1$s%n", "+", "-".repeat(rowLength - 4));
		System.out.print(heading);
		System.out.format("%1$s%2$s%1$s%n", "+", "-".repeat(rowLength - 4));
		this.producedMovies.getMovies(genre).ifPresent(movies -> {
			movies.sort(Comparator.comparing(Movie::getYearReleased));
			movies.forEach(movie -> System.out.format(format, movie.getTitle(), movie.getYearReleased(), movie.getRating()));
		});
		System.out.format("%1$s%2$s%1$s%n", "+", "-".repeat(rowLength - 4));
		System.out.format("%n%n");
	}

}
