package Models;

import java.util.HashMap;

public class SignedAccounts {
    public static HashMap<String, Account> signedAccounts = new HashMap<>();
    public static HashMap<String, Account> leftAccounts = new HashMap<>();

    public static void addSignedAccount(Account account) {
        signedAccounts.put(account.getPhoneNo(), account);
    }

    public static void removeLeftAccount(String phoneNo) {
        leftAccounts.remove(phoneNo);
    }

    public static void addSignedAccountFromLeft(String phoneNo) {
        Account account = leftAccounts.get(phoneNo);
        signedAccounts.put(phoneNo, account);
        leftAccounts.remove(phoneNo);
    }

    public static void addLeftAccountFromSigned(String phoneNo) {
        Account account = signedAccounts.get(phoneNo);
        leftAccounts.put(phoneNo, account);
        signedAccounts.remove(phoneNo);
    }
}
