package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KDouble implements KBClass {

	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;
	private String name="";
	private Double value=0D;
	
	/**
	 * 
	 */
	public KDouble (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KDouble (String aName, String aValue) {
		setName (aName);
		setValue (Long.parseLong(aValue));
	}		
	
	/**
	 * 
	 */
	public KDouble (String aName, Double aValue) {
		setName (aName);
		setValue ((Double) aValue);
	}	
	
	/**
	 * 
	 */
	public KDouble (String aName, double aValue) {
		Double tempDouble=aValue;
		
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
		value=(Double) aValue;
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
    	return (name + " DOUBLE NOT NULL");
    }
		
    return (name + " DOUBLE");
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
			value=anEntry.getDouble(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
