package com.knossys.rnd.data;

import com.knossys.rnd.data.db.DbDriverInterface;
import com.knossys.rnd.tools.StringTools;

/**
 * A high level representation of a data set which is assumed to be moved around,
 * stored on disk temporarily and streamed from and to a database. 
 * 
 */
public class KDataset extends KBTableOperations {
	
	// This module is currently idle (most consumer modules will be like this a lot)
	public static String COMMAND_IDLE = "idle ()";
	// This module produced data but expects to produce more
	public static String COMMAND_MORE = "more ()";
	// This module is finished and should be stopped by the manager
	public static String COMMAND_FINISHED = "finished ()";
	// This module encountered an exception and should either be stopped or quarantined
	public static String COMMAND_EXCEPTION = "exception ()";
	// This module, or an authorized piece of code, requests the total set of modules to be stopped
	public static String COMMAND_STOP = "stop ()";
	
	// We maintain a unique id per data set instantiation so that
	// it is possible to track what module the data comes from and
	// where it should be going.
	private String id="-1";
  
  // Future use, we might want to store what has happened to
  // the dataset of perhaps indicate it's the last one produced,
  // etc
  private String command=COMMAND_IDLE;
  
  /**
   * 
   */
  public KDataset (DbDriverInterface aDriver) {
  	super(aDriver);
  	setId(StringTools.generateStringUUID ());	
  }
  
  /**
   * @return
   */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}  
  
	/**
	 * @return
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
