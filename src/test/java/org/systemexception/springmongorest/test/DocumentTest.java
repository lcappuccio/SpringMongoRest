package org.systemexception.springmongorest.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.systemexception.springmongorest.exception.DocumentException;
import org.systemexception.springmongorest.model.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author leo
 * @date 23/09/15 14:58
 */
public class DocumentTest {

	private Document sut;
	private static byte[] bigFile, regularFile;

	@BeforeClass
	public static void setUp() throws IOException {
		// Create large file
		FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + File.separator +
				"target" + File.separator + "large_file.bin");
		bigFile = new byte[2 * Document.MAX_SIZE_BYTES];
		fileOutputStream.write(bigFile);
		fileOutputStream.flush();
		fileOutputStream.close();
		// Create regular file
		fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + File.separator +
				"target" + File.separator + "regular_file.bin");
		regularFile = new byte[Document.MAX_SIZE_BYTES];
		fileOutputStream.write(regularFile);
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	@Test(expected = DocumentException.class)
	public void refuse_file_larger_than_4_mb() throws DocumentException {
		sut = new Document();
		sut.setFileContents(bigFile);
	}

	@Test
	public void accept_file_smaller_than_4_mb() throws DocumentException {
		sut = new Document();
		sut.setFileContents(regularFile);
		assertTrue(sut.getFileContents().length == regularFile.length);
	}

	@Test(expected = DocumentException.class)
	public void refuse_long_filenames() throws DocumentException {
		String fileName = "";
		for (int i = 0; i < 500; i++) {
			fileName = fileName.concat("x");
		}
		sut = new Document();
		sut.setFileName(fileName);
	}
}