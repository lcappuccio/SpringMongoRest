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

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		try {
			verifyNullValue(name);
		} catch (PersonException e) {
			name = "";
		}
		this.name = checkStringLength(name);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		try {
			verifyNullValue(lastName);
		} catch (PersonException e) {
			lastName = "";
		}
		this.lastName = checkStringLength(lastName);
	}

	private String checkStringLength(final String stringToCheck) {
		String stringValidated = "";
		if (stringToCheck.length() > MAX_LENGTH) {
			stringValidated = stringToCheck.substring(0, MAX_LENGTH);
			logger.info("Name " + stringToCheck + " truncated to: " + stringValidated);
		} else {
			return stringToCheck;
		}
		return stringValidated;
	}

	private void verifyNullValue(String stringToVerify) throws PersonException {
		if (stringToVerify == null) {
			throw new PersonException("Invalid null value");
		}
	}

}
