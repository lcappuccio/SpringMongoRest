package org.systemexception.springmongorest.test;

import org.junit.Test;
import org.systemexception.springmongorest.exception.PersonException;
import org.systemexception.springmongorest.model.Person;

import static org.junit.Assert.assertTrue;

/**
 * @author leo
 * @date 19/09/15 12:18
 */
public class PersonTest {

	private Person sut;

	@Test
	public void build_person() throws PersonException {
		String name = "John";
		String lastName = "Doe";
		buildPerson(name, lastName);
		assertTrue(sut != null);
	}

	@Test
	public void truncate_long_names() throws PersonException {
		String name = buildLongString(100);
		String lastName = "Doe";
		buildPerson(name, lastName);
		assertTrue(sut.getName().length() <= Person.MAX_LENGTH);
	}

	@Test
	public void truncate_long_last_names() throws PersonException {
		String name = "John";
		String lastName = buildLongString(100);
		buildPerson(name, lastName);
		assertTrue(sut.getName().length() <= Person.MAX_LENGTH);
	}

	@Test
	public void accept_null_name() throws PersonException {
		String name = null;
		String lastName = "Doe";
		buildPerson(name, lastName);
		assertTrue(sut.getName().equals(""));
	}

	@Test
	public void accept_null_last_name() throws PersonException {
		String name = "John";
		String lastName = null;
		buildPerson(name, lastName);
		assertTrue(sut.getLastName().equals(""));
	}

	private String buildLongString(final int stringLength) {
		String name = "a";
		for (int i = 0; i < stringLength; i++) {
			name = name.concat("a");
		}
		return name;
	}

	private void buildPerson(final String name, final String lastName) throws PersonException {
		sut = new Person();
		sut.setName(name);
		sut.setLastName(lastName);
	}

}