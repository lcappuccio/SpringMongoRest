package org.systemexception.springmongorest.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.systemexception.springmongorest.Application;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.repository.PersonRepository;
import org.systemexception.springmongorest.service.MongoPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author leo
 * @date 22/09/15 10:37
 */
public class MongoPersonServiceTest {

	private MongoPersonService sut;
	private PersonRepository personRepository;
	private Person person;
	private List<Person> personList = new ArrayList<>();

	@Before
	public void setUp() {
		person = new Person();
		person.setName("Name");
		person.setLastName("LastName");
		personList.add(person);
		personRepository = mock(PersonRepository.class);
		when(personRepository.save(person)).thenReturn(person);
		when(personRepository.findAll()).thenReturn(personList);
		when(personRepository.findOne(person.getId())).thenReturn(Optional.of(person));
	}

	@Test
	public void create_person() {
		sut = new MongoPersonService(personRepository);
		sut.create(person);

		verify(personRepository).save(person);
	}

	@Test
	public void delete_person() {
		sut = new MongoPersonService(personRepository);
		sut.delete(person);

		verify(personRepository).delete(person);
	}

	@Test
	public void find_all_persons() {
		sut = new MongoPersonService(personRepository);
		List<Person> persons = sut.findAll();

		assertTrue(persons.size() == personList.size());
		verify(personRepository).findAll();
	}

	@Test
	public void find_one_person() {
		sut = new MongoPersonService(personRepository);
		Optional<Person> foundPerson = sut.findById(person.getId());

		assertTrue(foundPerson.get().getId() == person.getId());
		verify(personRepository).findOne(person.getId());
	}
}
