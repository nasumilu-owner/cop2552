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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Simple data object which contains information about a Movie.
 *
 * <p>
 * This class implements a fluent mutator interface so method chaining is
 * possible.
 * </p>
 * <strong>For Example</strong> <br>
 * 
 * <pre>
 * <code>
 * Movie movie = (new Movie()).setTitle("My Movie Title")
 * 		.setRating(Rating.G)
 *      .setGenre(Genre.DRAMA)
 *      .setYearReleased(1999);
 * </code>
 * </pre>
 *
 * @author Michael Lucas
 */
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * An exclusive list of the possible Genre values.
	 * 
	 * <blockquote>There are more, but to keep within the sprint, the genres have
	 * been generalized.</blockquote>
	 * 
	 * I have added an additional genre of UNKNOWN which is intended to be a movie's 
	 * default genre value.
	 * 
	 * @author Michael Lucas
	 */
	public enum Genre {
		/** A science fiction genre */
		SCI_FI,
		/** An adventure genre */
		ADVENTURE,
		/** A drama genre */
		DRAMA,
		/** A war genre */
		WAR,
		/** A romance genre */
		ROMANCE,
		/** A thriller genre */
		THRILLER,
		/** A fantasy genre */
		FANTASY,
		/** An unknown or not determined genre */
		UNKNOWN;

		/**
		 * The Genre resources
		 */
		private static final ResourceBundle RESOURCES = ResourceBundle
				.getBundle(Movie.class.getPackageName() + "." + Genre.class.getSimpleName());

		/**
		 * Gets a <code>Genre</code> from a String value by replacing the hyphen (-)
		 * with an underscore and converting the value to upper case.
		 * 
		 * @param name a common hyphened Genre value
		 * @return the <code>Genre</code> value
		 */
		public static Genre fromName(String name) {
			return Genre.valueOf(name.toUpperCase().replaceAll("\\s|-", "_"));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return RESOURCES.getString(this.name() + ".name");
		}

	}

	/**
	 * Movie ratings based off of the Movie Picture Association MPA.
	 * 
	 * <a href=
	 * "https://en.wikipedia.org/wiki/Motion_Picture_Association_film_rating_system">See
	 * Wikipedia</a>
	 * 
	 * @author Michael Lucas
	 */
	public enum Rating {
		/** G rated movie */
		G,
		/** PG rated movie */
		PG,
		/** PG-13 rated movie */
		PG_13,
		/** R rated movie */
		R,
		/** NC-17 rated movie */
		NC_17,
		/** Not rate movie */
		NR,
		/** Unrated movie */
		UR;

		/**
		 * The Rating resource bundle
		 */
		private static final ResourceBundle RESOURCES = ResourceBundle
				.getBundle(Movie.class.getPackageName() + "." + Rating.class.getSimpleName());

		/**
		 * Gets a <code>Rating</code> from a String value by replacing the hyphen (-)
		 * with an underscore and converting the value to upper case.
		 * 
		 * @param name a common hyphened Rating value
		 * @return the <code>Rating</code> value
		 */
		public static Rating fromName(String name) {
			return Rating.valueOf(name.toUpperCase().replaceAll("\\s|-", "_"));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return RESOURCES.getString(this.name() + ".name");
		}
	}

	// The movie title
	private String title;
	// The year released
	private int yearReleased;
	// The genre
	private Genre genre;
	// The rating
	private Rating rating;

	private final PropertyChangeSupport PROPERTY_CHANGE_LISTENER;

	/**
	 * Constructs a <code>Movie</code> with no title (None), year released (-1),
	 * rating (Rating.NR), or genre (Genre.UNKNOWN).
	 */
	public Movie() {
		this("None", -1, Genre.UNKNOWN, Rating.NR);
	}

	/**
	 * Constructs a <code>Movie</code> with a title, year released, rating and
	 * genre.
	 * 
	 * @param title        the title of the movie
	 * @param yearReleased the year the movie was released
	 * @param rating       the movie's rating
	 * @param genre        the movie's genre
	 */
	public Movie(String title, int yearReleased, Genre genre, Rating rating) {
		this.PROPERTY_CHANGE_LISTENER = new PropertyChangeSupport(this);
		this.title = title;
		this.yearReleased = yearReleased;
		this.rating = rating;
		this.genre = genre;
	}

	/**
	 * Gets the title of the <code>Movie</code>
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the <code>Movie</code>
	 * 
	 * @param title the title to set
	 * @return the <code>Movie</code> whose title was set (fluent)
	 */
	public Movie setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Gets the year the <code>Movie</code> was released.
	 * 
	 * @return the yearReleased
	 */
	public int getYearReleased() {
		return yearReleased;
	}

	/**
	 * Sets the year the <code>Movie</code> was released.
	 * 
	 * @param yearReleased the yearReleased to set
	 * @return the <code>Movie</code> whose year released was set (fluent)
	 */
	public Movie setYearReleased(int yearReleased) {
		int oldValue = this.yearReleased;
		this.yearReleased = yearReleased;
		this.PROPERTY_CHANGE_LISTENER.firePropertyChange("yearReleased", oldValue, yearReleased);
		return this;
	}

	/**
	 * Gets the <code>Movie</code>'s {@link Genre}
	 * 
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * Sets the <code>Movie</code>'s {@link Genre}
	 * 
	 * @param genre the genre to set
	 * @return the <code>Movie</code> whose {@link Genre} was set (fluent)
	 */
	public Movie setGenre(Genre genre) {
		Genre oldValue = this.genre;
		this.genre = genre;
		this.PROPERTY_CHANGE_LISTENER.firePropertyChange("genre", oldValue, genre);
		return this;
	}

	/**
	 * Gets the <code>Movie</code>'s {@link Rating}
	 * 
	 * @return the rating
	 */
	public Rating getRating() {
		return rating;
	}

	/**
	 * Sets the <code>Movie</code>'s {@link Rating}
	 * 
	 * @param rating the rating to set
	 * @return the <code>Movie</code> whose {@link Rating} was set (fluent)
	 */
	public Movie setRating(Rating rating) {
		Rating oldValue = this.rating;
		this.rating = rating;
		this.PROPERTY_CHANGE_LISTENER.firePropertyChange("rating", oldValue, rating);
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		PROPERTY_CHANGE_LISTENER.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		PROPERTY_CHANGE_LISTENER.removePropertyChangeListener(propertyName, listener);
	}

}
