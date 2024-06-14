package Models;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Data
@Entity(name = "atm_transactions")
@Table(name = "atm_transactions")
public class ModelTransaction {
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
    private Date date;

    @Column(name = "letter")
    @SerializedName("Letter")
    private String letter;

    public ModelTransaction() {
    }

    public ModelTransaction(String bankNoFrom, String bankNoTo, int amount, String date, String letter) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.bankNoFrom = bankNoFrom;
        this.bankNoTo = bankNoTo;
        this.amount = amount;
        try {
            this.date = (date == null) ? new Date() : formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
