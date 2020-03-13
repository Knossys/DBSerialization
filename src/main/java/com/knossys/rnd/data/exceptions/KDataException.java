/**
 * https://www.baeldung.com/java-new-custom-exception
 */
package com.knossys.rnd.data.exceptions;

/**
 * @author vvelsen
 */
public class KDataException extends Exception { 
	
	private static final long serialVersionUID = 4431565644820797350L;

	/**
	 * @param errorMessage
	 */
	public KDataException(String errorMessage) {
    super(errorMessage);
  }
}