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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

/**
 * @author Michael Lucas
 */
public class Main implements Runnable {

	/**
	 * ResourceBundle with all of the applications string literals
	 */
	private static ResourceBundle APP_RESOURCES = ResourceBundle.getBundle("application", Locale.getDefault());

	/**
	 * ResoruceBundle key for message when one of the patient existing, add, or
	 * remove files is not valid.
	 */
	private static final String PATIENT_REPOSITORY_INVALID_READ = "patient.repository.invalidRead";
	
	/**
	 * ResoruceBundle key for message when one of the patient existing, add, or
	 * remove files is not valid.
	 */
	private static final String PATIENT_REPOSITORY_INVALID_WRITE = "patient.repository.invalidWrite";
	
	/**
	 * ResourceBundle key for FileDialog when opening the patient's to remove file
	 */
	private static final String PATIENT_REPOSITORY_REMOVE = "patient.repository.remove";
	/**
	 * ResourceBundle key for FileDialog when opening the patient's to add file
	 */
	private static final String PATIENT_REPOSITORY_ADD = "patient.repository.add";
	/**
	 * ResourceBundle key for FileDialog when opening the existing patient
	 * information file
	 */
	private static final String PATIENT_REPOSITORY_OPEN = "patient.repository.open";

	/**
	 * ResourceBundle key for FileDialog when saving the merged patient information
	 * file
	 */
	private static final String PATIENT_REPOSITORY_MERGE = "patient.repository.merge";

	/**
	 * ResourceBundle key for FileDialog when saving the patient information which
	 * caused an error
	 */
	private static final String PATIENT_REPOSITORY_ERROR = "patient.repository.error";

	/**
	 * ResousceBundle key for JOptionPane when patient repository file already
	 * exits. Message may received on parameter %s = FQP of file selected
	 */
	private static final String PATIENT_REPOSITORY_EXISTS_MSG = "patient.repository.exitsMsg";

	/**
	 * ResourceBundle key for JOptionPane title when patient repository file exits.
	 */
	private static final String PATIENT_REPOSITORY_EXISTS_TITLE = "patient.repository.exitesTitle";

	/**
	 * ResourceBundle key for the JOptionPane message dialog when showing errors.
	 */
	private static final String ERROR_DIALOG_TITLE = "error.dialog.title";
	
	private static final String MERGE_COMPLETE_MSG = "merge.complete.msg";
	
	private static final String MERGE_COMPLETE_TITLE = "merge.complete.title";

	/**
	 * Used to report a Patient objects merge status. A MergeStatus is determined
	 * using the {@Patient#compareTo} method.
	 * 
	 * @see Main#addCompare(Patient, Patient)
	 * @see Main#removeCompare(Patient, Patient)
	 */
	private enum MergeStatus {
		ADD, // Flush and advance the add buffer
		REMOVE, // Do not flush the existing buffer and advance both existing and remove buffers
		NONE, // No special operation just flush and advance the exiting buffer
		ERROR; // Flush and advance the add/remove buffer to the error log
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		
		// Final output if all goes as planned
		String title = APP_RESOURCES.getString(MERGE_COMPLETE_TITLE);
		String msg = APP_RESOURCES.getString(MERGE_COMPLETE_MSG);
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			// init the patient readers
			PatientReader patientReader = Optional.ofNullable(patientRepositoryReader())
					.orElseThrow(() -> new NullPointerException(APP_RESOURCES.getString(PATIENT_REPOSITORY_INVALID_READ)));
			PatientReader addPatientReader = Optional.ofNullable(addPatientRepositoryReader())
					.orElseThrow(() -> new NullPointerException(APP_RESOURCES.getString(PATIENT_REPOSITORY_INVALID_READ)));
			PatientReader removePatientReader = Optional.ofNullable(removePatientRepositoryReader())
					.orElseThrow(() -> new NullPointerException(APP_RESOURCES.getString(PATIENT_REPOSITORY_INVALID_READ)));

			// init the patient writers
			PatientWriter patientWriter = Optional.ofNullable(mergePatientWriter())
					.orElseThrow(() -> new NullPointerException(APP_RESOURCES.getString(PATIENT_REPOSITORY_INVALID_WRITE)));

