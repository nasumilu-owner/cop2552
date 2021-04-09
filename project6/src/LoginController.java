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
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller handles the login.fxml form.
 *
 */
public class LoginController implements Initializable {

	/** The username control	 */
	@FXML private TextField accountField;
	
	/** The password control */
	@FXML private PasswordField passwordField;
	
	/** The error label */
	@FXML private Label errorMsg;

	@FXML private Button nextButton;
	
	// Event listener to enable the login menu item when both the account and password
	// have more than 0 characters
	private final ChangeListener<? super String> listener = (observable, oldValue, newValue) -> {
		this.errorMsg.setVisible(false);
		this.nextButton.setDisable(this.accountField.getText().length() == 0 
				|| this.passwordField.getText().length() == 0);
	};
	
	/**
	 * LoginController initializer
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {				
		// bind the listener to the account and password controls
		// enable/disable the next button
		this.accountField.textProperty().addListener(listener);
		this.passwordField.textProperty().addListener(listener);
	}
	

	/**
	 * Handler for when the Login button is clicked
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void onClick(ActionEvent event) throws IOException {
		try {
			App.setRoot("question", App.getDao().authenticate(this.passwordField.getText(), this.accountField.getText()));
		} catch (AccountException e) {
			this.errorMsg.setText(e.getMessage());
			this.errorMsg.setVisible(true);
		}
	}
	
	
	/**
	 * Handler for when the cancel button is clicked
	 */
	@FXML
	public void onCancel() {
		Platform.exit();
	}
}