package com.knossys.rnd.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.knossys.rnd.data.primitives.KBClass;

/**
 * @author vvelsen
 */
public class KDBTable {
	
	private static Logger M_log = Logger.getLogger(KDBTable.class.getName());
	
	protected String tableName="unassigned";
	protected ArrayList<KBClass> entries=new ArrayList<KBClass> ();
	
	protected SQLiteDriver driver=null;
	
	/**
	 * 
	 */
	public KDBTable (SQLiteDriver aDriver) {
		driver=aDriver;
	}
	
	/**
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}	
	
	/**
	 * 
	 * @param anEntry
	 */
	public void addEntry (KBClass anEntry) {
		entries.add(anEntry);
	}
	
	/**
	 * @return
	 */
	public ArrayList<KBClass> getEntries () {
	  return entries;
	}
	
	/**
	 * 
	 */
	public void selectall () {
    for (int i=0;i<entries.size();i++) {
      KBClass anEntry=entries.get(i);
      anEntry.select(true);
    }	  
	}

  /**
   * 
   */
  public void selectNone () {
    for (int i=0;i<entries.size();i++) {
      KBClass anEntry=entries.get(i);
      anEntry.select(false);
    }   
  }	
	
	/**
	 * @return
	 */
	public String generateSchema () {
		M_log.info("generateSchema ()");
		
		StringBuffer formatter=new StringBuffer();
		
		formatter.append("CREATE TABLE IF NOT EXISTS ");
		formatter.append(tableName);
		formatter.append(" (");
		
		for (int i=0;i<entries.size();i++) {
			KBClass anEntry=entries.get(i);
			
			if (i>0) {
				formatter.append(", ");
			}
			formatter.append(anEntry.generateSchema());
		}
			
		formatter.append(", PRIMARY KEY (");
		
		int index=0;
		
		for (int j=0;j<entries.size();j++) {
			KBClass anEntry=entries.get(j);
			
			if (anEntry.getPrimaryKey()==true) {
			  if (index>0) {
				  formatter.append(", ");
			  }
			  formatter.append(anEntry.getName());
			  index++;
			}
		}
		
		formatter.append(")");
		
		formatter.append(");");
		
		return (formatter.toString());
	}
	
	/**
	 * @return
	 */
	public String generateCreateStatement () {
		M_log.info("generateCreateStatement ()");
		
		StringBuffer formatter=new StringBuffer();
		
		formatter.append("INSERT INTO " + tableName + " (");
		
		for (int i=0;i<entries.size();i++) {
			KBClass anEntry=entries.get(i);
			
			if (i>0) {
				formatter.append(", ");
			}
			
			formatter.append(anEntry.getName());
		}
		
		formatter.append(") VALUES (");
		
		for (int j=0;j<entries.size();j++) {
			KBClass anEntry=entries.get(j);
			
			if (j>0) {
				formatter.append(", ");
			}
				
			formatter.append("'" + anEntry.generateCreateStatement()+"'");
		}
		
		formatter.append (");");
				
		return (formatter.toString());
	}
	
	/**
	 * @return
	 */
	public String generateUpdateStatement () {
		M_log.info("generateUpdateStatement ()");
		
		StringBuffer formatter=new StringBuffer();
		
		formatter.append("UPDATE " + tableName + " SET ");
		
		for (int i=0;i<entries.size();i++) {
			KBClass anEntry=entries.get(i);
			
			if (i>0) {
				formatter.append(", ");
			}
			
			formatter.append(anEntry.getName() + "='" + anEntry.generateUpdateStatement() + "'");
		}
		
		formatter.append(" WHERE ");
		
		int index=0;
		
		for (int j=0;j<entries.size();j++) {
			KBClass anEntry=entries.get(j);
			
			if (anEntry.getPrimaryKey()==true) {
				if (index>0) {
					formatter.append(", ");
				}
				
				formatter.append(anEntry.getName() + "='" + anEntry.generateUpdateStatement() + "'");
				index++;
			}
		}
		
		formatter.append (";");
				
		return (formatter.toString());
	}
	
	/**
	 * 
	 */
	protected void configureEnvironment () {	
		M_log.info("configureEnvironment ()");	
		
		M_log.info("No need to implement this in this class");
	}
	
	/**
	 * @return
	 */
	public KBClass getPrimaryKey () {
		for (int j=0;j<entries.size();j++) {
			KBClass anEntry=entries.get(j);
			
			if (anEntry.getPrimaryKey()==true) {
        return (anEntry);
			}
		}
		
		return (null);
	}
	
