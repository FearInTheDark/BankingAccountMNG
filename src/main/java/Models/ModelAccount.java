package Models;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Data
@Entity(name = "users_management")
@Table(name = "users_management")
public class ModelAccount implements Serializable {

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
    private Date birthDay;

    @Column(name = "idCard")
    @SerializedName("IDCard")
    private String idCard;

    @Column(name = "validDate")
    @SerializedName("ValidDate")
    private Date validDate;

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
    private transient ModelCard modelCard;

    @Transient
    private transient ImageIcon avatar;

    public ModelAccount() {

    }

    public ModelAccount(String id, String fullName, String phoneNo, String birthDay, String idCard, String validDate, String address, String password, String cardNumber, ModelCard modelCard) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.birthDay = (!birthDay.isEmpty() ? sdf.parse(birthDay) : null);
        this.idCard = idCard;
        this.validDate = (!validDate.isEmpty() ? sdf.parse(validDate) : null);
        this.address = address;
        this.password = password;
        this.cardNumber = cardNumber;
        this.modelCard = modelCard;
    }

    public ModelAccount(String id, String fullName, String phoneNo, String birthDay, String idCard, String validDate, String address, String password, String cardNumber) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.birthDay = sdf.parse(birthDay);
        this.idCard = idCard;
        this.validDate = sdf.parse(validDate);
        this.address = address;
        this.password = password;
        this.cardNumber = cardNumber;
    }

    public Object[] toTableRow(int rowNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new Object[]{false, rowNum, id, phoneNo, fullName, sdf.format(birthDay), idCard, sdf.format(validDate), address, password, cardNumber};
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

    public synchronized void transferTo(ModelCard receiverCard, int amountTemp) {
        if (modelCard.getBalance() < amountTemp) {
            JOptionPane.showMessageDialog(null, "Your balance is not enough to transfer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        receiverCard.setBalance(receiverCard.getBalance() + amountTemp);
        modelCard.setBalance(modelCard.getBalance() - amountTemp);

    }
}
