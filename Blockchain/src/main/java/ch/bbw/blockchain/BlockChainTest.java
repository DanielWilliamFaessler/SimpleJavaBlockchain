package ch.bbw.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

public class BlockChainTest {
	public static int difficulty = 7;
	public static ArrayList<Block> blockchain = new ArrayList<>();
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); //list of all unspent transactions.
	public static Wallet walletA;
	public static Wallet walletB;
	public static float minimumTransaction;

	public static void main(String[] args) {

		//Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();

		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringHashUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringHashUtil.getStringFromKey(walletA.publicKey));

		//Create a test transaction from WalletA to walletB
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);

		//Verify the signature works and verify it from the public key
		System.out.println("The moment of truth is the signature verified");
		System.out.println(transaction.verifiySignature());

		//adding Blocks to Blockchain
		System.out.println();
		blockchain.add(new Block("Hi I am patient 0","0"));
		System.out.println("mining Block 1 ...");
		blockchain.get(0).mineBlock(difficulty);
		blockchain.add(new Block("Hi I am patient 1",blockchain.get(blockchain.size()-1).getHash()));
		System.out.println("mining Block 2 ...");
		blockchain.get(1).mineBlock(difficulty);
		blockchain.add(new Block("Hi I am the the 3rd one everybody hates me",blockchain.get(blockchain.size()-1).getHash()));
		System.out.println("mining 3 ...");
		blockchain.get(2).mineBlock(difficulty);

		System.out.println("\nBlockchain is Valid: " + isValidMessage());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);


	}

	public static String isValidMessage(){
		if (isChainValid(blockchain) == true){
			return("Status: we good");
		}
		else{
			return("Status: we are all so fucked");
		}
	}

	public static Boolean isChainValid(ArrayList<Block> blockchain){
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

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

			if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
}




