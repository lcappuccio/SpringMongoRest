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

	void add(Person person);

	void delete(Person person);

	List<Person> findAll();

	Optional<Person> findById(String id);

}
