package ch.bbw.blockchain;

import ch.bbw.blockchain.StringHashUtil;
import java.util.Date;

public class Block {
	private String data; 
	private long timeStamp; 
	private String hash;
	private String previousHash;
	private int nonce;
	
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public String calculateHash() {
		return StringHashUtil.getHashForString( data + Long.toString(timeStamp)+ Integer.toString(nonce) + previousHash );
	}

	public void mineBlock(int difficulty){

		String target = new String(new char[difficulty]).replace('\0','0');

		while(!hash.substring(0,difficulty).equals(target)){
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!! : " + hash);
	}

	public String getFullData() {
		return data + Long.toString(timeStamp) + previousHash;
	}

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
