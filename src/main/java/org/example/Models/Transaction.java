package org.example.Models;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity(name = "atm_transactions")
@Table(name = "atm_transactions")
public class Transaction {
    @Id
    @Column(name = "t_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Auto generate new value
    @SerializedName("Transaction ID")
    private String id;

    @Column(name = "bankNo_From")
    @SerializedName("BankNo From")
    private String bankNoFrom;

    @Column(name = "bankNo_To")
    @SerializedName("BankNo To")
    private String bankNoTo;

    @Column(name = "amount")
    @SerializedName("Amount")
    private int amount;

    @Column(name = "t_date")
    @SerializedName("Date")
    private String date;

    @Column(name = "letter")
    @SerializedName("Letter")
    private String letter;

    public Transaction() {
    }

    public Transaction(String bankNoFrom, String bankNoTo, int amount, String date, String letter) {
        this.bankNoFrom = bankNoFrom;
        this.bankNoTo = bankNoTo;
        this.amount = amount;
        this.date = date;
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "Transaction: \n" +
                "\tTransaction ID: " + id + "\n" +
                "\tBankNo From: " + bankNoFrom + "\n" +
                "\tBankNo To: " + bankNoTo + "\n" +
                "\tAmount: " + amount + "\n" +
                "\tDate: " + date + "\n" +
                "\tLetter: " + letter + "\n";
    }
}
