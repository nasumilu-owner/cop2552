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

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import com.nasumilu.cop2552.project5.model.Movie;
import com.nasumilu.cop2552.project5.model.ProducedMovies;

/**
 * A javax.swing view for a {@link ProducedMovieView}
 * @author Michael Lucas
 */
public class SwingProcudedMovieView extends AbstractProducedMovieView {

	/**
	 * {@inheritDoc}
	 */
	public SwingProcudedMovieView() {
		super();
	};
	
	/**
	 * {@inheritDoc}
	 */
	public SwingProcudedMovieView(ProducedMovies producedMovies) {
		super(producedMovies);
	}
	
	// Converts the ProducedMovies to html.
	private String asHtml(Movie.Genre genre) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append("<html><p><b>Director:</b> ")
			.append(this.producedMovies.getDirector())
			.append("</p>")
			.append("<p><b>Composer:</b> ")
			.append(this.producedMovies.getComposer())
			.append("</p><br>")
			.append("<p><b>Genre:</b> ")
			.append(genre)
			.append("</p><br><br>");
		this.producedMovies.getMovies(genre).ifPresentOrElse(movies -> {
			builder.append("<table border=1 cellspacing=0 cellpadding=3><tr><td>Movie Title</td>")
				.append("<td>Year Released</td>")
				.append("<td>Rating</td></tr>");
			movies.stream().sorted(Comparator.comparing(Movie::getYearReleased))
				.forEach(movie -> {
					builder.append("<tr><td>")
						.append(movie.getTitle())
						.append("</td><td>")
						.append(movie.getYearReleased())
						.append("</td><td>")
						.append(movie.getRating())
						.append("</td></tr>");
				});
			builder.append("</table>");
			}, () -> builder.append("<p><b><font color=red>No Movies Found!</font></b></p>"));
		builder.append("</html>");
		return builder.toString();
	}
	
	@Override
	protected void display(Movie.Genre genre) {
		this.displayView(this.asHtml(genre));
	}

	// Displays the html converted ProducedMovie in a JTextPane
	private void displayView(String str) {
		JTextPane pane = new JTextPane();
		pane.setContentType("text/html");
		pane.setEditable(false);
		pane.setText(str);
		JOptionPane.showMessageDialog(null, pane, "Produced Movies", JOptionPane.INFORMATION_MESSAGE);
	}


}
