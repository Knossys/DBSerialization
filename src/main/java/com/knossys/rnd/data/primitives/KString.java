package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KString implements KBClass {
	
	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;	
	private String name="";
	private String value="";
	private Integer size=150;
	
	/**
	 * 
	 */
	public KString (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KString (String aName, String aValue) {
		setName (aName);
		setValue (aValue);
	}	
	
	/**
	 * Copy constructor
	 */
	public KBClass copy() {
		KString clone=new KString (name);
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
	 * @param aSize
	 */
	public void setSize (Integer aSize) {
		size=aSize;
	}
	
	/**
	 * 
	 */
	public Integer getSize () {
		return (size);
	}

	/**
	 * 
	 */
	public String generateSchema() {
    if (isRequired==true) {
    	return (name + " VARCHAR("+size+") NOT NULL");
    }
		
    return (name + " VARCHAR("+size+")");
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
			value=anEntry.getString(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
