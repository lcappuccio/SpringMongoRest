package org.systemexception.springmongorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.service.PersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 19/09/15 21:38
 */
@RestController
@RequestMapping("/api/person")
public class PersonController {

	private static final Logger logger = LoggerImpl.getFor(PersonController.class);
	private final PersonService personService;

	@Autowired
	PersonController(PersonService personService) {
		this.personService = personService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	Person create(@RequestBody @Valid Person person) {
		logger.info("Received CREATE: " + person.getName() + ", " + person.getLastName());
		return personService.create(person);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	void delete(Person person) {
		logger.info("Received DELETE: " + person.getName() + ", " + person.getLastName());
		personService.delete(person);
	}

	@RequestMapping(method = RequestMethod.GET)
	List<Person> findAll() {
		logger.info("Received GET all persons");
		return personService.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	Optional<Person> findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		return personService.findById(id);
	}

	@RequestMapping(method = RequestMethod.PUT)
	void update(Person person) {
		logger.info("Received UPDATE: " + person.getId() + ", " + person.getName() + ", " + person.getLastName());
		personService.update(person);
	}
}
