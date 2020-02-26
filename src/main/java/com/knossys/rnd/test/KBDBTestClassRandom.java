package com.knossys.rnd.test;

import java.util.Date;
import java.util.Random;

import com.knossys.rnd.data.KDBTable;
import com.knossys.rnd.data.db.DbDriverInterface;
import com.knossys.rnd.data.primitives.KBoolean;
import com.knossys.rnd.data.primitives.KDouble;
import com.knossys.rnd.data.primitives.KFloat;
import com.knossys.rnd.data.primitives.KGUID;
import com.knossys.rnd.data.primitives.KInteger;
import com.knossys.rnd.data.primitives.KLong;
import com.knossys.rnd.data.primitives.KString;
import com.knossys.rnd.data.primitives.KText;

/**
 * @author vvelsen
 */
public class KBDBTestClassRandom extends KDBTable {

	public KLong longEntry=new KLong ("longEntry",new Date ().getTime()); // Primary key
	public KGUID guidEntry=new KGUID ("guidEntry");
	public KDouble doubleEntry=new KDouble ("doubleEntry",0D);
	public KFloat floatEntry=new KFloat ("floatEntry",0F);
	public KString stringEntry=new KString ("stringEntry","string value");
	public KText textEntry=new KText ("textEntry","text value");
	public KBoolean booleanEntry=new KBoolean ("booleanEntry",true);	
	public KInteger integerEntry=new KInteger ("integerEntry",0);	
	
	/**
	 * 
	 */
	public KBDBTestClassRandom (DbDriverInterface aDriver) {
		super(aDriver);
		
		setTableName ("testtablerandom");
		
		longEntry.setPrimaryKey(true);
    addEntry (longEntry);
    addEntry (guidEntry);
    addEntry (doubleEntry);
    addEntry (floatEntry);
    addEntry (stringEntry);
    addEntry (textEntry);
    addEntry (booleanEntry);
    addEntry (integerEntry);
	}

	/**
	 * @return
	 */
	protected String getSaltString(int aLength) {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < aLength) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }	
	
	/**
	 * 
	 */
	public void makeChanges() {
		Random r = new Random();
		
    longEntry.setValue(new Date ().getTime());
    guidEntry.generate();
    doubleEntry.setValue(r.nextDouble());
    floatEntry.setValue(r.nextFloat());
    stringEntry.setValue(getSaltString(25));
    textEntry.setValue(getSaltString(250));
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
    guidEntry.generate();
    doubleEntry.setValue(r.nextDouble());
    floatEntry.setValue(r.nextFloat());
    stringEntry.setValue(getSaltString(25));
    textEntry.setValue(getSaltString(250));
    booleanEntry.setValue(r.nextBoolean());
    integerEntry.setValue(r.nextInt());
				
		modify ();
	}	
}
