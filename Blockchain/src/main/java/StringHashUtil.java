package ch.bbw.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHashUtil {
	
	private static String getHexString(byte [] buffer) {
		String hexString="";
		for (byte d : buffer) {
			String nextByte = Integer.toHexString(d & 0xFF).toLowerCase();
			if (nextByte.length()<2) { 
					nextByte = "0" + nextByte;
			}				
			hexString += nextByte;
		}
		return hexString;
	}
	
	public static String getHashForString(String data) {
		try {
		
			MessageDigest md = MessageDigest.getInstance( "SHA-256" );
			md.update( data.getBytes() );
			byte[] digest = md.digest();
			
			return getHexString(digest);
			
		} catch (NoSuchAlgorithmException e) {}
		return "";
	}
}
