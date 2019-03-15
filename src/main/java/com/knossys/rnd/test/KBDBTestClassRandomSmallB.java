package com.knossys.rnd.test;

import java.util.Date;
import java.util.Random;

import com.knossys.rnd.data.KDBTable;
import com.knossys.rnd.data.SQLiteDriver;
import com.knossys.rnd.data.primitives.KBoolean;
import com.knossys.rnd.data.primitives.KInteger;
import com.knossys.rnd.data.primitives.KLong;

/**
 * @author vvelsen
 */
public class KBDBTestClassRandomSmallB extends KDBTable {

	public KLong longEntry=new KLong ("b-key",new Date ().getTime()); // Primary key
	public KBoolean booleanEntry=new KBoolean ("b-boolean",true);	
	public KInteger integerEntry=new KInteger ("b-integer",0);	
	
	/**
	 * 
	 */
	public KBDBTestClassRandomSmallB (SQLiteDriver aDriver) {
		super(aDriver);
		
		setTableName ("smalltableb");
		
		longEntry.setPrimaryKey(true);
    addEntry (longEntry);
    addEntry (booleanEntry);
    addEntry (integerEntry);
	}
	
	/**
	 * 
	 */
	public void makeChanges() {
		Random r = new Random();
		
    longEntry.setValue(new Date ().getTime());
    booleanEntry.setValue(r.nextBoolean());
    integerEntry.setValue(r.nextInt());
				
		modify ();
	}
	
	/**
	 * 
	 */
	public void makeChanges(Long anIndex) {
		Random r = new Random();
		
    longEntry.setValue(anIndex);
    booleanEntry.setValue(r.nextBoolean());
    integerEntry.setValue(r.nextInt());
				
		modify ();
	}	
}
