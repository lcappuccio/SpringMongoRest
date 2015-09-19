package org.systemexception.springmongorest.model;

import org.springframework.data.annotation.Id;

/**
 * @author leo
 * @date 19/09/15 12:14
 */
public class Person {

	private static final int MAX_LENGTH = 100;

	@Id
	private String id;
	private final String name, lastName;

	public Person(final String name, final String lastName) {
		this.name = name;
		this.lastName = lastName;
	}
}
