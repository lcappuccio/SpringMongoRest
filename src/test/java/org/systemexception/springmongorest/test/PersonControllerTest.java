package org.systemexception.springmongorest.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.systemexception.springmongorest.Application;
import org.systemexception.springmongorest.controller.PersonController;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.service.MongoPersonService;
import org.systemexception.springmongorest.service.PersonService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author leo
 * @date 21/09/15 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@TestPropertySource(locations = "classpath:application.properties")
public class PersonControllerTest {

	@MockBean
	private PersonService personService;
	private final Person person = new Person();
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
		sut = MockMvcBuilders.standaloneSetup(personController).build();
	}

	@Test
	public void find_all_persons() throws Exception {
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status()
				.is(HttpStatus.OK.value()));
		verify(personService).findAll();
	}

	@Test
	public void find_one_person() throws Exception {
		personService.create(person);
		when(personService.findById(any())).thenReturn(Optional.of(person));
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT + person.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(HttpStatus.FOUND.value()));
		verify(personService).findById(any());
	}

	@Test
	public void dont_find_one_person() throws Exception {
		personService.create(person);
		when(personService.findById(any())).thenReturn(Optional.empty());
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT + person.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
		verify(personService).findById(any());
	}

	@Test
	public void create_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.post(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				(personJson(person).getBytes())).andExpect(status().is(HttpStatus.CREATED.value()));
		verify(personService).create(person);
	}

	@Test
	public void refuse_create_bad_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.post(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				("badly_formatted_data".getBytes())).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
		verify(personService, never()).create(person);
	}

	@Test
	public void delete_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.delete(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				(personJson(person).getBytes())).andExpect(status().is(HttpStatus.OK.value()));
		verify(personService).delete(person);
	}

	@Test
	public void update_person() throws Exception {
		sut.perform(MockMvcRequestBuilders.put(ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE).content
				(personJson(person).getBytes())).andExpect(status().is(HttpStatus.OK.value()));
		verify(personService).update(person);
	}

	private String personJson(Person person) {
		return "{\"name\":" + "\"" + person.getName() + "\"," + "\"lastName\":" + "\"" + person.getLastName() + "\"}";
	}

}