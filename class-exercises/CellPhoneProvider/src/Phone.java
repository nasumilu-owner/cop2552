import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
 *
 */
public class Phone implements CostItem {

	private final StringProperty modelProperty;
	private final DoubleProperty costProperty;
		
	public Phone() {
		this("Unknown", 0);
	}
	
	public Phone(String model, double cost) {
		this.modelProperty = new SimpleStringProperty(this, "model", model);
		this.costProperty = new SimpleDoubleProperty(this, "cost", cost);
	}
	
	public StringProperty modelProperty() {
		return this.modelProperty;
	}
	
	public String getModel() {
		return this.modelProperty.get();
	}
	
	public void setModel(String model) {
		this.modelProperty.set(model);
	}
	
	public DoubleProperty costProperty() {
		return this.costProperty;
	}
	
	public double getCost() {
		return this.costProperty.get();
	}
	
	public void setCost(double cost) {
		this.costProperty.set(cost);
	}
	
}
