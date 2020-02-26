package com.knossys.rnd.data.db;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author vvelsen
 */
public interface DbDriverInterface {
		
	/**
	 * @return
	 */
	public Boolean connect ();

	/**
	 * @return
	 */
	public Boolean close();
	
	/**
	 * @throws Exception
	 */
	public void prepTables() throws Exception;
	
	/**
	 * @return
	 */
	public Statement createStatement();

	/**
	 * You need to close the resultSet
	 */
	public void closeStatement(Statement statement, ResultSet resultSet);

	/**
	 * @param statement
	 * @param statementString
	 * @return
	 */
	public int executeUpdate(Statement statement, String statementString);
		
  /**
   * @param statement
   * @param statementString
   * @return
   */
	public ResultSet executeQuery(Statement statement, String statementString);	

	/**
	 * 
	 * @param aTable
	 * @return
	 */
	public Boolean tableExists (String aTable);
	
	/**
	 * @return
	 */
	public Boolean supportJoinOutterRight ();
	
	/**
	 * @return
	 */
	public Boolean joinOutterFull ();
	
}
