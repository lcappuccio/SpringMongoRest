package org.systemexception.springmongorest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.constants.StatusCodes;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.service.DocumentService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.List;

/**
 * @author leo
 * @date 23/09/15 19:45
 */
@EnableSwagger2
@RestController
@RequestMapping(value = "/api/document")
@Api(basePath = "/api/document", value = "Document", description = "Document files REST API")
public class DocumentController {

	private final static Logger logger = LoggerImpl.getFor(DocumentController.class);
	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiOperation(value = "Create document", notes = "Adds a document to the database")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields have validation errors"),
			@ApiResponse(code = StatusCodes.CREATED, message = "Document created")})
	HttpStatus create(@RequestParam("filename") String fileName, @RequestParam("file") MultipartFile receivedFile)
			throws DocumentException, IOException {
		logger.info("Received CREATE: " + fileName);
		Document documentReceived = new Document();
		documentReceived.setFileName(fileName);
		documentReceived.setFileContents(receivedFile.getBytes());
		Document documentCreated = documentService.create(documentReceived);
		if (documentCreated.getFileContents().equals(documentReceived.getFileContents())) {
			return HttpStatus.CREATED;
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete document", notes = "Delete document from database")
	@ApiResponses(value = {@ApiResponse(code = StatusCodes.OK, message = "Document deleted")})
	void delete(@PathVariable("id") String id) {
		logger.info("Received DELETE: " + id);
		documentService.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "List all documents", notes = "Produces the full document list in database")
	List<List<String>> findAll() {
		logger.info("Received GET all documents");
		return documentService.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Find document by id", notes = "Use database document id")
	Document findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		return documentService.findById(id).orElse(null);
	}
}
