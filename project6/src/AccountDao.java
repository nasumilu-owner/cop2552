import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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

/**
 * Very basic data access object (DAO) for getting account information
 */
public class AccountDao {

	/**
	 * Map of accounts by their account id
	 */
	private final Map<Integer, Accounts> ACCOUNTS;

	/**
	 * Construct an AccountDao from a List of accounts
	 * @param accounts
	 */
	private AccountDao(List<Accounts> accounts) {
		this.ACCOUNTS = new HashMap<>();
		accounts.forEach(a -> this.ACCOUNTS.put(a.getAcctID(), a));
	}
	
	/**
	 * Gets an Accounts object by its account id.
	 * 
	 * @param accountId the account's id
	 * @return the account 
	 * @throws AccountException when an account does not exist.
	 */
	public Accounts getAccountById(int accountId) throws AccountException {
		Accounts account = this.ACCOUNTS.get(accountId);
		if(null == account) {
			throw AccountException.createAccountNotFound(Integer.toString(accountId));
		}
		return account;
	}
	
	/**
	 * Gets an Accounts object by its id
	 * 
	 * @param accountId the account's id
	 * @return the account
	 * @throws AccountException either the account number is malformed or it does not exist
	 */
	public Accounts getAccountById(String accountId) throws AccountException {
		Accounts account = null;
		try {
			int id = Integer.parseInt(accountId);
			account = this.getAccountById(id);
		} catch(NumberFormatException ex) {
			throw AccountException.createMalformedAccountId();
		}
		return account;
	}
	
	/**
	 * Utility method to authenticate an account id and password
	 * @param password the account password
	 * @param accountId the account id
	 * @return if account/password match that Accounts object
	 * @throws AccountException if the account/password does not match
	 */
	public Accounts authenticate(String password, String accountId) throws AccountException {
		Accounts account = this.getAccountById(accountId);
		if(!account.getPassword().equals(password)) {
			throw AccountException.createUnauthorized();
		}
		return account;
	}
	
	/**
	 * Utility method to verify an account's security question.
	 * 
	 * @param answer the answer to the security question
	 * @param accountId the account id to check 
	 * @return if the account/security answer match that Accounts object
	 * @throws AccountException if the account/security answer does not match
	 */
	public Accounts verify(String answer, int accountId) throws AccountException {
		Accounts account = this.getAccountById(accountId);
		if(!account.getSecurityA().equals(answer)) {
			throw AccountException.createUnverified();
		}
		return account;
	}

	/**
	 * Prompts the user for an Accounts data file. 
	 * @param stage
	 * @return Optional AccountDao that is present when a valid file is read; empty othewise
	 */
	public static Optional<AccountDao> prompt(Stage stage) {
		AccountDao dao = null;
		// https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Account Data");
		chooser.getExtensionFilters().add(new ExtensionFilter("Account Data File", "*.dat"));
		File selected = chooser.showOpenDialog(stage);

		if (null != selected) {
			try {
				dao = new AccountDao(readData(selected));
			} catch (ClassNotFoundException | IOException e) { 
				System.out.println(e.getMessage());
			}
		}

		return Optional.ofNullable(dao);

	}

	// Internal method for reading the data file into a list of Accounts object
	private static List<Accounts> readData(File file) throws IOException, ClassNotFoundException {
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream objStream = new ObjectInputStream(in);

		List<Accounts> accounts = new ArrayList<>();
		try {
			Accounts a = (Accounts) objStream.readObject();
			while (null != a) {
				accounts.add(a);
				a = (Accounts) objStream.readObject();
			}
		} catch (EOFException ex) {
			// EOF
		} finally {
			objStream.close();
		}
		return accounts;
	}

}
