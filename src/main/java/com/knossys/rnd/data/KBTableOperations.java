/**
 * References:
 * 
 * https://en.wikipedia.org/wiki/Join_(SQL)
 * 
 */
package com.knossys.rnd.data;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.knossys.rnd.data.db.DbDriverInterface;
import com.knossys.rnd.data.primitives.KBClass;

/**
 * @author vvelsen
 */
public class KBTableOperations extends KDBTable {

  private static Logger M_log = Logger.getLogger(KBTableOperations.class.getName());  
  
	/**
	 * @param aDriver
	 */
	public KBTableOperations(DbDriverInterface aDriver) {
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
	    if (test.isSelected()==true) {
	      entries.add(test);
	    }
	  }
	  
    for (int j=0;j<rightEntries.size();j++) {
      KBClass test=rightEntries.get(j);
      if (test.isSelected()==true) {
        entries.add(test);
      }
    }	  
	}
	
	/**
	 * @param left
	 * @param right
	 * @return
	 */
	public ArrayList<ArrayList<KBClass>> joinCross (KDBTable left, KDBTable right) {
    M_log.info("joinCross ()");
    
		StringBuffer statement=new StringBuffer ();
		
		mergeEntries (left,right);
		
		statement.append("SELECT * FROM " + left.getTableName() + " CROSS JOIN " + right.getTableName());
		
    return (executeStatementGetAll (statement.toString()));
	}
	
	/**
	 * @param left
	 * @param right
	 * @return
	 */
	public ArrayList<ArrayList<KBClass>> joinInner (KDBTable left, KDBTable right) {
    M_log.info("joinInner ()");
    
		StringBuffer statement=new StringBuffer ();
		
    mergeEntries (left,right);
		
		String primaryLeft=left.getPrimaryKey ().getName();
		String primaryRight=right.getPrimaryKey ().getName();
		
		statement.append("SELECT * FROM " + left.getTableName() + "," + right.getTableName() + " WHERE " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
		
    return (executeStatementGetAll (statement.toString()));
	}

  /**
   * @param left
   * @param right
   * @return
   */
  public ArrayList<ArrayList<KBClass>> joinOutterLeft (KDBTable left, KDBTable right) {
    M_log.info("joinOutterLeft ()");
    
    StringBuffer statement=new StringBuffer ();
    
    mergeEntries (left,right);
    
    String primaryLeft=left.getPrimaryKey ().getName();
    String primaryRight=right.getPrimaryKey ().getName();
    
    statement.append("SELECT * FROM " + left.getTableName() + " LEFT OUTER JOIN " + right.getTableName() + " ON " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
    
    return (executeStatementGetAll (statement.toString()));
  }
  
  /**
   * http://www.sqlitetutorial.net/sqlite-full-outer-join/
   * 
   * @param left
   * @param right
   * @return
   */
  public ArrayList<ArrayList<KBClass>> joinOutterRight (KDBTable left, KDBTable right) {
    M_log.info("joinOutterRight ()");
    
    if (driver.supportJoinOutterRight()==false) {
    	M_log.info("SQLITE doesn't support this operation yet");
    	return (null);
    }
    
    StringBuffer statement=new StringBuffer ();
    
    mergeEntries (left,right);
    
    String primaryLeft=left.getPrimaryKey ().getName();
    String primaryRight=right.getPrimaryKey ().getName();
     
    statement.append("SELECT * FROM " + left.getTableName() + " RIGHT OUTER JOIN " + right.getTableName() + " ON " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
    
    return (executeStatementGetAll (statement.toString()));
  }  
	
	/**
   * http://www.sqlitetutorial.net/sqlite-full-outer-join/
   * 
   * @param left
   * @param right
   * @return
   */
  public ArrayList<ArrayList<KBClass>> joinOutterFull (KDBTable left, KDBTable right) {
    M_log.info("joinOutterFull ()");
    
    if (driver.joinOutterFull ()==false) {
    	M_log.info("SQLITE doesn't support this operation yet");
    	return (null);
    }   
    
    StringBuffer statement=new StringBuffer ();
    
    mergeEntries (left,right);
    
    String primaryLeft=left.getPrimaryKey ().getName();
    String primaryRight=right.getPrimaryKey ().getName();
    
    statement.append("SELECT * FROM " + left.getTableName() + " FULL OUTER JOIN " + right.getTableName() + " ON " + left.getTableName() + "." + primaryLeft + " = " + right.getTableName() + "." + primaryRight);
    
    return (executeStatementGetAll (statement.toString()));
  }
  
  /**
   * Make a copy of the entire table. ONLY use this for small tables. We need this however
   * so that we can support tables being send across the network. In most cases this should
   * not be an issue because copy will most likely be called on tables that represent
   * snippets of data.
   * 
   * @return
   */
  public KDBTable copy () {
    M_log.info("copy ()");
    
  	KDBTable newTable=new KDBTable (this.driver);
	  
	  ArrayList<KBClass> fromEntries=this.getEntries();
  	
	  for (int i=0;i<fromEntries.size();i++) {
	    KBClass test=fromEntries.get(i);
      KBClass copy=test.copy();
      newTable.entries.add(copy);
	  }
  			
  	return (newTable);
  }
  
  /**
   * Make a copy of the entire table. ONLY use this for small tables. We need this however
   * so that we can support tables being send across the network. In most cases this should
   * not be an issue because copy will most likely be called on tables that represent
   * snippets of data.
   * 
   * @return
   */
  public KDBTable copy (KDBTable aSource) {
    M_log.info("copy (KDBTable)");
    
  	KDBTable newTable=new KDBTable (this.driver);
  	
	  ArrayList<KBClass> fromEntries=aSource.getEntries();
	  
	  M_log.info("Copying " + fromEntries.size() + " entries ...");
  	
	  for (int i=0;i<fromEntries.size();i++) {
	    KBClass test=fromEntries.get(i);
	    newTable.entries.add(test.copy());
	  }
  			
  	return (newTable);
  }  
}
