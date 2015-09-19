package org.systemexception.springmongorest.services;

import org.systemexception.springmongorest.model.Person;

import java.util.List;

/**
 * @author leo
 * @date 19/09/15 20:03
 */
public interface PersonService {

	/**
	 * @param person
	 * @return
	 */
	Person create(Person person);

	/**
	 *
	 * @param person
	 * @return
	 */
	void delete(Person person);

	/**
	 * @return
	 */
	List<Person> findAll();

	/**
	 * @param id
	 * @return
	 */
	Person findById(String id);

	/**
	 * @param person
	 * @return
	 */
	void update(Person person);
}
