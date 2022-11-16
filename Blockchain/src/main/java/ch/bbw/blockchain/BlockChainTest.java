package ch.bbw.blockchain;

import java.util.ArrayList;

public class BlockChainTest {

	public static void main(String[] args) {
		
		ArrayList<Block> blockchain = new ArrayList<>();
		
		Block genesis = new Block("Hi I am patient 0","0");
		System.out.println("Hash for Block 1: "+genesis.getHash());
		Block two = new Block("Hi I am patient 1",genesis.getHash());
		System.out.println("Hash for Block 2: "+two.getHash());

		
	}
}




