package org.systemexception.springmongorest.model;

import org.springframework.data.annotation.Id;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.exception.PersonException;

/**
 * @author leo
 * @date 19/09/15 12:14
 */
public class Person {

	private final static Logger logger = LoggerImpl.getFor(Person.class);
	public static final int MAX_LENGTH = 50;

	@Id
	private String id;
	private String name, lastName;

	public Person() {
	}

	/**
	 * @param name
	 * @param lastName
	 * @throws PersonException if names don't fullfill requirements
	 */
	public Person(final String name, final String lastName) throws PersonException {
		if (name == null || lastName == null) {
			throw new PersonException("Null names not allowed");
		}
		this.name = checkStringLength(name);
		this.lastName = checkStringLength(lastName);
		logger.info("New person: " + name + ", " + lastName);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String checkStringLength(final String stringToCheck) {
		String stringValidated = "";
		if (stringToCheck.length() > MAX_LENGTH) {
			stringValidated = stringToCheck.substring(0, MAX_LENGTH);
			logger.info("Name " + stringToCheck + " truncated to: " + stringValidated);
		}
		return stringValidated;
	}
}
