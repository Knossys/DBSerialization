package com.knossys.rnd.data.primitives;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vvelsen
 */
public class KGUID implements KBClass {
	
	private boolean isPrimaryKey=false;
	private boolean isRequired=true;
	private boolean isSelectedInQuery=true;	
	private String name="id";
	private String value=java.util.UUID.randomUUID().toString();
	private Integer size=150;
	
	/**
	 * 
	 */
	public KGUID (String aName) {
		setName (aName);
	}
	
	/**
	 * 
	 */
	public KGUID (String aName, String aValue) {
		setName (aName);
		setValue (aValue);
	}	
	
	/**
	 * Copy constructor
	 */
	public KBClass copy() {
		KGUID clone=new KGUID (name);
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
    	return (name + " VARCHAR(36) NOT NULL");
    }
		
    return (name + " VARCHAR(36)");
	}

	/**
	 * 
	 */
	public String generateCreateStatement() {
		return value;
	}

	/**
	 * 
	 */
	public String generateUpdateStatement() {
		return value;
	}

	/**
	 * 
	 */
	public void generate() {
		value=java.util.UUID.randomUUID().toString();
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
