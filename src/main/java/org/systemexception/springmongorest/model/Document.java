package org.systemexception.springmongorest.model;

import org.springframework.data.annotation.Id;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.exception.DocumentException;

/**
 * @author leo
 * @date 23/09/15 14:53
 */
public class Document {

	private final static Logger logger = LoggerImpl.getFor(Document.class);
	public static final int MAX_SIZE_BYTES = 4000000, FILENAME_LENGTH = 255;

	@Id
	private String id;
	private String fileName;
	private byte[] fileContents;

	public Document() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) throws DocumentException {
		if (fileName.length() > FILENAME_LENGTH) {
			DocumentException documentException = new DocumentException("File name larger than " +
					FILENAME_LENGTH + " bytes");
			logger.error("File name too large", documentException);
			throw documentException;
		} else {
			this.fileName = fileName;
		}
	}

	public int getFileSize() {
		return fileContents.length;
	}

	public byte[] getFileContents() {
		return fileContents;
	}

	public void setFileContents(byte[] fileContents) throws DocumentException {
		if (fileContents.length > MAX_SIZE_BYTES) {
			DocumentException documentException = new DocumentException("File larger than " + MAX_SIZE_BYTES
					+ " bytes");
			logger.error("File too large", documentException);
			throw documentException;
		} else {
			this.fileContents = fileContents;
		}
	}
}
