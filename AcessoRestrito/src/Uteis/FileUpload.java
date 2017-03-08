package Uteis;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class FileUpload {

	private final long limit_max_size = 999910240000L;
	private final String limit_type_file = "gif|jpg|png|jpeg|pdf|doc|docx|txt";
	private String path_to = "";

	public FileUpload() {

	}

	public String processUpload(Part fileUpload) {
		String fileSaveData = null;

		try {

			if (fileUpload.getSize() > 0) {
				String submittedFileName = getFilename(fileUpload);
				if (checkFileType(submittedFileName)) {
					if (fileUpload.getSize() > this.limit_max_size) {

					} else {
						String currentFileName = submittedFileName;
						String extension = currentFileName.substring(currentFileName.lastIndexOf("."),
								currentFileName.length());
						long nameRadom = Calendar.getInstance().getTimeInMillis();
						String newfilename = currentFileName;

						fileSaveData = newfilename;
						String fileSavePath = path_to;

						try {
							byte[] fileContent = new byte[(int) fileUpload.getSize()];
							InputStream in = fileUpload.getInputStream();
							in.read(fileContent);

							File fileToCreate = new File(fileSavePath, newfilename);

							File folder = new File(fileSavePath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(fileContent);
							fileOutStream.flush();
							fileOutStream.close();
							fileSaveData = newfilename;
						} catch (IOException e) {
							fileSaveData = null;
						}

					}

				} else {
					fileSaveData = null;

				}

			}
		} catch (Exception ex) {
			fileSaveData = null;

		}
		return fileSaveData;
	}

	public String getPath_to() {
		return path_to;
	}

	public void setPath_to(String path_to) {
		this.path_to = path_to;
	}

	private String getFilename(Part part) {

		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");

				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);

			}
		}
		return null;
	}

	private boolean checkFileType(String fileName) {
		if (fileName.length() > 0) {
			String[] parts = fileName.split("\\.");
			if (parts.length > 0) {
				String extention = parts[parts.length - 1];
				return this.limit_type_file.contains(extention);
			}
		}
		return false;
	}
}