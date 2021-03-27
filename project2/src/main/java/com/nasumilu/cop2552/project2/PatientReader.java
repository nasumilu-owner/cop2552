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
package com.nasumilu.cop2552.project2;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Reads patient information in a well-known format. This class performs
 * <strong>ZERO</strong> validation, and makes no guarantee if the data input
 * stream isn't well-formated.
 * 
 * The {@link Closeable} interface is implemented to ensure that the
 * {@link InputStreamReader} is closed when all references are lost.
 * 
 * The project said no Array's or data structures so this class implements the
 * {@link Iterator} interface reading one Patient in advance into a buffer. This
 * class also implements {@link Iterable} so its possible to iterate over the
 * {@link InputStreamReader}.
 * 
 * @author Michael Lucas
 */
public class PatientReader implements Closeable, Iterator<Patient>, Iterable<Patient> {

	private final InputStreamReader reader;

	/**
	 * An optional heading of the InputStreamReader
	 */
	private String heading;
	
	/**
	 * Buffer to read one Patient ahead
	 */
	private Patient next;

	/**
	 * Constructs a PaitentReader with the data read from parameter
	 * {@link InputStreamReader}
	 * 
	 * @param reader the input stream used to read patient data
	 */
	public PatientReader(InputStreamReader reader) {
		this.reader = reader;
		this.next = this.readPatient();
	}
	
	/**
	 * Constructs a PaitentReader reading the data from an {@link InputStreamReader} where
	 * the first line of text is considered the streams heading and all other data is in 
	 * a well-known format. 
	 * 
	 * @param reader InputStreamReader used to read the data
	 * @param hasHeading flag indicating that the stream contains a heading
	 * @throws IOException if any I/O errors occur when reading the heading
	 */
	public PatientReader(InputStreamReader reader, boolean hasHeading) throws IOException {
		this.reader = reader;
		if(hasHeading) {
			this.heading = this.readLine();
		}
		this.next = this.readPatient();
	}
	
	public boolean hasHeading() {
		return null != this.heading;
	}
	
	public String getHeading() {
		return this.heading;
	}

	/**
	 * Read three lines from the {@link InputStreamReader} and constructs a Patient
	 * object.
	 * 
	 * Line 1, MUST be the patient's id, line 2 MUST be the patients name, 
	 * line 3 MUST be the patients' date of birth. <br>
	 * <strong>Other than check to see if a value exists (not null), no
	 * other validate is performed</strong>. Data arranged in any other format
	 * will produce unintended results.
	 * 
	 * @return A Patient object from the underlying InputStreamReader
	 * @throws NullPointerException when any of the lines read are null
	 */
	protected Patient readPatient() throws NullPointerException {
		try {
			String id = Optional.ofNullable(this.readLine()).orElseThrow(() -> new NullPointerException());
			String name = Optional.ofNullable(this.readLine()).orElseThrow(() -> new NullPointerException());
			String dob = Optional.ofNullable(this.readLine()).orElseThrow(() -> new NullPointerException());
			return new Patient(id, name, dob, null);
		} catch (IOException ex) { }
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return null != this.next;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Patient next() {
		Patient current = this.next;
		try {
			this.next = this.readPatient();
		} catch (NullPointerException ex) {
			this.next = null;
		}
		return Optional.ofNullable(current).orElseThrow(() -> new NoSuchElementException());
	}

	/**
	 * A read line utility method
	 * 
	 * @return one line of the InputStreamReader as a String
	 * @throws IOException when an I/O error occurs
	 */
	protected String readLine() throws IOException {
		int ch = this.reader.read();
		StringBuilder strBuilder = new StringBuilder();
		while (ch > 0 && ch != '\n') {
			strBuilder.append((char) ch);
			ch = this.reader.read();
		}
		return  strBuilder.length() > 0 ? strBuilder.toString().trim() : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		this.reader.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Patient> iterator() {
		return this;
	}

}
