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
package com.nasumilu.cop2552.project4.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.nasumilu.cop2552.project4.model.Hurricane;

/**
 * Utility class to handle the projects I/O operations.
 * 
 * TODO: I18N
 * @author Michael Lucas
 */
public class HurricaneIO {

	/**
	 * Reads a text file named <strong>NamedFloridaHurricanes.txt"</strong> found in the
	 * current working directory of hurricane data into a set
	 * 
	 * @return Set of Hurricane objects read from a file
	 */
	public static Set<Hurricane> readFile() {
		final Set<Hurricane> hurricanes = new HashSet<Hurricane>();
		try (FileInputStream input = new FileInputStream("NamedFloridaHurricanes.txt")) {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			Scanner scanner = new Scanner(input);
			scanner.forEachRemaining(s -> {
				String[] line = s.trim().split(",|:");
				hurricanes.add(
						new Hurricane(line[0], Integer.parseInt(line[1]), LocalDate.parse(line[2], dateFormatter)));
			});
			scanner.close();
		} catch (IOException ex) {
			System.err.println("Unable to open file!");
			System.exit(-1);
		}
		return hurricanes;
	}

	/**
	 * Writes the some String content to a file using {@link PrintWriter}
	 * 
	 * @param file the full-qualified file path (FQFP) intended to write to
	 * @param content the contents to write
	 */
	public static void writeFile(String file, String content) {
		try (PrintWriter writer = new PrintWriter(file)) {
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			//Need to i18n this string
			System.err.println("Unable to write to file " + file);
		}
	}

}
