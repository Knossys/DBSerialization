package com.knossys.rnd.kshiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import com.knossys.rnd.data.KBTableOperations;
import com.knossys.rnd.data.KDBTable;
import com.knossys.rnd.data.db.SQLiteDriver;
import com.knossys.rnd.data.primitives.KBClass;

import com.knossys.rnd.test.KBDBTestClassIndexed;
import com.knossys.rnd.test.KBDBTestClassRandom;
import com.knossys.rnd.test.KBDBTestClassRandomSmallA;
import com.knossys.rnd.test.KBDBTestClassRandomSmallB;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 */
public class AppTest extends TestCase {
  
  private static Logger M_log = Logger.getLogger(AppTest.class.getName());
  
  // Use SQLite for this test. There should be a separate but similar test
  // with the MySQL and Cassandra drivers
  private SQLiteDriver driver=new SQLiteDriver ();
  
  private KBDBTestClassRandom testClassRandom=null;
  private KBDBTestClassIndexed testClassIndexed=null; 
  
  private KBDBTestClassRandomSmallA testClassRandomSmall1=null; 
  private KBDBTestClassRandomSmallB testClassRandomSmall2=null; 

  private KBDBTestClassRandomSmallA testSourceTable=null; 
  
  /**
   * Create the test case
   *
   * @param testName
   *          name of the test case
   */
  public AppTest(String testName) {
    super(testName);
    
    M_log.info("AppTest ("+testName+")");
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }
  
  /**
   * A support function to get a random integer from a range. We use this
   * to grab a random entry from the database.
   * 
   * @param min
   * @param max
   * @return
   */
  private static int getRandomNumberInRange(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }

    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  } 

  /**
   * 
   */
  public void testApp() {  	  	
    M_log.info("testApp ()");
    
    // How many random instances do we want per test/table
    int maxEntries=10000;

    // Create an instance of the table/class that has a guid as the primary key
    testClassRandom=new KBDBTestClassRandom (driver);
    
    //Create an instance of the table/class that has an index as the primary key
    testClassIndexed=new KBDBTestClassIndexed (driver);
    
    // Configure the database driver, in this case specific to SQLite
    driver.setDbPath ("./db");
    driver.setDbName ("testdb");
    driver.init ();
    
    // Allow the table class to configure its table schema
    try {
      testClassRandom.prepTables ();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    // Allow the table class to configure its table schema    
    try {
      testClassIndexed.prepTables ();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    
    // Generate 1000 random instances
    for (long t=0;t<1000;t++) {
    	testClassRandom.makeChanges(t);
    }
    
    // Generate 1000 random instances, but keep the indices the same
    for (long t=0;t<1000;t++) {
    	testClassIndexed.indexChanges(t);
    }
        
    // Try to load 10 random instances from the database
    for (int k=0;k<10;k++) {
      Long randomInstance=(long) getRandomNumberInRange (0,maxEntries);
      
      M_log.info("Loading random instance: " + randomInstance);
      
      // Configure the primary key with the value we want to load
      testClassIndexed.longEntry.setValue (randomInstance);
      
      // Let the sub system load the data from the database and
      // fill in all the requested values.
      testClassIndexed.getInstance ();
      
      // At this point testClassIndexed should be configured with
      // the values from the database
      
      M_log.info("Current state: " + testClassIndexed.toJSONString());
    }
        
    KBTableOperations tableOperations=new KBTableOperations (driver);
    
    if (tableOperations.joinCross(testClassRandom, testClassIndexed)==null) {
    	fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinInner(testClassRandom, testClassIndexed)==null) {
    	fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinOutterLeft(testClassRandom, testClassIndexed)==null) {
    	fail("Test failed, see log for details");
    }
    
    // RIGHT and FULL OUTER JOINs are not currently supported in SQLite
    
    /*
    if (tableOperations.joinOutterRight(testClassRandom, testClassIndexed)==null) {
    	fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinOutterFull(testClassRandom, testClassIndexed)==null) {
    	fail("Test failed, see log for details");
    }
    */
    
    //>--------------------------------------------------------------------------------
    
    // Create an instance of a table/class that is small enough so that we can see what we're doing
    testClassRandomSmall1=new KBDBTestClassRandomSmallA (driver);
    testClassRandomSmall1.setTableName ("SmallA");
    
    // Allow the table class to configure its table schema    
    try {
      testClassRandomSmall1.prepTables ();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }    
    
    // Generate 10 random instances
    for (long t=0;t<10;t++) {
      testClassRandomSmall1.makeChanges(t);
    }
    
    // Create an instance of a table/class that is small enough so that we can see what we're doing
    testClassRandomSmall2=new KBDBTestClassRandomSmallB (driver);
    testClassRandomSmall2.setTableName ("SmallB");
    
    // Allow the table class to configure its table schema    
    try {
      testClassRandomSmall2.prepTables ();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    
    // Generate 10 random instances
    for (long t=0;t<10;t++) {
      testClassRandomSmall2.makeChanges(t);
    }
    
    ArrayList<ArrayList<KBClass>> results=null;

    results=testClassRandomSmall1.getAll();
    
    if (results==null) {
      fail("Test failed, see log for details");
    }
    
    testClassRandomSmall1.toTSV("./db/smalla-all.tsv", results);
    
    results=testClassRandomSmall2.getAll();
    
    if (results==null) {
      fail("Test failed, see log for details");
    }
    
    testClassRandomSmall2.toTSV("./db/smallb-all.tsv", results);
    
    //>--------------------------------------------------------------------------------
    
    results=tableOperations.joinCross(testClassRandomSmall1, testClassRandomSmall2);
    
    if (results==null) {
      fail("Test failed, see log for details");
    }
        
    tableOperations.toTSV("./db/table-join-cross.tsv", results);
        
    results=tableOperations.joinInner(testClassRandomSmall1, testClassRandomSmall2);
    
    if (results==null) {
      fail("Test failed, see log for details");
    }    
    
    tableOperations.toTSV("./db/table-join-inner.tsv", results);
    
    results=tableOperations.joinOutterLeft(testClassRandomSmall1, testClassRandomSmall2);
    
    if (results==null) {
      fail("Test failed, see log for details");
    }
    
    tableOperations.toTSV("./db/table-join-left.tsv", results);
    
    // RIGHT and FULL OUTER JOINs are not currently supported in SQLite
    
    /*
    if (tableOperations.joinOutterRight(testClassRandomSmall1, testClassRandomSmall2)==null) {
      fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinOutterFull(testClassRandomSmall1, testClassRandomSmall2)==null) {
      fail("Test failed, see log for details");
    }
    */    
    
    //>--------------------------------------------------------------------------------
    
    testSourceTable=new KBDBTestClassRandomSmallA (driver);
    testSourceTable.setTableName ("SourceTable");
        
    // Allow the table class to configure its table schema    
    try {
    	testSourceTable.prepTables ();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    
    KDBTable result=tableOperations.copy (testSourceTable);
    
    tableOperations.toTSV("./db/table-copy.tsv", result.getInstances());
    
    assertTrue(true);
  }
}
