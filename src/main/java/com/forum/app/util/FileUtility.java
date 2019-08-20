package com.forum.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.forum.app.constant.ForumError;
import com.forum.app.exception.ModifyException;

/**
 * Utility class to perform various file related operations.
 * 
 * @author Saurabh Mhatre
 */
public class FileUtility {
	public static final int BUFFER_LENGTH = 1024;

	public static String uploadLocation;

	public static String contextPath;

    /**
     * This describes the different types of values which can be assigned
     * to the mode parameter passed along with the avatar add/update API.
     *
     * @author Saurabh Mhatre
     */
	public enum Mode {
		ADD("ADD"),

		UPDATE("UPDATE");

		private String value;

		private Mode(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value.toLowerCase();
		}

	}

	/**
	 * This will upload a file to path specified by uploadLocation which is fetched
	 * from the configuration file.
	 * 
	 * @param inputStream		the input stream of the file to be uploaded.
	 * @param mode				this determines if a file is being uploaded for the first time.
	 * @param fileName			the name of the file(avatar) to be upload.
	 * @param previousFileName	the name of the file(avatar) already being used.
	 *
	 * @throws ModifyException	the wrapped exception thrown during processing.
	 *
	 * @return A String instance of the contextual location at which the file is uploaded.
	 */
	public static String uploadFile(InputStream inputStream, String mode, String fileName, String previousFileName)
			throws ModifyException {
		Integer read = null;
		OutputStream outFile = null;
		String fileLocation = uploadLocation + fileName;
		String contextLocation = contextPath + fileName;
		File uploadFile = new File(fileLocation);
		if (Mode.valueOf(mode.toUpperCase()).equals(Mode.UPDATE)) {
			String previousFileLocation = uploadLocation + previousFileName;
			File previousFile = new File(previousFileLocation);
			if (previousFile.exists()) {
				previousFile.delete();
			}
		}
		final byte[] buffer = new byte[BUFFER_LENGTH];
		try {
			outFile = new FileOutputStream(uploadFile);
			while ((read = inputStream.read(buffer)) != -1) {
				outFile.write(buffer, 0, read);
			}
			return contextLocation;
		} catch (IOException ex) {
			throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
		} finally {
			try {
				if (outFile != null) {
					outFile.flush();
					outFile.close();
				}
			} catch (IOException ex) {
				if (uploadFile.exists()) {
					uploadFile.delete();
				}
				throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
			}
		}
	}

	/**
	 * This deletes the file corresponding to the passed fileName.
	 * 
	 * @param fileName the name of the file to be deleted
	 */
	public static void deleteFile(String fileName) {
		String fileLocation = uploadLocation + fileName;
		File file = new File(fileLocation);
		if (file.exists()) {
			file.delete();
		}
	}

}
