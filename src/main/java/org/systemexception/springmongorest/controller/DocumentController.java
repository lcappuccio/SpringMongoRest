package org.systemexception.springmongorest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.systemexception.springmongorest.constants.Endpoints;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.service.DocumentService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author leo
 * @date 23/09/15 19:45
 */
@EnableSwagger2
@RestController
@RequestMapping(value = Endpoints.DOCUMENT)
public class DocumentController {

	private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);
	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Document> create(@RequestParam("filename") String fileName,
	                                       @RequestParam("file") MultipartFile receivedFile)
			throws DocumentException, IOException {
		logger.info("Received CREATE: " + fileName);
		Document documentReceived = new Document();
		documentReceived.setFileName(fileName);
		documentReceived.setFileContents(receivedFile.getBytes());
		documentReceived.setFileSize(receivedFile.getSize());
		Document documentCreated = documentService.create(documentReceived);
		if (Arrays.equals(documentCreated.getFileContents(), documentReceived.getFileContents())) {
			return new ResponseEntity<>(documentCreated, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(documentReceived, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = Endpoints.ID, method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
		logger.info("Received DELETE: " + id);
		if (documentService.delete(id)) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Document>> findAll() {
		logger.info("Received GET all documents");
		List<Document> documentList = documentService.findAll();
		return new ResponseEntity<>(documentList, HttpStatus.OK);
	}

	@RequestMapping(value = Endpoints.ID, method = RequestMethod.GET)
	public ResponseEntity<Document> findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		Document document = documentService.findById(id).orElse(null);
		if (document != null) {
			return new ResponseEntity<>(document, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(document, HttpStatus.NOT_FOUND);
		}
	}
}
