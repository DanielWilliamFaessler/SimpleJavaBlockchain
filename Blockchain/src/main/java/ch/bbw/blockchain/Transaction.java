package ch.bbw.blockchain;
import java.security.*;
import ch.bbw.blockchain.StringHashUtil;
import java.util.ArrayList;
public class Transaction {
    public String transactionId; // this is also the hash of the transaction.
    public PublicKey sender; // senders address/public key.
    public PublicKey reciepient; // Recipients address/public key.
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calulateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return StringHashUtil.getHashForString(
                StringHashUtil.getStringFromKey(sender) +
                StringHashUtil.getStringFromKey(reciepient)
                + Float.toString(value)
                        + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringHashUtil.getStringFromKey(sender) + StringHashUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        signature = StringHashUtil.applyECDSASig(privateKey,data);
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifiySignature() {
        String data = StringHashUtil.getStringFromKey(sender) + StringHashUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        return StringHashUtil.verifyECDSASig(sender, data, signature);
    }

}
