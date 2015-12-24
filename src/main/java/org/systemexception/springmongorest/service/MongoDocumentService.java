package org.systemexception.springmongorest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.repository.DocumentRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 23/09/15 17:47
 */
@Component
@Service
public class MongoDocumentService implements DocumentService {

	private static final Logger logger = LoggerFactory.getLogger(MongoDocumentService.class);
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
	public Boolean delete(String id) {
		Document document = documentRepository.findOne(id).get();
		if (!document.getId().isEmpty()) {
			documentRepository.delete(document);
			return true;
		} else {
			return false;
		}
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
}
