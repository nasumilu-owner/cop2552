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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 */
public class QuestionController implements AccountReceiver, Initializable {

	private Accounts accounts;
	
	private StringProperty questionProperty = new SimpleStringProperty();
	
	@FXML
	private Label securityQuestion;
	
	@FXML
	private TextField securityAnswer;
	
	@FXML
	private Label errorMsg;
	
	@FXML
	private Button loginButton;
	
	/**
	 * LoginController initializer
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.securityQuestion.textProperty().bind(this.questionProperty);
		this.securityAnswer.textProperty().addListener((observable, newValue, oldValue) ->
			this.loginButton.setDisable(this.securityAnswer.getText().length() == 0));
	}
		
	@FXML
	public void onCancel(ActionEvent event) throws IOException {
		App.setRoot("login");
	}
	@FXML
	public void onLogin(ActionEvent event) throws IOException {
		try {
			App.setRoot("account", App.getDao().verify(this.securityAnswer.getText(), this.accounts.getAcctID()));
			return;
		} catch (AccountException e) {
			this.errorMsg.setText(e.getMessage());
			this.errorMsg.setVisible(true);
		}
	}

	@Override
	public void setAccount(Accounts account) {
		this.accounts = account;
		this.questionProperty.set(account.getSecurityQ());
	}
	
}
