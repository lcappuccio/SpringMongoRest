package org.systemexception.springmongorest.service;

import org.systemexception.springmongorest.model.Document;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 23/09/15 17:50
 */
public interface DocumentService {

	/**
	 * @param document
	 * @return
	 */
	Document create(Document document);

	/**
	 *
	 * @param document
	 * @return
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

	/**
	 * @param document
	 * @return
	 */
	Document update(Document document);
}
