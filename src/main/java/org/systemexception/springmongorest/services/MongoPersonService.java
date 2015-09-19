package org.systemexception.springmongorest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.repository.PersonRepository;

import java.util.List;

/**
 * @author leo
 * @date 19/09/15 20:57
 */
@Service
public class MongoPersonService implements PersonService {

	private final PersonRepository personRepository;

	@Autowired
	MongoPersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	/**
	 * @param person
	 * @return
	 */
	@Override
	public Person create(Person person) {
		return personRepository.save(person);
	}

	/**
	 * @param person
	 * @return
	 */
	@Override
	public void delete(Person person) {
		personRepository.delete(person);
	}

	/**
	 * @return
	 */
	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public Person findById(String id) {
		return personRepository.findOne(id);
	}

	/**
	 * @param person
	 * @return
	 */
	@Override
	public void update(Person person) {
		Person foundPerson = personRepository.findOne(person.getId());
		foundPerson.setName(person.getName());
		foundPerson.setLastName(person.getLastName());
		personRepository.save(foundPerson);
	}
}
