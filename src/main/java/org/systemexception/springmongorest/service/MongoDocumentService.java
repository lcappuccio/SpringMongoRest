package org.systemexception.springmongorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.repository.DocumentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 23/09/15 17:47
 */
@Component
@Service
public class MongoDocumentService implements DocumentService {

	private static final Logger logger = LoggerImpl.getFor(MongoDocumentService.class);
	private final DocumentRepository documentRepository;

	@Autowired
	public MongoDocumentService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	@Override
	public Document create(Document document) {
		return documentRepository.save(document);
	}

	@Override
	public void delete(String id) {
		documentRepository.delete(documentRepository.findOne(id).get());
	}

	@Override
	public List<List<String>> findAll() {
		List<List<String>> documentList = new ArrayList<>();
		List<Document> documents = documentRepository.findAll();
		for (Document document: documents) {
			List<String> documentElement = new ArrayList<>();
			documentElement.add(document.getId());
			documentElement.add(document.getFileName());
			documentList.add(documentElement);
		}
		return documentList;
	}

	@Override
	public Optional<Document> findById(String id) {
		logger.info("Finding id: " + id);
		return documentRepository.findOne(id);
	}
}
