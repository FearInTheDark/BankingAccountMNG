package Models;

import java.net.Socket;
import java.util.HashMap;

public class SignedAccounts {
    public static HashMap<String, ModelAccount> signedAccounts = new HashMap<>();
    public static HashMap<String, ModelAccount> leftAccounts = new HashMap<>();
    public static HashMap<Socket, ModelAccount> clients = new HashMap<>();


    public static void addSignedAccount(ModelAccount modelAccount) {
        signedAccounts.put(modelAccount.getPhoneNo(), modelAccount);
    }

    public static void removeLeftAccount(String phoneNo) {
        leftAccounts.remove(phoneNo);
    }

    public static void addSignedAccountFromLeft(String phoneNo) {
        ModelAccount modelAccount = leftAccounts.get(phoneNo);
        signedAccounts.put(phoneNo, modelAccount);
        leftAccounts.remove(phoneNo);
    }

    public static void addLeftAccountFromSigned(String phoneNo) {
        ModelAccount modelAccount = signedAccounts.get(phoneNo);
        leftAccounts.put(phoneNo, modelAccount);
        signedAccounts.remove(phoneNo);
    }
}
