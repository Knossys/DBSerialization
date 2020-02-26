package com.knossys.rnd.data.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * @author vvelsen
 */
public class FileManager {
	
	private static Logger M_log = Logger.getLogger(FileManager.class.getName());
	
	private Writer streamOut = null;
	
	/**
	 *
	 */	
	public Boolean makeDirectory (String aDirectory) {
    File directory = new File(aDirectory);
    
    if (!directory.exists()){
	    if (directory.mkdirs()==false) {
	    	return (false);
	    }
	    // If you require it to make the entire directory path including parents,
	    // use directory.mkdirs(); here instead.
    }
    
		return true;
	}
	
	/**
	 *
	 */
	public boolean saveContents(String aFileURI, String aContents) {
		M_log.info("saveContents (" + aFileURI + ")");
		// M_log.info("contents "+aContents);

		File aFile = new File(aFileURI);
		Writer output = null;

		// if (checkCreate (aFileURI)==false)
		// return (false);

		try {
			output = new BufferedWriter(new FileWriter(aFile));
		} catch (IOException e) {
			M_log.info("Exception: IOException while opening output file (" + aFileURI + "): " + e.getMessage());
			e.printStackTrace();
			return (false);
		}

		try {
			// FileWriter always assumes default encoding is OK!
			output.write(aContents);
		} catch (IOException e) {
			M_log.info("Exception: IOException while writing contents to disk (" + aFileURI + "): " + e.getMessage());

			try {
				output.close();
			} catch (IOException closeException) {
				M_log.info("Exception: closeException while closing file writer (" + aFileURI + "): " + e.getMessage());
				return (false);
			}

			return (false);
		}

		try {
			output.flush();
		} catch (IOException e) {
			M_log.info("Exception: IOException while flushing contents to disk (" + aFileURI + "): " + e.getMessage());
		}

		try {
			output.close();
		} catch (IOException e) {
			M_log.info("Exception: IOException closing output file (" + aFileURI + "): " + e.getMessage());
		}

		return (true);
	}

	/**
	 * @param string
	 * @param string2
	 */
	public Boolean rename(String aFileFrom, String aFileTo) {
		M_log.info("rename (" + aFileFrom + "," + aFileTo + ")");

		File original = new File(aFileFrom);

		if (original.exists() == false) {
			M_log.info("Unable to rename file, source does not exist: " + aFileFrom);
			return (false);
		}

		File target = new File(aFileTo);

		if (target.exists() == true) {
			M_log.info("Unable to rename file, target already exists: " + aFileTo);
			return (false);
		}

		if (original.renameTo(target) == false) {
			M_log.info("Unable to rename file!");
			return (false);
		}

		M_log.info("File renamed successfully");

		return (true);
	}

	/**
	 *
	 */
	public boolean isStreamOpen() {
		if (streamOut != null)
			return (true);

		return (false);
	}

	/**
	 *
	 */
	public boolean openStream(String aFileURI) {
		M_log.info("openStream (" + aFileURI + ")");

		if (streamOut != null) {
			M_log.info("Stream already open");
			return (false);
		}

		File aFile = new File(aFileURI);

		if (aFile.exists() == false) {
			M_log.info("File does not exist yet, creating new empty file ...");
			try {
				aFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				return (false);
			}
		}

		if (!aFile.exists()) {
			M_log.info("File does not exist: " + aFile);
			return (false);
		}

		if (!aFile.isFile()) {
			M_log.info("Should not be a directory: " + aFile);
			return (false);
		}

		if (!aFile.canWrite()) {
			M_log.info("File cannot be written: " + aFile);
			return (false);
		}

		M_log.info("Setting up output stream ...");
		
		try {
			streamOut = new BufferedWriter(new FileWriter(aFile));
		} catch (IOException e) {
			M_log.info("Can't open output file: " + e.getMessage());
			return (false);
		}

		return (true);
	}

	/**
	 *
	 */
	public void closeStream() {
		// M_log.info ("closeStream ()");

		if (streamOut != null) {
			try {
				streamOut.close();
				streamOut = null;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error closing output stream");
			}
		}
	}

	/**
	 *
	 */
	public void writeToStream(String aContents) {
		if (streamOut != null) {
			try {
				streamOut.write(aContents);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				streamOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 *
	 */
	public void writeValueToStream(String aContents) {
		String aValue="";
		
		if (aContents!=null) {
			aValue=aContents;
		} else {
			M_log.info("Null Argument!");
		}
		
		writeToStream (aValue);
	}

	/**
	 *
	 */
	public String loadContents(String aFileURI) {
		M_log.info("loadContents (" + aFileURI + ")");

		File aFile = new File(aFileURI);
		
		if (aFile.exists()==false) {
			M_log.info("Error: file does not exist");
			return (null);
		}

		StringBuilder contents = new StringBuilder();

		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));

			try {
				String line = null; // not declared within while loop

				/*
				 * readLine is a bit quirky : it returns the content of a line MHoopUS
				 * the newline. it returns null only for the END of the stream. it
				 * returns an empty String if two newlines appear in a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} catch (IOException e) {
				return (null);
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return (null);
		}

		M_log.info("Loaded " + contents.length() + " characters");

		return (contents.toString());
	}
}
