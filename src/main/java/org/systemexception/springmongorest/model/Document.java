package org.systemexception.springmongorest.model;

import org.springframework.data.annotation.Id;
import org.systemexception.logger.api.Logger;
import org.systemexception.logger.impl.LoggerImpl;
import org.systemexception.springmongorest.exception.DocumentException;

import java.util.Arrays;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Document document = (Document) o;

		if (id != null ? !id.equals(document.id) : document.id != null) return false;
		if (fileName != null ? !fileName.equals(document.fileName) : document.fileName != null) return false;
		return Arrays.equals(fileContents, document.fileContents);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
		result = 31 * result + (fileContents != null ? Arrays.hashCode(fileContents) : 0);
		return result;
	}
}
