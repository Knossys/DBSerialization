package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;

/**
 * @author vvelsen
 */
public class KText implements KBClass {
	
	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;	
	private String name="";
	private String value="";
	
	/**
	 * 
	 */
	public KText (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KText (String aName, String aValue) {
		setName (aName);
		setValue (aValue);
	}		
	
	/**
	 * Copy constructor
	 */
	public KBClass copy() {
		KText clone=new KText (name);
		clone.setIsRequired(isRequired);
		clone.setPrimaryKey(isPrimaryKey);
		clone.select (isSelectedInQuery);
		clone.setValue(value);
		return clone;
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
		value=(String) aValue;
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
    	return (name + " LONGTEXT NOT NULL");
    }
		
    return (name + " LONGTEXT");
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
		
	}	
}
