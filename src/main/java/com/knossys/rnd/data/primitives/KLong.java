package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KLong implements KBClass {

	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;	
	private String name="";
	private Long value=0L;
	
	/**
	 * 
	 */
	public KLong (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KLong (String aName, String aValue) {
		setName (aName);
		setValue (Long.parseLong(aValue));
	}		
	
	/**
	 * 
	 */
	public KLong (String aName, Long aValue) {
		setName (aName);
		setValue (aValue);
	}	
	
	/**
	 * 
	 */
	public KLong (String aName, long aValue) {
		Long tempValue=aValue;
		
		setName (aName);
		setValue (tempValue);
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
		value=(Long) aValue;
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
    	return (name + " BIGINT NOT NULL");
    }
		
    return (name + " BIGINT");
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
			value=anEntry.getLong(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
