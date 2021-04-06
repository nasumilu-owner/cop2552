import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
public class DataPlan implements CostItem {

	private final DoubleProperty costProperty;
	private final IntegerProperty amountProperty;
		
	public DataPlan() {
		this(0, 0);
	}
	
	public DataPlan(int amount, double cost) {
		this.amountProperty = new SimpleIntegerProperty(this, "amount", amount);
		this.costProperty = new SimpleDoubleProperty(this, "cost", cost);
	}
	
	public DoubleProperty amountProperty() {
		return this.amountProperty();
	}
	
	public int getAmount() {
		return this.amountProperty.get();
	}
	
	public void setAmount(int amount) {
		this.amountProperty.set(amount);
	}
	
	public double getCost() {
		return this.costProperty.get();
	}
	
	public void setCost(double cost) {
		this.costProperty.set(cost);
	}
	
	public DoubleProperty costProperty() {
		return this.costProperty;
	}
	
}
