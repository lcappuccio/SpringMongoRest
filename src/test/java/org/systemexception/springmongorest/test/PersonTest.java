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
		sut = new Person(name, lastName);
		assertTrue(sut != null);
	}

	@Test
	public void exception_on_long_names() throws PersonException {
		String name = buildLongString(100);
		String lastName = "Doe";
		sut = new Person(name, lastName);
		assertTrue(sut.getName().length() <= Person.MAX_LENGTH);
	}

	@Test
	public void exception_on_long_last_names() throws PersonException {
		String name = "John";
		String lastName = buildLongString(100);
		sut = new Person(name, lastName);
		assertTrue(sut.getName().length() <= Person.MAX_LENGTH);
	}

	@Test(expected = PersonException.class)
	public void throw_exception_on_null_name() throws PersonException {
		String name = null;
		String lastName = "Doe";
		sut = new Person(name, lastName);
	}

	@Test(expected = PersonException.class)
	public void throw_exception_on_null_last_name() throws PersonException {
		String name = "John";
		String lastName = null;
		sut = new Person(name, lastName);
	}

	private String buildLongString(final int stringLength) {
		String name = "a";
		for (int i = 0; i < stringLength; i++) {
			name = name.concat("a");
		}
		return name;
	}
}