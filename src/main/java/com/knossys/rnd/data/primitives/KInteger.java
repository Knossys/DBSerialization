package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KInteger implements KBClass {

	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;	
	private String name="";
	private Integer value=0;
	
	/**
	 * 
	 */
	public KInteger (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KInteger (String aName, String aValue) {
		setName (aName);
		setValue (Integer.parseInt(aValue));
	}		
	
	/**
	 * 
	 */
	public KInteger (String aName, Integer aValue) {
		setName (aName);
		setValue ((Integer) aValue);
	}	
	
	/**
	 * 
	 */
	public KInteger (String aName, int aValue) {
		Integer tempDouble=aValue;
		
		setName (aName);
		setValue (tempDouble);
	}

	/**
	 * Copy constructor
	 */
	public KBClass copy() {
		KInteger clone=new KInteger (name);
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
		value=(Integer) aValue;
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
    	return (name + " INT NOT NULL");
    }
		
    return (name + " INT");
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
			value=anEntry.getInt(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
