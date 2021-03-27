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
import java.io.File;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Very simple class which writes a Patient in a well-known format.
 * 
 * The very generalized OutputStreamWriter was used so System.out could
 * be used for testing rather than writing to files. 
 * 
 * @author Michael Lucas
 */
public class PatientWriter implements Closeable, Flushable {

	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMddyyyy");
	
	private final OutputStreamWriter writer;

	/**
	 * Constructs an {@link OutputStreamWriter} who specifically is used to write
	 * Patient objects in a well-known format.
	 * 
	 * @param writer
	 */
	private PatientWriter(OutputStreamWriter writer) {
		this.writer = writer;
	}

	/**
	 * Writes a Patient to output
	 * 
	 * Format of output:<br>
	 * {@link Patient#getId()}<br>
	 * {@link Patient#getName()}<br>
	 * {@link Patient#getDob()}<br>
	 * 
	 * @param p the Patient object to write
	 * @throws IOException if an I/O error occurs
	 */
	public void write(Patient p) throws IOException {
		this.writer.write(p.getId() + "\n");
		this.writer.write(p.getName() + "\n");
		this.writer.write(p.getDob() + "\n");
		this.flush();
	}
	
	/**
	 * Static factory method, most because the project standard request to write the
	 * system's date as the first line of the file.
	 * 
	 * @param out the out stream to write the patient information to
	 * @return an instance of PatientWriter
	 * @throws IOException if an I/O error occurs
	 */
	public static PatientWriter getWriter(OutputStreamWriter out) throws IOException {
		PatientWriter writer = new PatientWriter(out);
		writer.writer.write(LocalDate.now().format(FORMATTER)+"\n");
		return writer;
	}
	
	/**
	 * Static factory method to create PatientWriter whose output is flushed to a file.
	 * 
	 * @param file the file to write the patient information to
	 * @return an instance of PatientWriter
	 * @throws IOException if an I/O error occurs
	 */
	public static PatientWriter getWriter(File file) throws IOException {
		return getWriter(new FileWriter(file));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		this.writer.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() throws IOException {
		this.writer.flush();
	}

}