	/**
	 * @param aCompany
	 * @return
	 */
	public Boolean entryExists(String aKey,String aValue) {
		M_log.info("entryExists (" + aKey + " => " + aValue + ")");
		
		if (driver==null) {
			return (false);
		}
		
		ResultSet result = null;
		
		Statement statement = driver.createStatement();

		if (statement == null) {
			return (false);
		}

		String statementString = "SELECT * FROM " + tableName + " WHERE "+aKey+"='"+aValue+"'";
		
		result = driver.executeQuery(statement,statementString);
		
		//M_log.info("result: " + result);
		
		if (result!=null) {
			try {
				if (result.next()==true) {
				  return (true);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (false);
	}	
	
	/**
	 * @throws Exception
	 */
	public void prepTables() throws Exception {
		M_log.info("prepTables ()");
		
		if (driver==null) {
			return;
		}		
	
		ResultSet resultSet = null;
		Statement statement = null;
		String statementString = "";
		int result = -1;

		statement = driver.createStatement();

		if (statement == null) {
			return;
		}
		
		statementString = generateSchema ();
		
		M_log.info("Executing: " + statementString);

		try {
			result = statement.executeUpdate(statementString);
		} catch (Exception e) {
			M_log.info("result: " + e.getMessage());
		} finally {
			driver.closeStatement(statement, resultSet);
		}		
		
		//modify();
	}	
	
	/**
	 * 
	 */
	public void modify() {		
		M_log.info("modify ()");
		
		if (driver==null) {
			return;
		}		
		
		ResultSet resultSet = null;
		Statement statement = null;
		String statementString = "";
		int result = -1;
		
		KBClass primaryKey=getPrimaryKey ();
		
		if (primaryKey==null) {
			return;
		}
		
		if (entryExists (primaryKey.getName(),primaryKey.getValue().toString())==true) {	
			statement = driver.createStatement();
	
			if (statement == null) {
				return;
			}
			
			statementString = generateUpdateStatement ();
			
			M_log.info("Executing: " + statementString);
	
			try {
				result = statement.executeUpdate(statementString);
			} catch (Exception e) {
				M_log.info("result: " + e.getMessage());
			} finally {
				driver.closeStatement(statement, resultSet);
			}	
		} else {
			statement = driver.createStatement();
	
			if (statement == null) {
				return;
			}
			
			statementString = generateCreateStatement ();
			
			M_log.info("Executing: " + statementString);
	
			try {
				result = statement.executeUpdate(statementString);
			} catch (Exception e) {
				M_log.info("result: " + e.getMessage());
			} finally {
				driver.closeStatement(statement, resultSet);
			}				
		}
	}
	
	/**
	 * @param k
	 */
	public Boolean getInstance() {		
		M_log.info("getInstance ()");
		
		if (driver==null) {
			return (false);
		}
				
		StringBuffer selectFormatter=new StringBuffer ();
		
		int index=0;
		
		for (int i=0;i<entries.size();i++) {
			KBClass anEntry=entries.get(i);
			
			if (anEntry.isSelected()==true) {
				if (index>0) {
					selectFormatter.append(", ");
				}
				
				selectFormatter.append(anEntry.getName());
				
				index++;
			}			
		}		

		StringBuffer formatter=new StringBuffer ();
		
		index=0;
		
		for (int i=0;i<entries.size();i++) {
			KBClass anEntry=entries.get(i);
			
			if (anEntry.getPrimaryKey()==true) {
				if (index>0) {
					formatter.append(", ");
				}
				
				formatter.append(anEntry.getName()+ "='"+anEntry.getValue().toString()+"'");
				
				index++;
			}			
		}
		
		String statementString = "SELECT "+selectFormatter+" FROM " + tableName + " WHERE " + formatter.toString() + ";";
		
		M_log.info("Query: " + statementString);
		
    return (executeStatement (statementString));
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean executeStatement (String statementString) {
	  M_log.info("executeStatement (" + statementString + ")");
	  
    ResultSet resultSet = null;
    
    Statement statement = driver.createStatement();

    if (statement == null) {
    	M_log.info("Stament is null, can't execute");
      return (false);
    }
    
    resultSet = driver.executeQuery(statement,statementString);
    
    if (resultSet==null) {
    	return(false);
    }
      
    int index=0;
    
    if (resultSet!=null) {
    	M_log.info("We have results, extracting ...");
      try {
        index=0;
        while (resultSet.next() && (index<entries.size())) {
          KBClass anEntry=entries.get(index);
          if (anEntry.isSelected()==true) {
            anEntry.extract (resultSet);
            index++;
          }
        }
      } catch (Exception e) {
        M_log.info("DB Exception: " + e.getMessage());
        driver.closeStatement(statement, resultSet);
        return (false);
      } finally {
        driver.closeStatement(statement, resultSet);
      }
    } 
    
    return(true);	  
	}
	
	/**
	 * @return
	 */
	public JsonObject toJSON () {
		JsonObjectBuilder JSONDisplayObject=Json.createObjectBuilder();
				
		for (int i=0;i<entries.size();i++) {
			KBClass anEntry=entries.get(i);
			
			if (anEntry.isSelected()==true) {
				JSONDisplayObject.add(anEntry.getName(),anEntry.getValue().toString());				
			}			
		}				
		
		return (JSONDisplayObject.build());
	}
	
	/**
	 * @return
	 */
	public String toJSONString () {		
		JsonObject builder=toJSON ();
		
		if (builder!=null) {
			return (builder.toString());
		}
		
		return ("{}");
	}
}
