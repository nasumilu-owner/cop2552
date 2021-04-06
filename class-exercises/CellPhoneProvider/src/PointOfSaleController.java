
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
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.StringConverter;

/**
 *
 */
public class PointOfSaleController implements Initializable {

	private final PointOfSaleModel model = new PointOfSaleModel();
	
	@FXML
	private ComboBox<DataPlan> dataPlanComboBox;

	@FXML
	private ComboBox<Phone> phonePlanComboBox;
	
	@FXML
	private CheckBox insuranceCheckBox;
	
	@FXML 
	private CheckBox hotspotCheckBox;
	
	@FXML
	private Label saleCostLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// set up the data plan combobox
		this.dataPlanComboBox.itemsProperty().bind(this.model.dataPlansProperty());
		this.dataPlanComboBox.setConverter(new StringConverter<DataPlan>() {
			@Override
			public DataPlan fromString(String value) {
				return model.getDataPlans().stream().filter(p -> {
					Double amount = Double.parseDouble(value.replaceAll("\sgig/mo", ""));
					return p.getAmount() == amount;
				}).findFirst().orElse(null);
			}

			@Override
			public String toString(DataPlan value) {
				return String.format("%d gig/mo", value.getAmount());
			}
		});

		// set up the phone plan combobox
		this.phonePlanComboBox.itemsProperty().bind(this.model.phonesProperty());
		this.phonePlanComboBox.setConverter(new StringConverter<Phone>() {

			@Override
			public Phone fromString(String value) {
				return model.getPhones().stream().filter(p -> p.equals(value)).findFirst().orElse(null);
			}

			@Override
			public String toString(Phone value) {
				return value.getModel();
			}
		});
	}
	
	@FXML
	public void onCostItemChanged(ActionEvent evt) {

		double value = 0.00;
		
		// add data plan if not empty
		SingleSelectionModel<DataPlan> dataSelection = this.dataPlanComboBox.getSelectionModel();
		if(!dataSelection.isEmpty()) {
			value += dataSelection.getSelectedItem().getCost();
		}
		
		// add phone if not empty
		SingleSelectionModel<Phone> phoneSelection = this.phonePlanComboBox.getSelectionModel();
		if(!phoneSelection.isEmpty()) {
			value += (phoneSelection.getSelectedItem().getCost() * 1.07);
		}
		
		//add insurance if checked
		if(this.insuranceCheckBox.isSelected()) {
			value += 5;
		}
		
		// add wifi hotspot
		if(this.hotspotCheckBox.isSelected()) {
			value += 10;
		}
	
		this.saleCostLabel.setText(MessageFormat.format("Cost: {0,number,currency}", value));

	}
	
	@FXML
	public void onExitClicked(ActionEvent evt) {
		Platform.exit();
	}
	
	@FXML
	public void onClearClicked(ActionEvent evt) {
		this.dataPlanComboBox.getSelectionModel().select(null);
		this.phonePlanComboBox.getSelectionModel().select(null);
		this.insuranceCheckBox.setSelected(false);
		this.hotspotCheckBox.setSelected(false);
		this.saleCostLabel.setText(MessageFormat.format("Cost: {0,number,currency}", 0));
	}

}
