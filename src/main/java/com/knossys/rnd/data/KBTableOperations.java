/**
 * References:
 * 
 * https://en.wikipedia.org/wiki/Join_(SQL)
 * 
 */
package com.knossys.rnd.data;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.knossys.rnd.data.primitives.KBClass;

/**
 * @author vvelsen
 */
public class KBTableOperations extends KDBTable {

  private static Logger M_log = Logger.getLogger(KBTableOperations.class.getName());  
  
	/**
	 * @param aDriver
	 */
	public KBTableOperations(SQLiteDriver aDriver) {
		super(aDriver);
	}

	/**
	 * 
	 */
	private void mergeEntries (KDBTable left, KDBTable right) {
	  M_log.info("mergeEntries ()");
	  
	  entries=new ArrayList<KBClass> ();
	  
	  ArrayList<KBClass> leftEntries=left.getEntries();
	  ArrayList<KBClass> rightEntries=right.getEntries();
	  
	  for (int i=0;i<leftEntries.size();i++) {
	    KBClass test=leftEntries.get(i);
	    entries.add(test);
	  }
	  
    for (int j=0;j<rightEntries.size();j++) {
      KBClass test=rightEntries.get(j);
      entries.add(test);
    }	  
	}
	
	/**
	 * @param left
	 * @param right
	 * @return
	 */
	public boolean joinCross (KDBTable left, KDBTable right) {
    M_log.info("joinCross ()");
    
		StringBuffer statement=new StringBuffer ();
		
		mergeEntries (left,right);
		
		statement.append("SELECT * FROM " + left.getTableName() + " CROSS JOIN " + right.getTableName());
		
    return (executeStatement (statement.toString()));
	}
	
	/**
	 * @param left
	 * @param right
	 * @return
	 */
	public boolean joinInner (KDBTable left, KDBTable right) {
    M_log.info("joinInner ()");
    
		StringBuffer statement=new StringBuffer ();
		
    mergeEntries (left,right);
		
		String primaryLeft=left.getPrimaryKey ().getName();
		String primaryRight=right.getPrimaryKey ().getName();
		
		statement.append("SELECT * FROM " + left.getTableName() + "," + right.getTableName() + " WHERE " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
		
    return (executeStatement (statement.toString()));
	}

  /**
   * @param left
   * @param right
   * @return
   */
  public boolean joinOutterLeft (KDBTable left, KDBTable right) {
    M_log.info("joinOutterLeft ()");
    
    StringBuffer statement=new StringBuffer ();
    
    mergeEntries (left,right);
    
    String primaryLeft=left.getPrimaryKey ().getName();
    String primaryRight=right.getPrimaryKey ().getName();
    
    statement.append("SELECT * FROM " + left.getTableName() + " LEFT OUTER JOIN " + right.getTableName() + " ON " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
    
    return (executeStatement (statement.toString()));
  }
  
  /**
   * http://www.sqlitetutorial.net/sqlite-full-outer-join/
   * 
   * @param left
   * @param right
   * @return
   */
  public boolean joinOutterRight (KDBTable left, KDBTable right) {
    M_log.info("joinOutterRight ()");
    
    if (driver.getDriverType().equalsIgnoreCase("SQLITE")==true) {
    	M_log.info("SQLITE doesn't support this operation yet");
    	return (false);
    }
    
    StringBuffer statement=new StringBuffer ();
    
    mergeEntries (left,right);
    
    String primaryLeft=left.getPrimaryKey ().getName();
    String primaryRight=right.getPrimaryKey ().getName();
     
    statement.append("SELECT * FROM " + left.getTableName() + " RIGHT OUTER JOIN " + right.getTableName() + " ON " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
    
    return (executeStatement (statement.toString()));
  }  
	
	/**
   * http://www.sqlitetutorial.net/sqlite-full-outer-join/
   * 
   * @param left
   * @param right
   * @return
   */
  public boolean joinOutterFull (KDBTable left, KDBTable right) {
    M_log.info("joinOutterFull ()");
    
    if (driver.getDriverType().equalsIgnoreCase("SQLITE")==true) {
    	M_log.info("SQLITE doesn't support this operation yet");
    	return (false);
    }   
    
    StringBuffer statement=new StringBuffer ();
    
    mergeEntries (left,right);
    
    String primaryLeft=left.getPrimaryKey ().getName();
    String primaryRight=right.getPrimaryKey ().getName();
    
    statement.append("SELECT * FROM " + left.getTableName() + " FULL OUTER JOIN " + right.getTableName() + " ON " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
    
    return (executeStatement (statement.toString()));
  }
}
