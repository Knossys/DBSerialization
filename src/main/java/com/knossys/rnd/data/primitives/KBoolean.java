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
	 * Copy constructor
	 */
	public KBClass copy() {
		KBoolean clone=new KBoolean (name);
		clone.setIsRequired(isRequired);
		clone.setPrimaryKey(isPrimaryKey);
		clone.select (isSelectedInQuery);
		clone.setValue(value);
		return clone;
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
    	return (name + " TINYINT(1) NOT NULL");
    }
		
    return (name + " TINYINT(1)");
    
		/*
    if (isRequired==true) {
    	return (name + " INT NOT NULL");
    }
		
    return (name + " INT");		
    */
	}

	/**
	 * 
	 */
	public String generateCreateStatement() {
		Integer converter=0;
		
		if (value==true) {
			converter=1;
		} else {
			converter=0;
			
		}
		return converter.toString();
	}

	/**
	 * 
	 */
	public String generateUpdateStatement() {
		Integer converter=0;
		
		if (value==true) {
			converter=1;
		} else {
			converter=0;
			
		}
		return converter.toString();
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
			//System.out.println("New value " + this.name + ": " + anEntry.getBoolean(this.name) +" -> " + anEntry.getInt(this.name));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		/*
		Integer testValue=0;
		
		try {
			testValue=anEntry.getInt(this.name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (testValue==1) {
			value=true;
		} else {
			value=false;
		}
		*/
	}
}
