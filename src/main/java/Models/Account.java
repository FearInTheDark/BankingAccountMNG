package Models;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
@Data
@Entity(name = "users_management")
@Table(name = "users_management")
public class Account {

    @Id
    @Column(name = "bankNo")
    @SerializedName("BankNo")
    private String id; // BankNo

    @Column(name = "fullName")
    @SerializedName("FullName")
    private String fullName;

    @Column(name = "phoneNo")
    @SerializedName("PhoneNo")
    private String phoneNo;

    @Column(name = "birthDay")
    @SerializedName("BirthDay")
    private String birthDay;

    @Column(name = "idCard")
    @SerializedName("IDCard")
    private String idCard;

    @Column(name = "validDate")
    @SerializedName("ValidDate")
    private String validDate;

    @Column(name = "address")
    @SerializedName("Address")
    private String address;

    @Column(name = "password")
    @SerializedName("Password")
    private String password;

    @Column(name = "card_Number")
    @SerializedName("CardNumber")
    private String cardNumber;

    @Transient
    private transient Card card;

    @Transient
    private transient ImageIcon avatar;

    public Account() {

    }

    public Account(String id, String fullName, String phoneNo, String birthDay, String idCard, String validDate, String address, String password, String cardNumber, Card card) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.birthDay = birthDay;
        this.idCard = idCard;
        this.validDate = validDate;
        this.address = address;
        this.password = password;
        this.cardNumber = cardNumber;
        this.card = card;
    }

    public synchronized Account transferTo(Account account) {
        return this;
    }

    public void setAvatar(ImageIcon avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Account: \n" +
                "\tBankNo: " + id + "\n" +
                "\tFull Name: " + fullName + "\n" +
                "\tPhone Number: " + phoneNo + "\n" +
                "\tBirth Day: " + birthDay + "\n" +
                "\tID Card: " + idCard + "\n" +
                "\tValid Date: " + validDate + "\n" +
                "\tAddress: " + address + "\n" +
                "\tPassword: " + password + "\n" +
                "\tCard Number: " + cardNumber + "\n";
//                "\tCard: " + card + "\n";
    }
}
