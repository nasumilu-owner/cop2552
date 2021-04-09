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


import java.io.IOException;
import java.text.NumberFormat;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * AccountController used to display an accounts information.
 * See account.fxml
 */
public class AccountController implements AccountReceiver {
	
	/**
	 * The account to display
	 */
	private Accounts account;
	
	/**
	 * The account id field
	 */
	@FXML
	private TextField accountField;
	
	/**
	 * The status field
	 */
	@FXML
	private TextField statusField;
	
	/**
	 * The balance field
	 */
	@FXML
	private TextField balanceField;
	
	/**
	 * The credit limit field
	 */
	@FXML
	private TextField creditField;
	
	/**
	 * Event handler for when the logout button is clicked.
	 * @throws IOException
	 */
	@FXML
	public void onLogout() throws IOException {
		App.setRoot("login");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAccount(Accounts account) {
		this.account = account;
		try {
			this.accountField.setText(Integer.toString(account.getAcctID()));
			try {
				this.statusField.setText(Status.valueOf(account.getStatus()).toString());
			} catch (IllegalArgumentException e) {
				this.statusField.setStyle("-fx-text-fill: red");
				this.statusField.setText("Invalid account status!");
			}
			this.balanceField.setText(NumberFormat.getCurrencyInstance().format(account.getBalance()));
			this.creditField.setText(NumberFormat.getCurrencyInstance().format(account.getLimit()));
		} catch(IllegalArgumentException e) {
			
		}
	}
}
