/**
 * Notes:
 * 
 *   https://dev.mysql.com/doc/refman/5.5/en/encryption-functions.html#function_aes-encrypt
 *   https://www.example-code.com/java/mysql_aes_encrypt.asp
 *   https://dzone.com/articles/mysql-data-at-rest-encryption
 */
package com.knossys.rnd.data.db;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
import org.apache.commons.codec.binary.Hex;

/**
 * @author vvelsen
 */
public class EncryptDecryptDrydock {
	
	/**
	 * @param key
	 * @param encoding
	 * @return
	 */
	public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
		try {
			final byte[] finalKey = new byte[16];
			int i = 0;
			for(byte b : key.getBytes(encoding))
				finalKey[i++%16] ^= b;			
			return new SecretKeySpec(finalKey, "AES");
		} catch(UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
 
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		
		// Decrypt
		final Cipher decryptCipher = Cipher.getInstance("AES");	        				
		decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey("your super secret passphrase", "UTF-8"));
		
		System.out.println(new String(decryptCipher.doFinal(Hex.decodeHex("56A34D7AB6225616799F6559AA388F07C2C9E53983111BDD5F49F36461AAD789".toCharArray()))));
 
		// Encrypt
		final Cipher encryptCipher = Cipher.getInstance("AES");	        				
		encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey("your super secret passphrase", "UTF-8"));		
		
		System.out.println(String.format("Select aes_decrypt(unhex('%s'), 'your super secret passphrase');", new String(Hex.encodeHex(encryptCipher.doFinal("Hallo nach Aachen".getBytes("UTF-8")))))); 
	}
}