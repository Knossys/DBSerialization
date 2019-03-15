package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KFloat implements KBClass {
	
	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;	
	private String name="";
	private Float value=0F;
	
	/**
	 * 
	 */
	public KFloat (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KFloat (String aName, String aValue) {
		setName (aName);
		setValue (Float.parseFloat(aValue));
	}		
	
	/**
	 * 
	 */
	public KFloat (String aName, Float aValue) {
		setName (aName);
		setValue ((Float) aValue);
	}	
	
	/**
	 * 
	 */
	public KFloat (String aName, float aValue) {
		Float tempDouble=aValue;
		
		setName (aName);
		setValue (tempDouble);
	}	
	
	/**
	 * Copy constructor
	 */
	public KBClass copy() {
		KFloat clone=new KFloat (name);
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
		name = aName.replaceAll("[\\s-]+", "_");
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
		value=(Float) aValue;
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
		
		setIsRequired (true);
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
    	return (name + " FLOAT NOT NULL");
    }
		
    return (name + " FLOAT");
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
			value=anEntry.getFloat(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
