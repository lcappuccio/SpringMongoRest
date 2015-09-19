package org.systemexception.springmongorest.repository;

import org.springframework.data.repository.Repository;
import org.systemexception.springmongorest.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 19/09/15 20:01
 */
public interface PersonRepository extends Repository<Person, String> {

	/**
	 * @param person
	 */
	Person save(Person person);

	/**
	 * @param person
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
	Optional<Person> findOne(String id);

}
