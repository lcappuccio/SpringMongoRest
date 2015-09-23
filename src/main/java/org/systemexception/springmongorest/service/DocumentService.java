package org.systemexception.springmongorest.service;

import org.springframework.stereotype.Service;
import org.systemexception.springmongorest.model.Document;

import java.util.List;
import java.util.Optional;

/**
 * @author leo
 * @date 23/09/15 17:50
 */
@Service
public interface DocumentService {

	/**
	 * @param document
	 * @return
	 */
	Document create(Document document);

	/**
	 *
	 * @param id
	 * @return
	 */
	void delete(String id);

	/**
	 * @return
	 */
	List<List<String>> findAll();

	/**
	 * @param id
	 * @return
	 */
	Optional<Document> findById(String id);

}
