package org.systemexception.springmongorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.services.PersonService;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 19/09/15 21:38
 */
@RestController
@RequestMapping("/api/person")
public class PersonController {

	private final PersonService personService;

	@Autowired
	PersonController(PersonService personService) {
		this.personService = personService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	Person create(Person person) {
		return personService.create(person);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	void delete(Person person) {
		personService.delete(person);
	}

	@RequestMapping(method = RequestMethod.GET)
	List<Person> findAll() {
		return personService.findAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	Optional<Person> findById(@PathVariable("id") String id) {
		return personService.findById(id);
	}

	@RequestMapping(method = RequestMethod.PUT)
	void update(Person person) {
		personService.update(person);
	}
}
