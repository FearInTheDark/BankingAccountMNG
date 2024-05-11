package org.example.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity(name = "atm_card_details")
@Table(name = "atm_card_details")
public class Card {
    @Id
    @Column(name = "card_number")
    private String id;

    @Column(name = "pin")
    private String pin;

    @Column(name = "bankNo")
    private String bankNo;

    @Column(name = "card_status")
    private String cardStatus;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_issue_date")
    private String cardIssueDate;

    @Column(name = "card_expiry_date")
    private String cardExpiryDate;

    @Column(name = "CVV")
    private String cvv;

    @Column(name = "balance")
    private int balance;

    public Card() {
    }

    public Card(String id, String pin, String bankNo, String cardStatus, String cardType, String cardIssueDate, String cardExpiryDate, String cvv, int balance) {
        this.id = id;
        this.pin = pin;
        this.bankNo = bankNo;
        this.cardStatus = cardStatus;
        this.cardType = cardType;
        this.cardIssueDate = cardIssueDate;
        this.cardExpiryDate = cardExpiryDate;
        this.cvv = cvv;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Card: \n" +
                "\tCard Number: " + id + "\n" +
                "\tPin: " + pin + "\n" +
                "\tBank Number: " + bankNo + "\n" +
                "\tCard Status: " + cardStatus + "\n" +
                "\tCard Type: " + cardType + "\n" +
                "\tCard Issue Date: " + cardIssueDate + "\n" +
                "\tCard Expiry Date: " + cardExpiryDate + "\n" +
                "\tCVV: " + cvv + "\n" +
                "\tBalance: " + balance + "\n";
    }
}
