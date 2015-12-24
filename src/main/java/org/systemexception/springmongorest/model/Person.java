package org.systemexception.springmongorest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.systemexception.springmongorest.exception.PersonException;

/**
 * @author leo
 * @date 19/09/15 12:14
 */
public class Person {

	private final static Logger logger = LoggerFactory.getLogger(Person.class);
	public static final int MAX_LENGTH = 50;

	@Id
	private String id;
	private String name, lastName;

	public Person() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		if (id != null ? !id.equals(person.id) : person.id != null) return false;
		if (name != null ? !name.equals(person.name) : person.name != null) return false;
		return !(lastName != null ? !lastName.equals(person.lastName) : person.lastName != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}
}
