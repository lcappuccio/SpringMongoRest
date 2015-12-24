package org.systemexception.springmongorest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.systemexception.springmongorest.constants.StatusCodes;
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
@RequestMapping(value = "/api/person", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(basePath = "/api/person", value = "Person", description = "Person REST API")
public class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	private final PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType
			.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiOperation(value = "Create person", notes = "Adds a person to the database")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields have validation errors"),
			@ApiResponse(code = StatusCodes.CREATED, message = "Person created")})
	Person create(@RequestBody @Valid Person person) {
		logger.info("Received CREATE: " + person.getName() + ", " + person.getLastName());
		return personService.create(person);
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete person", notes = "Delete person from database")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields are with validation errors"),
			@ApiResponse(code = StatusCodes.OK, message = "Person deleted")})
	void delete(@RequestBody @Valid Person person) {
		logger.info("Received DELETE: " + person.getName() + ", " + person.getLastName());
		personService.delete(person);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List all persons", notes = "Produces the full person list in database")
	List<Person> findAll() {
		logger.info("Received GET all persons");
		return personService.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Find person by id", notes = "Use internal database id")
	Person findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		return personService.findById(id).orElse(null);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update person", notes = "Unknown behaviour if id does not exist")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields are with validation errors"),
			@ApiResponse(code = StatusCodes.OK, message = "Person updated")})
	Person update(@RequestBody @Valid Person person) {
		logger.info("Received UPDATE: " + person.getId() + ", " + person.getName() + ", " + person.getLastName());
		return personService.update(person);
	}
}
