package org.systemexception.springmongorest.repository;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.systemexception.springmongorest.model.Document;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 23/09/15 17:19
 */
@Component
public interface DocumentRepository extends Repository<Document, String> {

	/**
	 * @param document
	 */
	Document save(Document document);

	/**
	 * @param document
	 */
	void delete(Document document);

	/**
	 * @return
	 */
	List<Document> findAll();

	/**
	 * @param id
	 * @return
	 */
	Optional<Document> findById(String id);
}
