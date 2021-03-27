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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Very simple class for getting application String literals and the 
 * exercise activity calorie consumption rates. 
 * 
 * @author Michael Lucas
 */
public class Messages {
	
	/**
	 * Cache the Messages object by their package name
	 */
	private static Map<String, Messages> RESOURCE_BUNDLES = new HashMap<>();
	
	/**
	 * Loads a packages ResouceBundle (Messages)
	 */
	private final ResourceBundle RESOURCE_BUNDLE;
	
	/**
	 * Cache for each MessageFormat rather than constructing them
	 * on each call.
	 */
	private Map<String, MessageFormat> FORMATS = new HashMap<>();
	
	/**
	 * Private constructor; this is a singleton please use {@link Messages.getMessageFormat()}
	 * @param clazz the class whose package is used to get the messages
	 */
	private Messages(Class<?> clazz) {
		String messages =  clazz.getPackageName() + ".messages";
		this.RESOURCE_BUNDLE = ResourceBundle.getBundle(messages);
	}
	
	/**
	 * Gets a MessagesFormat from the cache or constructs it for
	 * a key.
	 * 
	 * @param key the messages unique key
	 * @return the resources as a MessageFormat
	 */
	protected MessageFormat getMessageFormat(String key) {
		if(!this.FORMATS.containsKey(key)) {
			String tpl = this.RESOURCE_BUNDLE.getString(key);
			MessageFormat format = new MessageFormat(tpl);
			this.FORMATS.put(key, format);
		}
		return this.FORMATS.get(key);
	}
	
	/**
	 * Gets a Messages instance for a Class
	 * @param clazz used to obtain the messages.properties for the classes package
	 * @return the Messages object of a class
	 */
	public static Messages getMessagesFor(Class<?> clazz) {
		String key = clazz.getPackageName();
		if(!RESOURCE_BUNDLES.containsKey(key)) {
			RESOURCE_BUNDLES.put(key, new Messages(clazz));
		}
		return RESOURCE_BUNDLES.get(key);
	}
	
	/**
	 * Gets a formated messaged for a resource <code>key</code> using
	 * the variable list of <code>args</code>.
	 * 
	 * @param key the resource key
	 * @param args the variable list of keys
	 * @return a formated messages
	 */
	public String message(String key, Object... args) {
		return this.getMessageFormat(key).format(args);
	}
	
	/**
	 * Gets a String for resource <code>key</code>.
	 * 
	 * This is expected to be a plain old string rather than 
	 * a message format.
	 * 
	 * @param key the resource key
	 * @return the resource as a string
	 */
	public String message(String key) {
		return this.RESOURCE_BUNDLE.getString(key);
	}
	
	/**
	 * Gets a Float for resource <code>key</code>
	 * 
	 * @param key the resource key
	 * @return the resource as a Float
	 */
	public Float value(String key) {
		return Float.parseFloat(this.message(key));
	}

}
