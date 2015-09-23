package org.systemexception.springmongorest.test;

import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.repository.DocumentRepository;
import org.systemexception.springmongorest.service.MongoDocumentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author leo
 * @date 23/09/15 17:53
 */
public class MongoDocumentServiceTest {

	private MongoDocumentService sut;
	private DocumentRepository documentRepository;
	private Document document;
	private List<Document> documentList = new ArrayList<>();

}