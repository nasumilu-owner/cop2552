import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

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
public class PointOfSaleModel {

	private final ListProperty<DataPlan> dataPlansProperty;
	
	private final ListProperty<Phone> phonesProperty;
	
	public PointOfSaleModel() {
		this(new DataPlan[] {
				new DataPlan(8, 45.00),
				new DataPlan(16, 65),
				new DataPlan(20, 99)
		}, new Phone[] {
				new Phone("Model 100", 299.95),
				new Phone("Model 110", 399.95),
				new Phone("Model 120", 499.95)
		});
	}
	
	public PointOfSaleModel(DataPlan[] dataPlanes, Phone[] phones) {
		this(Arrays.asList(dataPlanes), Arrays.asList(phones));
	}
	
	public PointOfSaleModel(List<DataPlan> plans, List<Phone> phones) {
		this.dataPlansProperty = new SimpleListProperty<>(this, "dataPlans", FXCollections.observableList(plans));
		this.phonesProperty = new SimpleListProperty<>(this, "phones", FXCollections.observableList(phones));
	}
	
	public ListProperty<DataPlan> dataPlansProperty() {
		return this.dataPlansProperty;
	}
	
	public ListProperty<Phone> phonesProperty() {
		return this.phonesProperty;
	}
	
	public List<Phone> getPhones() {
		return this.phonesProperty.get();
	}
	
	public List<DataPlan> getDataPlans() {
		return this.dataPlansProperty.get();
	}
	
}
