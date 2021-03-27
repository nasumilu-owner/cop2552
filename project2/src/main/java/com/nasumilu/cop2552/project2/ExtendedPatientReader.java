package com.nasumilu.cop2552.project2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Reads a well-known formatted patient data from an {@link InputStreamReader}. 
 * <br>
 * <strong>IMPORTANT: The data must be in the following format or unintended results may occur:</strong><br>
 * &lt;patient id&gt;<br>
 * &lt;last name, first name&gt;<br>
 * &lt;date of birth&gt;<br>
 * &lt;year add/joined&gt;<br>
 * 
 * @author Michael Lucas
 */
public class ExtendedPatientReader extends PatientReader {

	/**
	 * Extended patient information files have a heading. Therefore, this 
	 * construct will read the first line which MUST be the files heading.
	 * 
	 * @see PatientReader#getHeading()
	 * @param reader the input stream to get data from
	 * @throws IOException if an I/O error occurs
	 */
	public ExtendedPatientReader(InputStreamReader reader) throws IOException {
		super(reader, true);
	}

	/**
	 * Read four lines from the {@link InputStreamReader} and constructs a Patient
	 * object.
	 * 
	 * Simple, this overloads the existing {@link PatientReader#readPatient()} method 
	 * and reads a fourth line, which is expected to be the patient's year added.
	 * 
	 * @return A Patient object from the underlying InputStreamReader
	 * @throws NullPointerException  when any of the lines read are null
	 * @throws NumberFormatException when the value read is not an integer
	 */
	protected Patient readPatient() throws NullPointerException {
		Patient patient = super.readPatient();
		try {
			String yearAdded = Optional.ofNullable(this.readLine()).orElseThrow(() -> new NullPointerException());
			patient.setYearAdded(Integer.parseInt(yearAdded));
			return patient;
		} catch(IOException ex) {}
		
		return null;
	}
}
