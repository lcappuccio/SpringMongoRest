package org.systemexception.springmongorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.repository.PersonRepository;
import org.systemexception.springmongorest.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 19/09/15 20:57
 */
@Component
@Service
public class MongoPersonService implements PersonService {

	private static final Logger logger = LoggerImpl.getFor(MongoPersonService.class);
	private final PersonRepository personRepository;

	@Autowired
	public MongoPersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	/**
	 * @param person
	 * @return
	 */
	@Override
	public Person create(Person person) {
		logger.info("Save person: " + person.getName() + ", " + person.getLastName());
		return personRepository.save(person);
	}

	/**
	 * @param person
	 * @return
	 */
	@Override
	public void delete(Person person) {
		logger.info("Delete person: " + person.getName() + ", " + person.getLastName());
		personRepository.delete(person);
	}

	/**
	 * @return
	 */
	@Override
	public List<Person> findAll() {
		List<Person> persons = personRepository.findAll();
		logger.info("Listing " + persons.size() + " persons");
		return persons;
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public Optional<Person> findById(String id) {
		logger.info("Finding id: " + id);
		return personRepository.findOne(id);
	}

	/**
	 * @param person
	 * @return
	 */
	@Override
	public Person update(Person person) {
		Person foundPerson = personRepository.findOne(person.getId()).get();
		logger.info("Update id: " + person.getId());
		foundPerson.setName(person.getName());
		foundPerson.setLastName(person.getLastName());
		personRepository.save(foundPerson);
		return foundPerson;
	}
}
