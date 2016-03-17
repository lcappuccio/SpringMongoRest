package org.systemexception.springmongorest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.systemexception.springmongorest.constants.StatusCodes;
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
@RequestMapping(value = "/api/document")
@Api(basePath = "/api/document", value = "Document", description = "Documents REST API")
public class DocumentController {

	private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);
	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Create document", notes = "Adds a document to the database")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields have validation errors"),
			@ApiResponse(code = StatusCodes.CREATED, message = "Document created")})
	public ResponseEntity<HttpStatus> create(@RequestParam("filename") String fileName,
	                                         @RequestParam("file") MultipartFile receivedFile)
			throws DocumentException, IOException {
		logger.info("Received CREATE: " + fileName);
		Document documentReceived = new Document();
		documentReceived.setFileName(fileName);
		documentReceived.setFileContents(receivedFile.getBytes());
		documentReceived.setFileSize(receivedFile.getSize());
		Document documentCreated = documentService.create(documentReceived);
		if (Arrays.equals(documentCreated.getFileContents(), documentReceived.getFileContents())) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete document", notes = "Delete document from database")
	@ApiResponses(value = {@ApiResponse(code = StatusCodes.FOUND, message = "Document deleted"),
			@ApiResponse(code = StatusCodes.NOT_FOUND, message = "Document not deleted")})
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
		logger.info("Received DELETE: " + id);
		if (documentService.delete(id)) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "List all documents", notes = "Produces the full document list in database")
	public List<Document> findAll() {
		logger.info("Received GET all documents");
		return documentService.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Find document by id", notes = "Use database document id")
	public Document findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		return documentService.findById(id).orElse(null);
	}
}
