package Models;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
@Entity(name = "atm_card_details")
@Table(name = "atm_card_details")
public class ModelCard implements Serializable {
    @Id
    @Column(name = "card_number")
    @SerializedName("CardNumber")
    private String id;

    @Column(name = "pin")
    @SerializedName("Pin")
    private String pin;

    @Column(name = "bankNo")
    @SerializedName("BankNo")
    private String bankNo;

    @Column(name = "card_status")
    @SerializedName("CardStatus")
    private String cardStatus;

    @Column(name = "card_type")
    @SerializedName("CardType")
    private String cardType;

    @Column(name = "card_issue_date")
    @SerializedName("CardIssueDate")
    private String cardIssueDate;

    @Column(name = "card_expiry_date")
    @SerializedName("CardExpiryDate")
    private String cardExpiryDate;

    @Column(name = "CVV")
    @SerializedName("CVV")
    private String cvv;

    @Column(name = "balance")
    @SerializedName("Balance")
    private int balance;

    public ModelCard() {
    }

    public ModelCard(String id, String pin, String bankNo, String cardStatus, String cardType, String cardIssueDate, String cardExpiryDate, String cvv, int balance) {
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
