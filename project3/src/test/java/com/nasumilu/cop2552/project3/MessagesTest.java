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

import junit.framework.TestCase;

/**
 * @author Michael Lucas <mlucas@nasumilu.com>
 *
 */
public class MessagesTest extends TestCase {

	/**
	 * Test method for {@link com.nasumilu.cop2552.project3.Messages#message(java.lang.String, java.lang.Object[])}.
	 */
	public void testMessage() {
		Messages m = Messages.getMessagesFor(Bicycle.class);
		assertEquals("Biking", m.message("Bicycle.name"));
	}

}