			PatientWriter errorWriter = Optional.ofNullable(errorPatientWriter())
					.orElseThrow(() -> new NullPointerException(APP_RESOURCES.getString(PATIENT_REPOSITORY_INVALID_WRITE)));

			// init the remove, add, existing patient inform from their prospective readers
			Patient remove = removePatientReader.hasNext() ? removePatientReader.next() : null;
			Patient add = addPatientReader.hasNext() ? addPatientReader.next() : null;
			Patient existing = patientReader.hasNext() ? patientReader.next() : null;

			// Does not use an Array any other Collection. The PatientReader implements
			// Iterator<Patient>, Iterable<Patient> so leveraging the convenience of 
			// Iterator#hasNext() & Iterator#next() is used.
			while (null != existing) {
				
				// removal is considered a pre-operation.
				MergeStatus removeStatus = this.removeCompare(existing, remove);
				// Perform a merge remove and advance existing and remove
				if (MergeStatus.REMOVE == removeStatus) {
					remove = removePatientReader.hasNext() ? removePatientReader.next() : null;
					existing = patientReader.hasNext() ? patientReader.next() : null;
				//Perform a merge error and advance remove
				} else if (MergeStatus.ERROR == removeStatus) {
					errorWriter.write(remove);
					remove = removePatientReader.hasNext() ? removePatientReader.next() : null;
				}

				// insert operations
				MergeStatus addStatus = this.addCompare(existing, add);
				// Perform a merge add and advance the add 
				if (MergeStatus.ADD == addStatus) {
					patientWriter.write(add);
					add = addPatientReader.hasNext() ? addPatientReader.next() : null;
				// Perform a merge error and advance the add
				} else if (MergeStatus.ERROR == addStatus) {
					errorWriter.write(add);
					add = addPatientReader.hasNext() ? addPatientReader.next() : null;
				// Perform a merge and advance the existing
				} else {
					patientWriter.write(existing);
					existing = patientReader.hasNext() ? patientReader.next() : null;
				}
			}
			// Flush the add buffer to the patientWriter because any left are greater than the last 
			// existing patient and since the data is considered to be ascending
			addPatientReader.forEachRemaining(p -> {
				try {
					patientWriter.write(p);
				} catch (IOException e) { 
					// need better error handling 
				}
			});
			
			// Flush the remove buffer to the errorWriter because any left are greater than the last
			// existing patient, therefore not found.
			removePatientReader.forEachRemaining(p -> {
				try {
					errorWriter.write(p);
				} catch (IOException e) {
					// need better error handling
				}
			});
			
