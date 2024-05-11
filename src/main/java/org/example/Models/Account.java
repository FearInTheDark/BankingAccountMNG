package org.example.Models;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity(name = "users_management")
@Table(name = "users_management")
public class Account {

    @Id
    @Column(name = "bankNo")
    private String id; // BankNo

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phoneNo")
    private String phoneNo;

//    birthDay - Date
    @Column(name = "birthDay")
    private String birthDay;

    @Column(name = "idCard")
    private String idCard;

    @Column(name = "validDate")
    private String validDate;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "card_Number")
    private String cardNumber;

    @Transient
    private Card card;

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
