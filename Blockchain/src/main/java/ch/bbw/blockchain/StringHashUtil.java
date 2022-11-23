package ch.bbw.blockchain;

import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

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

	//Applies ECDSA Signature and returns the result ( as bytes ).
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getStringFromKey(Key key){
		return(Base64.getEncoder().encodeToString(key.getEncoded()));
	}

	//Tacks in array of transactions and returns a merkle root.
	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
		int count = transactions.size();
		ArrayList<String> previousTreeLayer = new ArrayList<String>();
		for(Transaction transaction : transactions) {
			previousTreeLayer.add(transaction.transactionId);
		}
		ArrayList<String> treeLayer = previousTreeLayer;
		while(count > 1) {
			treeLayer = new ArrayList<String>();
			for(int i=1; i < previousTreeLayer.size(); i++) {
				treeLayer.add(getHashForString(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return merkleRoot;
	}
}
