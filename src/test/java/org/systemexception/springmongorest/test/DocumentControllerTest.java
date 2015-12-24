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
import org.systemexception.springmongorest.constants.StatusCodes;
import org.systemexception.springmongorest.controller.DocumentController;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.service.DocumentService;
import org.systemexception.springmongorest.service.MongoDocumentService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author leo
 * @date 23/09/15 19:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class DocumentControllerTest {

	private DocumentService documentService;
	private Document document = new Document();
	@InjectMocks
	@Autowired
	private DocumentController documentController;
	private MockMvc sut;
	private final static String ENDPOINT = "/api/document/";

	@Before
	public void setUp() throws DocumentException {
		document.setFileName("FileName");
		document.setFileContents("FileContentsHere".getBytes());
		documentService = mock(MongoDocumentService.class);
		when(documentService.findAll()).thenReturn(null);
		documentController = new DocumentController(documentService);
		MockitoAnnotations.initMocks(this);
		sut = MockMvcBuilders.standaloneSetup(documentController).build();
	}

	@Test
	public void find_all_documents() throws Exception {
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT).content(document.getFileContents())).andExpect(status()
				.is(StatusCodes.OK));
		verify(documentService).findAll();
	}

	@Test
	public void find_one_document() throws Exception {
		documentService.create(document);
		when(documentService.findById(any())).thenReturn(Optional.of(document));
		sut.perform(MockMvcRequestBuilders.get(ENDPOINT + document.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(StatusCodes.OK));
		verify(documentService).findById(any());
	}

}