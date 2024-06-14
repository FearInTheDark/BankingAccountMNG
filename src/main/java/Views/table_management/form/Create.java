package Views.table_management.form;

import Models.ModelAccount;
import com.formdev.flatlaf.FlatClientProperties;
import raven.datetime.component.date.DatePicker;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Create extends JPanel {

    private DatePicker datePickerBirthDay, datePickerValidDate;
    private JLabel nameLabel, bankNoLabel, phoneNoLabel, birthDayLabel, idCardLabel;
    private JLabel validDateLabel, addressLabel, passwordLabel, cardNumberLabel;
    private JFormattedTextField txtBirthDay, txtValidDate;
    private JTextField txtName, txtBankNo, txtPhoneNo, txtIDCard, txtAddress, txtCardNumber;
    private JPasswordField txtPassword;

    public Create() {
        initComponents();
        datePickerBirthDay.setCloseAfterSelected(true);
        datePickerBirthDay.setEditor(txtBirthDay);
        datePickerValidDate.setCloseAfterSelected(true);
        datePickerValidDate.setEditor(txtValidDate);

    }

    private void initComponents() {

        datePickerBirthDay = new DatePicker();
        datePickerValidDate = new DatePicker();
        nameLabel = new JLabel();
        bankNoLabel = new JLabel();
        phoneNoLabel = new JLabel();
        birthDayLabel = new JLabel();
        idCardLabel = new JLabel();
        validDateLabel = new JLabel();
        addressLabel = new JLabel();
        passwordLabel = new JLabel();
        cardNumberLabel = new JLabel();

        txtName = new JTextField();
        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Account's full name");

        txtBankNo = new JTextField();
        txtBankNo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Must be 14 digits)");

        txtPhoneNo = new JTextField();
        txtPhoneNo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Must be 10 digits)");

        txtBirthDay = new JFormattedTextField();
        txtIDCard = new JTextField();
        txtIDCard.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Must be 12 digits)");

        txtValidDate = new JFormattedTextField();
        txtAddress = new JTextField();
        txtAddress.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Account's address");

        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Account's password");
        txtPassword.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton:true;"
                        + "showCapsLock:true");

        txtCardNumber = new JTextField();
        txtCardNumber.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Must be 16 digits)");

        nameLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        nameLabel.setText("Name");

        bankNoLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        bankNoLabel.setText("Bank No");

        phoneNoLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        phoneNoLabel.setText("Phone No");

        birthDayLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        birthDayLabel.setText("Birth Day");

        idCardLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        idCardLabel.setText("ID Card");

        validDateLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        validDateLabel.setText("Valid Date");

        addressLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        addressLabel.setText("Address");

        passwordLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        passwordLabel.setText("Password");

        cardNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        cardNumberLabel.setText("Card Number");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        setBorder(BorderFactory.createTitledBorder("Account Information"));
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bankNoLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(phoneNoLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(birthDayLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(idCardLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(validDateLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(addressLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cardNumberLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBankNo, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhoneNo, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBirthDay, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIDCard, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtValidDate, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCardNumber, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(bankNoLabel)
                                .addComponent(txtBankNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(phoneNoLabel)
                                .addComponent(txtPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(birthDayLabel)
                                .addComponent(txtBirthDay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(idCardLabel)
                                .addComponent(txtIDCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(validDateLabel)
                                .addComponent(txtValidDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(addressLabel)
                                .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(passwordLabel)
                                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cardNumberLabel)
                                .addComponent(txtCardNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
        );
    }

    public void loadData(ModelAccount data) {
        if (data != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txtName.setText(data.getFullName());
            txtBankNo.setText(data.getId());
            txtPhoneNo.setText(data.getPhoneNo());
            txtBirthDay.setText(sdf.format(data.getBirthDay()));
            txtIDCard.setText(data.getIdCard());
            txtValidDate.setText(sdf.format(data.getValidDate()));
            txtAddress.setText(data.getAddress());
            txtPassword.setText(data.getPassword());
            txtCardNumber.setText(data.getCardNumber());
        }
    }

    public ModelAccount getData() throws ParseException {
        SimpleDateFormat raw = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String name = txtName.getText();
        String bankNo = txtBankNo.getText();
        String phoneNo = txtPhoneNo.getText();
        String birthDay = sdf.format(raw.parse(txtBirthDay.getText()));
        String idCard = txtIDCard.getText();
        String validDate = sdf.format(raw.parse(txtValidDate.getText()));
        String address = txtAddress.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String cardNumber = txtCardNumber.getText();
        return new ModelAccount(bankNo, phoneNo, name, birthDay, idCard, validDate, address, password, cardNumber);
    }
}
