/**
 * Ref:
 *   http://www.mysqltutorial.org/mysql-boolean/
 */
package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KBoolean implements KBClass {
	
	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;
	private String name="";
	private Boolean value=false;
	
	/**
	 * 
	 */
	public KBoolean (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KBoolean (String aName, String aValue) {
		setName (aName);
		setValue (Boolean.parseBoolean(aValue));
	}		
	
	/**
	 * 
	 */
	public KBoolean (String aName, Boolean aValue) {
		setName (aName);
		setValue ((Boolean) aValue);
	}	
	
	/**
	 * 
	 */
	public KBoolean (String aName, boolean aValue) {
		Boolean tempDouble=aValue;
		
		setName (aName);
		setValue (tempDouble);
	}		
	
	/**
	 * @param aName
	 */
	public void setName(String aName) {
		name=aName;
	}

	/**
	 * 
	 */
	public String getName() {
		return (name);
	}
	
	/**
	 * 
	 */
	public void setValue (Object aValue) {
		value=(Boolean) aValue;
	}
	
	/**
	 * @return
	 */
	public Object getValue () {
		return (value);
	}
	
	/**
	 * 
	 */
	public void select(boolean aValue) {
		isSelectedInQuery=aValue;
	}

	/**
	 * 
	 */
	public boolean isSelected() {
		return isSelectedInQuery;
	}	

	/**
	 * 
	 */
	public void setIsRequired(boolean aValue) {
		isRequired=aValue;
	}

	/**
	 * 
	 */
	public boolean getIsRequired() {
    return (isRequired);
	}

	/**
	 * 
	 */
	public void setPrimaryKey(boolean aValue) {
		isPrimaryKey=aValue;
	}

	/**
	 * 
	 */
	public boolean getPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * 
	 */
	public String generateSchema() {
    if (isRequired==true) {
    	return (name + " TINYINT(1) NOT NULL");
    }
		
    return (name + " TINYINT(1)");
	}

	/**
	 * 
	 */
	public String generateCreateStatement() {
		return getValue ().toString();
	}

	/**
	 * 
	 */
	public String generateUpdateStatement() {
		return getValue ().toString();
	}
	
	/**
	 * @param anEntry
	 */
	public void extract(ResultSet anEntry) {
		if (anEntry==null) {
			return;
		}
		
		try {
			value=anEntry.getBoolean(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