			//close the resources
			patientReader.close();
			addPatientReader.close();
			removePatientReader.close();
			patientWriter.close();
			errorWriter.close();			
			
		} catch (NullPointerException | IOException e) {
			// change to an error dialog.
			msg = e.getMessage();
			msgType = JOptionPane.ERROR_MESSAGE;
			title = APP_RESOURCES.getString(ERROR_DIALOG_TITLE);
		}
		JOptionPane.showMessageDialog(null, msg, title, msgType);
		System.exit(0);
	}

	/**
	 * Basically a compareTo method that handles null values and reports an add
	 * merge status:
	 * <ul>
	 * <li>MergeStatus.NONE - one of the Patient objects is null</li>
	 * <li>MergeStatus.ERROR - <code>p1</code> is equal to <code>p2</code></li>
	 * <li>MergeStatus.ADD_BEFORE - <code>p2</code> is considered less than
	 * <code>p1</code> by using {@see Patient#compareTo(Patient)}</li>
	 * <li>MergeStatus.ADD_AFTER - <code>p2</code> is considered greater than
	 * <code>p1</code> by using {@see Patient#compareTo(Patient)}</li>
	 * </ul>
	 * 
	 * @param p1 the first <code>Patient</code> to compare
	 * @param p2 the second <code>Patient</code> to determine its add status.
	 * @return the <code>MergeStatus</code> of <code>p2</code>
	 */
	private MergeStatus addCompare(Patient p1, Patient p2) {
		if (null == p1 || null == p2) {
			return MergeStatus.NONE;
		}
		int compare = p1.compareTo(p2);
		if (0 == compare) {
			return MergeStatus.ERROR;
		} else if (0 < compare) {
			return MergeStatus.ADD;
		}
		return MergeStatus.NONE;
	}

	/**
	 * Basically a compareTo method that handles null values and reports a removal
	 * merge status:
	 * 
	 * <ul>
	 * <li>MergeStatus.NONE - one of the Patient objects is null.</li>
	 * <li>MergeStatus.REMOVE - <code>p2</code> is equal to <code>p1</code> when
	 * compared using {@see Patient#compareTo(Patient)}</li>
	 * <li>MergeStatus.ERROR - <code>p2</code> is less than <code>p1</code> when
	 * compared using {@see Patient#compareTo(Patient)}</li>
	 * </ul>
	 * 
	 * @param p1 the first <code>Patient</code> to compare
	 * @param p2 the second <code>Patient</code> to determine its removal status.
	 * @return the <code>MergeStatus</code> of <code>p2</code>
	 */
	private MergeStatus removeCompare(Patient p1, Patient p2) {
		if (null == p1 || null == p2) {
			return MergeStatus.NONE;
		}
		int compare = p1.compareTo(p2);
		if (0 == compare) {
			return MergeStatus.REMOVE;
		} else if (0 < compare) {
			return MergeStatus.ERROR;
		}
		return MergeStatus.NONE;
	}

	/**
	 * Entry point which runs the Patient information merging application in its own
	 * Thread
	 * 
	 * @see Thread
	 * @param args Not used with this project
	 */
	public static void main(String[] args) {
		// Run the application in its own thread
		new Thread(new Main()).start();
	}

	/**
	 * Utility method used to build a PatientReader for reading patient information
	 * to be removed.
	 * 
	 * @return a PatientReader for patient data to remove or null if the data file
	 *         is null
	 * @throws IOException if an I/O error occurs
	 */
	private static PatientReader removePatientRepositoryReader() throws IOException {
		File f = removePatientRepository();
		return null == f ? null : new PatientReader(new FileReader(f));
	}

	/**
	 * Utility method used to build a PatientReader for reading patient information
	 * to be added
	 * 
	 * @return a PatientReader for patient data to add or null if the data file is
	 *         null
	 * @throws IOException if an I/O error occurs
	 */
	private static PatientReader addPatientRepositoryReader() throws IOException {
		File f = addPatientRepository();
		return null == f ? null : new PatientReader(new FileReader(f));
	}

	/**
	 * Utility method used to build a PatientReader for reading the existing patient
	 * information. This is reader gets the main patient information to merge with
	 * the add/removal readers.
	 * 
	 * @return a Patient reader for existing patient data or null if the data file
	 *         is null
	 * @throws IOException if an I/O error occurs
	 */
	private static PatientReader patientRepositoryReader() throws IOException {
		File f = patientRepository();
		return null == f ? null : new ExtendedPatientReader(new FileReader(f));
	}

	/**
	 * Checks to see if the "RemovePatientList.txt" file exits in a well-known
	 * location otherwise prompts the user to select the remove patient file.
	 * 
	 * @return the file which MUST contain the patients to remove
	 */
	private static File removePatientRepository() {
		File f = new File(System.getProperty("user.dir") + File.separator + "RemovePatientList.txt");
		return f.exists() ? f : promptOpenFile(PATIENT_REPOSITORY_REMOVE);
	}

	/**
	 * Checks to see if the "NewPatientList.txt" file exits in a well-known location
	 * otherwise prompts the user to select the new (patients to add) file.
	 * 
	 * @return the file which MUST contain the patients to add
	 */
	private static File addPatientRepository() {
		File f = new File(System.getProperty("user.dir") + File.separator + "NewPatientList.txt");
		return f.exists() ? f : promptOpenFile(PATIENT_REPOSITORY_ADD);
	}

	/**
	 * Checks to see if the "PatientListW3.txt" file exits in a well-known location
	 * otherwise prompts the user to select the exiting patients file.
	 * 
	 * @return the file which MUST contain the exiting patient information to merge
	 *         with the add/remove files
	 */
	private static File patientRepository() {
		File f = new File(System.getProperty("user.dir") + File.separator + "PatientListW3.txt");
		return f.exists() ? f : promptOpenFile(PATIENT_REPOSITORY_OPEN);
	}

	/**
	 * Static factory method for getting the merge patient writer.
	 * 
	 * @return a valid PatientWriter or null
	 * @throws IOException if an I/O error occurs
	 */
	private static PatientWriter mergePatientWriter() throws IOException {
		File f = mergePatientRepository();
		return null != f ? PatientWriter.getWriter(f) : null;
	}

	/**
	 * Static factory method for getting the error patient writer
	 * 
	 * @return a valid PatientWriter or null
	 * @throws IOException if an I/O error occurs
	 */
	private static PatientWriter errorPatientWriter() throws IOException {
		File f = errorRepository();
		return null != f ? PatientWriter.getWriter(f) : null;
	}

	/**
	 * Utility method to resolve the merge patient file from a well-known path
	 * 
	 * If the file exists the user is prompted as to whether to overwrite the existing
	 * file or select another. 
	 * 
	 * @return valid file or null
	 */
	private static File mergePatientRepository() {
		String path = wellKnownPath() + File.separator + "PatientListW4.txt";
		File f = new File(path);
		return promptOverwrite(f, PATIENT_REPOSITORY_MERGE);
	}
	
	/**
	 * Utility method to resolve the error patient file.
	 * 
	 * @see Main#mergePatientWriter()
	 * @return valid file or null
	 */
	private static File errorRepository() {
		String path = wellKnownPath() + File.separator + "PatientErrorsW4.txt";
		File f = new File(path);
		return promptOverwrite(f, PATIENT_REPOSITORY_ERROR);
	}

	
	/**
	 * Utility method for obtaining a well-known path.
	 * 
	 * On Windows this is C:\SFC\COP2552\Project2
	 * for all others it is current directory derived from System.getProperty("user.dir")
	 * 
	 * @return
	 */
	private static String wellKnownPath() {
		String path = System.getProperty("user.dir");
		if (!System.getProperty("os.name").toLowerCase().matches("linux|unix")) {
			path = "C:" + File.separator + "SFC" + File.separator + "COP2552" + File.separator + "Project2";

		}
		
		return path;
	}

	/**
	 * Utility method to prompt notify the user that this application is going to overwrite an 
	 * existing file and allows them to cancel that operation by specifiying another file
	 * 
	 * @param file the existing file that is going to be overwritten
	 * @param saveTitle the title for the FileDialog if the user choses to specify another file
	 * @return a valid file or null
	 */
	private static File promptOverwrite(File file, String saveTitle) {
		if (file.exists()) {
			int option = JOptionPane.showConfirmDialog(null,
					String.format(APP_RESOURCES.getString(PATIENT_REPOSITORY_EXISTS_MSG), file),
					APP_RESOURCES.getString(PATIENT_REPOSITORY_EXISTS_TITLE), JOptionPane.YES_NO_OPTION);
			if (JOptionPane.NO_OPTION == option) {
				file = promptSaveFile(saveTitle);
			}
		}
		return file;
	}

	/**
	 * Utility method to get a {@see FileDialog}
	 * 
	 * @param title the title for the FileDialog
	 * @param mode  the FileDialog's mode
	 * @return a full qualified fill path or null if user cancels the selection
	 */
	private static File promptFileDialog(String title, int mode) {
		FileDialog dialog = new FileDialog((Frame) null, APP_RESOURCES.getString(title), mode);
		dialog.setFilenameFilter((File file, String filename) -> {
			return filename.matches(".*(\\.txt)$");
		});
		dialog.setVisible(true);
		String filename = dialog.getFile();
		return null != filename ? new File(dialog.getDirectory() + File.separator + dialog.getFile()) : null;
	}

	/**
	 * Utility method to get a {@see FileDialog} in save/write mode.
	 * 
	 * @param title the dialog's title
	 * @return a file path
	 */
	private static File promptSaveFile(String title) {
		return promptFileDialog(title, FileDialog.SAVE);
	}

	/**
	 * Gets a {@see FileDialog} in open mode.
	 * 
	 * @param title the dilog's title
	 * @return a file path
	 */
	private static File promptOpenFile(String title) {
		return promptFileDialog(title, FileDialog.LOAD);
	}

}
