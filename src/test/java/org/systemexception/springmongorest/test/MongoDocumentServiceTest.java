package org.systemexception.springmongorest.test;

import org.junit.Before;
import org.junit.Test;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.model.Person;
import org.systemexception.springmongorest.repository.DocumentRepository;
import org.systemexception.springmongorest.service.MongoDocumentService;
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
 * @date 23/09/15 17:53
 */
public class MongoDocumentServiceTest {

	private MongoDocumentService sut;
	private DocumentRepository documentRepository;
	private Document document;
	private List<Document> documentList = new ArrayList<>();

	@Before
	public void setUp() throws DocumentException {
		document = new Document();
		document.setFileName("fileName");
		document.setFileContents("fileContentsAreHere".getBytes());
		documentList.add(document);
		documentRepository = mock(DocumentRepository.class);
		when(documentRepository.save(document)).thenReturn(document);
		when(documentRepository.findAll()).thenReturn(documentList);
		when(documentRepository.findOne(document.getId())).thenReturn(Optional.of(document));
	}

	@Test
	public void create_document() {
		sut = new MongoDocumentService(documentRepository);
		Document newDocument = sut.create(document);

		assertTrue(newDocument.equals(document));
		verify(documentRepository).save(document);
	}
}