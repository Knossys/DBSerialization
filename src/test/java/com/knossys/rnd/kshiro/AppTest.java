package com.knossys.rnd.kshiro;

import java.util.Random;
import java.util.logging.Logger;

import com.knossys.rnd.data.KBTableOperations;
import com.knossys.rnd.data.SQLiteDriver;
import com.knossys.rnd.test.KBDBTestClassIndexed;
import com.knossys.rnd.test.KBDBTestClassRandom;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
  
  private static Logger M_log = Logger.getLogger(AppTest.class.getName());
  
  private SQLiteDriver driver=new SQLiteDriver ();
  
  private KBDBTestClassRandom testClassRandom=null;
  private KBDBTestClassIndexed testClassIndexed=null;  
  
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
    
    // Configure the database driver
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
    
    if (tableOperations.joinCross(testClassRandom, testClassIndexed)==false) {
    	fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinInner(testClassRandom, testClassIndexed)==false) {
    	fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinOutterLeft(testClassRandom, testClassIndexed)==false) {
    	fail("Test failed, see log for details");
    }
    
    // RIGHT and FULL OUTER JOINs are not currently supported in SQLite
    
    /*
    if (tableOperations.joinOutterRight(testClassRandom, testClassIndexed)==false) {
    	fail("Test failed, see log for details");
    }
    
    if (tableOperations.joinOutterFull(testClassRandom, testClassIndexed)==false) {
    	fail("Test failed, see log for details");
    }
    */
    
    assertTrue(true);
  }
}
