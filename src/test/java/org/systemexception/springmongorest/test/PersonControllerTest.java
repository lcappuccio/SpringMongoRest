package org.systemexception.springmongorest.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.systemexception.springmongorest.Application;
import org.systemexception.springmongorest.controller.PersonController;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.service.MongoPersonService;
import org.systemexception.springmongorest.service.PersonService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author leo
 * @date 21/09/15 14:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class PersonControllerTest {

	private PersonService personService;
	private Person person = new Person();
	@InjectMocks
	@Autowired
	private PersonController personController;
	private MockMvc sut;
	private final static String ENDPOINT = "/api/person/";

	@Before
	public void setUp() {
		person.setName("PersonName");
		person.setLastName("PersonLastName");
		personService = mock(MongoPersonService.class);
		when(personService.findAll()).thenReturn(null);
		personController = new PersonController(personService);
		MockitoAnnotations.initMocks(this);
		sut = MockMvcBuilders.standaloneSetup(personController).build();
	}

	@Test
	public void find_all_persons() throws Exception {
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status()
				.is(200));
		verify(personService).findAll();
	}

	@Test
	public void find_one_person() throws Exception {
		String personId = "123";
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT + personId).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect
				(status().is(200));
		verify(personService).findById(personId);
	}

	@Test
	public void create_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.post(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				(personJson(person).getBytes())).andExpect(status().is(201));
		verify(personService).create(person);
	}

	@Test
	public void delete_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.delete(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				(personJson(person).getBytes())).andExpect(status().is(200));
		verify(personService).delete(person);
	}

	@Test
	public void update_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.put(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				(personJson(person).getBytes())).andExpect(status().is(200));
		verify(personService).update(person);
	}

	private String personJson(Person person) {
		return "{\"name\":" + "\"" + person.getName() + "\"," +
				"\"lastName\":"+ "\"" + person.getLastName() + "\"}";
	}

}