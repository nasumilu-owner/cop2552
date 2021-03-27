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
package com.nasumilu.cop2552.project3;

/**
 * Bicycle exercise activity.
 * 
 * @author Michael Lucas
 */
public class Bicycle extends AbstractExerciseActivity {

	/**
	 * @see Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return MESSAGES.message("Bicycle.name");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Float getModerateRate() {
		return MESSAGES.value("Bicycle.moderate.intensity");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Float getVigorousRate() {
		return MESSAGES.value("Bicycle.vigorous.intensity");
	}

}
