package org.systemexception.springmongorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.repository.DocumentRepository;

import java.util.List;
import java.util.Optional;

/**
 *  @author leo
 * @date 23/09/15 17:47
 */
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
	public void delete(Document document) {
		documentRepository.delete(document);
	}

	@Override
	public List<Document> findAll() {
		return documentRepository.findAll();
	}

	@Override
	public Optional<Document> findById(String id) {
		logger.info("Finding id: " + id);
		return documentRepository.findOne(id);
	}

	@Override
	public Document update(Document document) {
		return null;
	}
}
