package com.knossys.rnd.data;

import java.sql.ResultSet;
import java.sql.Statement;

import com.knossys.rnd.data.db.DbDriverInterface;

/**
 * @author vvelsen
 */
public class DbBaseDriver implements DbDriverInterface {
	
	private String driverType="ABSTRACT";
	
	/**
	 * @return
	 */
	public String getDriverType() {
		return driverType;
	}

	/**
	 * @param driverType
	 */
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	/**
	 * 
	 */
	public Boolean connect() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public Boolean close() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public void prepTables() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public Statement createStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public void closeStatement(Statement statement, ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public int executeUpdate(Statement statement, String statementString) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 */
	public ResultSet executeQuery(Statement statement, String statementString) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public Boolean tableExists(String aTable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return
	 */
	public Boolean supportJoinOutterRight () {
		return (true);
	}
	
	/**
	 * @return
	 */
	public Boolean joinOutterFull () {
		return (true);
	}
}
