package Application.Bank;

import java.time.LocalDate;
import java.util.HashMap;

import Utils.Randomiser.GenerateReferenceNumber;

import java.util.Map;

// Bank manages individual BankAccounts, other banks franchises can extend bank class
public class Bank {

    private final Map<String, BankAccount> accounts; // Keyed by some unique identifier, e.g. an account number.

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public String createAccount(String ownerName) {
        String accountNumber = new GenerateReferenceNumber(LocalDate.now().getMonth(), 18).generateReferenceNumber('U');
        BankAccount account = new BankAccount(accountNumber, ownerName);
        accounts.put(accountNumber, account);
        return this.accounts.get(accountNumber).ACCOUNT_NUMBER;
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean removeAccount(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            accounts.remove(accountNumber);
            return true;
        }
        else return false;
    }

    public void printAllAccountsSummary() {
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            String id = entry.getKey();
            BankAccount acc = entry.getValue();
            System.out.println("Account ID: " + id
                    + " | Owner: " + acc.ACCOUNT_HOLDER
                    + " | Balance: " + acc.getFormattedBalance());
        }
    }
}
