package com.knossys.rnd.data.db;

import java.io.File;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * @author vvelsen
 */
public class ServerFileManager extends FileManager {

	private static Logger M_log = Logger.getLogger(ServerFileManager.class.getName());

	private String userId = "";
	private String basePath = "";

	/**
	 * 
	 */
	public ServerFileManager() {
		setBasePath(System.getProperty("jboss.server.base.dir") + "/deployments/datasets/");

		File directory = new File(getBasePath());
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;

		if (this.userId == null) {
			M_log.info("Error, trying to set userId to NULL!");
			return;
		}

		File directory = new File(getBasePath() + "/" + userId);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File d1 = new File(getBasePath() + "/" + userId + "/extracts");
		if (!d1.exists()) {
			d1.mkdirs();
		}

		File d2 = new File(getBasePath() + "/" + userId + "/exports");
		if (!d2.exists()) {
			d2.mkdirs();
		}
	}

	/**
	 * @return
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	/**
	 * 
	 * @param string
	 */
	public String mapFile(String aFilename) {
		M_log.info("createFile (" + aFilename + ")");

		if (this.userId == null) {
			M_log.info("Error, trying to set userId to NULL!");
			return (null);
		}

		return (getBasePath() + "/" + userId + "/" + aFilename);
	}
	
	/**
	 * 
	 */
	public String listDirectory(String aUser, String aPath, String aRef) {

		this.userId = aUser;

		if (this.userId == null) {
			M_log.info("Error, trying to set userId to NULL!");
			return ("[]");
		}

		File directory = new File(getBasePath() + "/" + userId);
		if (!directory.exists()) {
			return ("[]");
		}

		File d1 = new File(getBasePath() + "/" + userId + "/" + aPath + "/");
		if (aPath.equalsIgnoreCase("/") == true) {
			d1 = new File(getBasePath() + "/" + userId + "/");
		}

		if (!d1.exists()) {
			return ("[]");
		}

		M_log.info("Generating file listing for: " + d1.getPath());

		File[] listOfFiles = d1.listFiles();

		String result = "[]";

		try {
			JsonArrayBuilder json = Json.createArrayBuilder();

			for (int i = 0; i < listOfFiles.length; i++) {
				JsonObjectBuilder aFile = Json.createObjectBuilder();

				File testFile = listOfFiles[i];

				if (testFile.getName().endsWith(".part") == false) {
					aFile.add("id", testFile.getName()); // change to persistent uuid
					aFile.add("ref", aRef);
					aFile.add("path", aPath);
					aFile.add("userid", userId);
					aFile.add("name", testFile.getName());
					aFile.add("size", testFile.length());
					aFile.add("modified", testFile.lastModified());

					if (testFile.isFile()) {
						aFile.add("type", "file");
					} else if (testFile.isDirectory()) {
						aFile.add("type", "directory");
					}

					json.add(aFile);
				}
			}
			result = json.build().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (result);
	}

	/**
	 * 
	 * @param string
	 */
	public String createFile(String aFilename) {
		M_log.info("createFile (" + aFilename + ")");

		if (this.userId == null) {
			M_log.info("Error, trying to set userId to NULL!");
			return (null);
		}

		File directory = new File(getBasePath() + "/" + userId);
		if (!directory.exists()) {
			return (null);
		}

		File testFile = new File(getBasePath() + "/" + userId + "/" + aFilename);
		if (testFile.exists() == true) {
			return (null);
		}

		if (saveContents(getBasePath() + "/" + userId + "/" + aFilename, "")) {
			// TBD
		}

		return (getBasePath() + "/" + userId + "/" + aFilename);
	}
	
}
