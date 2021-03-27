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
package com.nasumilu.cop2552.project3.event;

import com.nasumilu.cop2552.project3.ExerciseActivityManager;

/**
 * ActivityManagerListener is used to listen for when the ExerciseActivityManager
 * is about to close.
 * 
 * @author Michael Lucas
 */
public interface ActivityManagerListener {

	/**
	 * Event listener which is called before the ExerciseActivityManager closes.
	 * 
	 * @param manager the ExerciseActivityManager about to close
	 */
	public void activityManagerClosing(ExerciseActivityManager manager);
	
}
