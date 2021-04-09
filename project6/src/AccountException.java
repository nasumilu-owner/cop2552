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
 * An accounts possible exceptions.
 */
public class AccountException extends Exception {

	public AccountException(String msg) {
		super(msg);
	}
	
	/**
	 * Used if the account id is malformed. (Not an integer)
	 * @return 
	 */
	public static AccountException createMalformedAccountId() {
		return new AccountException("Malformed Account Number!");
	}
	
	/**
	 * Used if the account is not found.
	 * @param id
	 * @return
	 */
	public static AccountException createAccountNotFound(String id) {
		return new AccountException(String.format("Account %s not found!", id));
	}
	
	/**
	 * USed if the account's data file is invalid
	 * @return
	 */
	public static AccountException createInvalidAccountFile() {
		return new AccountException("Invalid account data file!");
	}
	
	/**
	 * Used if the account id and password are do not match
	 * @return
	 */
	public static AccountException createUnauthorized() {
		return new AccountException("Unauthorized, please check you account/password!");
	}
	
	/**
	 * Used when the acount/security answer does not match
	 * @return
	 */
	public static AccountException createUnverified() {
		return new AccountException("Unauthorized, please check your answer!");
	}
	
	/**
	 * Used when an account's status is invalid
	 * @return
	 */
	public static AccountException createInvalidStatus() {
		return new AccountException("Invalid account status!");
	}
	
}
