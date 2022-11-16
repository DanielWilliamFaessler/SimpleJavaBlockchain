package ch.bbw.blockchain;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class BlockChainTest {

	public static void main(String[] args) {
		
		ArrayList<Block> blockchain = new ArrayList<>();
		//adding Blocks to Blockchain
		blockchain.add(new Block("Hi I am patient 0","0"));
		blockchain.add(new Block("Hi I am patient 1",blockchain.get(blockchain.size()-1).getHash()));
		blockchain.add(new Block("Hi I am the the 3rd one everybody hates me",blockchain.get(blockchain.size()-1).getHash()));

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(blockchainJson);
		if (isChainValid(blockchain) == true){
			System.out.println("we good");
		}
		else{
			System.out.println("we are all so fucked");
		}
	}
	public static Boolean isChainValid(ArrayList<Block> blockchain){
		Block currentBlock;
		Block previousBlock;
		for (int i =1; i< blockchain.size(); i++){
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}




