package org.systemexception.springmongorest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.systemexception.springmongorest.constants.Endpoints;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.service.PersonService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/**
 * @author leo
 * @date 19/09/15 21:38
 */
@EnableSwagger2
@RestController
@RequestMapping(value = Endpoints.PERSON, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	private final PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType
			.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> create(@RequestBody @Valid Person person) {
		logger.info("Received CREATE: " + person.getName() + ", " + person.getLastName());
		Person personCreated = personService.create(person);
		return new ResponseEntity<>(personCreated, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@RequestBody @Valid Person person) {
		logger.info("Received DELETE: " + person.getName() + ", " + person.getLastName());
		personService.delete(person);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> findAll() {
		logger.info("Received GET all persons");
		List<Person> personList = personService.findAll();
		return new ResponseEntity<>(personList, HttpStatus.OK);
	}

	@RequestMapping(value = Endpoints.ID, method = RequestMethod.GET)
	public ResponseEntity<Person> findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		Person person = personService.findById(id).orElse(null);
		if (person != null) {
			return new ResponseEntity<>(person, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> update(@RequestBody @Valid Person person) {
		logger.info("Received UPDATE: " + person.getId() + ", " + person.getName() + ", " + person.getLastName());
		Person updatedPerson = personService.update(person);
		return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
	}
}
