package org.systemexception.springmongorest.test;

import org.junit.Test;
import org.systemexception.springmongorest.model.Person;

import static org.junit.Assert.assertTrue;

/**
 * @author leo
 * @date 19/09/15 12:18
 */
public class PersonTest {

	private Person sut;

	@Test
	public void build_person() {
		String name = "John";
		String lastName = "Doe";
		sut = new Person(name, lastName);
		assertTrue(sut != null);
	}

	@Test
	public void exception_on_long_names() {
		String name = buildLongString(100);
		String lastName = "Doe";
		sut = new Person(name, lastName);
		assertTrue(sut.getName().length() <= Person.MAX_LENGTH);
	}

	@Test
	public void exception_on_long_last_names() {
		String name = "John";
		String lastName = buildLongString(100);
		sut = new Person(name, lastName);
		assertTrue(sut.getName().length() <= Person.MAX_LENGTH);
	}

	private String buildLongString(final int stringLength) {
		String name = "a";
		for (int i = 0; i < stringLength; i++) {
			name = name.concat("a");
		}
		return name;
	}
}