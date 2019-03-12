/**
 * Ref:
 * 
 *  http://tutorials.jenkov.com/java/interfaces.html
 */
package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;

/**
 * @author vvelsen
 */
public interface KBClass {
	
	/**
	 * Call this if you want this object to be included when doing a retrieval
	 * query on the database
	 * 
	 * @param aValue
	 */
	public void select (boolean aValue);
	
	/**
	 * @return
	 */
	public boolean isSelected ();

	/**
	 * 
	 */
	public void setName (String aName);
	
	/**
	 * @return
	 */
	public String getName ();
	
	/**
	 * 
	 */
	public void setValue (Object aValue);
	
	/**
	 * @param <T>
	 * @return
	 */
	public Object getValue ();
	
	/**
	 * @param aValue
	 */
	public void setIsRequired (boolean aValue);
	
	/**
	 * 
	 */
	public boolean getIsRequired ();
	
	/**
	 * @param aValue
	 */
	public void setPrimaryKey (boolean aValue);
	
	/**
	 * @return
	 */
	public boolean getPrimaryKey ();
	
	/**
	 * @return
	 */
	public String generateSchema ();
	
	/**
	 * @return
	 */
	public String generateCreateStatement ();
	
	/**
	 * @return
	 */
	public String generateUpdateStatement ();

	/**
	 * @param anEntry
	 */
	public void extract(ResultSet anEntry);
}
