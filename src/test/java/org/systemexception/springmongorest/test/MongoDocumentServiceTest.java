package org.systemexception.springmongorest.test;

import org.junit.Before;
import org.junit.Test;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.repository.DocumentRepository;
import org.systemexception.springmongorest.service.MongoDocumentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author leo
 * @date 23/09/15 17:53
 */
public class MongoDocumentServiceTest {

	private MongoDocumentService sut;
	private DocumentRepository documentRepository;
	private Document document;
	private List<Document> documentList = new ArrayList<>();
	private final String nonExistingId = "NON_EXISTING_ID";

	@Before
	public void setUp() throws DocumentException {
		document = new Document();
		document.setId("123");
		document.setFileName("fileName");
		document.setFileContents("fileContentsAreHere".getBytes());
		documentList.add(document);
		documentRepository = mock(DocumentRepository.class);
		when(documentRepository.save(document)).thenReturn(document);
		when(documentRepository.findAll()).thenReturn(documentList);
		when(documentRepository.findOne(document.getId())).thenReturn(Optional.of(document));
		when(documentRepository.findOne(nonExistingId)).thenReturn(Optional.empty());
	}

	@Test
	public void create_document() {
		sut = new MongoDocumentService(documentRepository);
		Document newDocument = sut.create(document);

		verify(documentRepository).save(document);
		assertTrue(newDocument.equals(document));
	}

	@Test
	public void delete_document() {
		sut = new MongoDocumentService(documentRepository);

		assertTrue(sut.delete(document.getId()));
		verify(documentRepository).findOne(document.getId());
		verify(documentRepository).delete(document);
	}

	@Test
	public void delete_nonexisting_document() {
		sut = new MongoDocumentService(documentRepository);

		assertFalse(sut.delete(nonExistingId));
		verify(documentRepository).findOne(nonExistingId);
	}

	@Test
	public void find_all_documents() {
		sut = new MongoDocumentService(documentRepository);
		List<Document> documents = sut.findAll();

		assertTrue(documents.size() == documentList.size());
		verify(documentRepository).findAll();
	}

	@Test
	public void find_one_document() {
		sut = new MongoDocumentService(documentRepository);
		Optional<Document> foundDocument = sut.findById(document.getId());

		assertTrue(Objects.equals(foundDocument.get(), document));
		assertTrue(Objects.equals(foundDocument.get().hashCode(), document.hashCode()));
		verify(documentRepository).findOne(document.getId());
	}

}