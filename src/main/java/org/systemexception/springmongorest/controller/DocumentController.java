package org.systemexception.springmongorest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.constants.StatusCodes;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;
import org.systemexception.springmongorest.service.DocumentService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author leo
 * @date 23/09/15 19:45
 */
@EnableSwagger2
@RestController
@RequestMapping(value = "/api/document")
@Api(basePath = "/api/receivedFile", value = "Document", description = "Document files REST API")
public class DocumentController {

	private final static Logger logger = LoggerImpl.getFor(DocumentController.class);
	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces =
			MediaType.TEXT_PLAIN_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiOperation(value = "Create document", notes = "Adds a receivedFile to the database")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields are with validation errors"),
			@ApiResponse(code = StatusCodes.CREATED, message = "Document created")})
	HttpStatus create(@RequestBody @Valid File receivedFile) throws DocumentException, IOException {
		logger.info("Received CREATE: " + receivedFile.getName());
		Path filePath = Paths.get(receivedFile.getPath());
		Document documentReceived = new Document();
		documentReceived.setFileName(receivedFile.getName());
		documentReceived.setFileContents(Files.readAllBytes(filePath));
		Document documentCreated = documentService.create(documentReceived);
		if (documentCreated.getFileContents().equals(documentReceived.getFileContents())) {
			return HttpStatus.CREATED;
		} else {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete receivedFile", notes = "Delete receivedFile from database")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields are with validation errors"),
			@ApiResponse(code = StatusCodes.OK, message = "Document deleted")})
	void delete(@RequestBody @Valid Document document) {
		logger.info("Received DELETE: " + document.getFileName());
		documentService.delete(document);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List all documents", notes = "Produces the full receivedFile list in database")
	List<Document> findAll() {
		logger.info("Received GET all documents");
		return documentService.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ApiOperation(value = "Find receivedFile by id", notes = "Use internal database id")
	Document findById(@PathVariable("id") String id) {
		logger.info("Received GET id: " + id);
		return documentService.findById(id).orElse(null);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces =
			MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ApiOperation(value = "Update receivedFile", notes = "Unknown behaviour if id does not exist")
	@ApiResponses(value = {
			@ApiResponse(code = StatusCodes.BAD_REQUEST, message = "Fields are with validation errors"),
			@ApiResponse(code = StatusCodes.OK, message = "Person updated")})
	Document update(@RequestBody @Valid Document document) {
		logger.info("Received UPDATE: " + document.getId() + ", " + document.getFileName());
		return documentService.update(document);
	}
}
