package org.systemexception.springmongorest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.systemexception.springmongorest.exception.DocumentException;

import java.util.Arrays;

/**
 * @author leo
 * @date 23/09/15 14:53
 */
public class Document {

	private final static Logger logger = LoggerFactory.getLogger(Document.class);
	public static final int MAX_SIZE_BYTES = 4000000, FILENAME_LENGTH = 255;

	@Id
	private String id;
	private String fileName;
	@JsonIgnore
	private byte[] fileContents;
	private long fileSize;

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

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
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

		if (fileSize != document.fileSize) return false;
		if (id != null ? !id.equals(document.id) : document.id != null) return false;
		if (fileName != null ? !fileName.equals(document.fileName) : document.fileName != null) return false;
		return Arrays.equals(fileContents, document.fileContents);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
		result = 31 * result + Arrays.hashCode(fileContents);
		result = 31 * result + (int) (fileSize ^ (fileSize >>> 32));
		return result;
	}
}
