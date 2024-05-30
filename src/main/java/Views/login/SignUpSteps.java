package Views.login;

import Data.DB_Manager;
import Models.Account;
import com.formdev.flatlaf.FlatClientProperties;
import raven.datetime.component.date.DatePicker;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.IOException;
public class SignUpSteps extends JFrame {
    private final Account account;
    private JLabel guideLabel;
    private JTextField nameField, idCardField, addressField;
    private JFormattedTextField validDateField, birthdayField;
    private JLayeredPane mainPanel;
    private MaskFormatter mask;

    public SignUpSteps(String phone, String password) {
        account = new Account();
        account.setPhoneNo(phone);
        account.setPassword(password);

        customizeMask();
        init();
        addKeyStrokes();

        setSize(1200, 700);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        add(mainPanel);
    }

    private void init() {
        ImageIcon icon = new ImageIcon("src/main/resources/icons/SignUp/Picture1.png");
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        JLabel back = new JLabel(scaledIcon);
        back.setBounds(600, 100, 600, 600);
        back.setVisible(true);

        mainPanel = new JLayeredPane();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1200, 700);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setOpaque(true);

        JLabel titleLabel = new JLabel("SIGN UP NEW ACCOUNT");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 50));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
        titleLabel.setBounds(0, 0, 1200, 100);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.CENTER);

        guideLabel = new JLabel("Please fill in the following information");
        guideLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        guideLabel.setForeground(Color.BLACK);
        guideLabel.setBounds(600, 100, 600, 50);
        guideLabel.setHorizontalAlignment(JLabel.CENTER);
        guideLabel.setVerticalAlignment(JLabel.CENTER);
        guideLabel.setHorizontalTextPosition(JLabel.LEFT);
        guideLabel.setVerticalTextPosition(JLabel.CENTER);

        JLabel guideLabel2 = new JLabel("You are Singing up for a new account");
        guideLabel2.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        guideLabel2.setForeground(Color.BLACK);
        guideLabel2.setBounds(0, 575, 600, 50);
        guideLabel2.setHorizontalAlignment(JLabel.CENTER);
        guideLabel2.setVerticalAlignment(JLabel.CENTER);
        guideLabel2.setHorizontalTextPosition(JLabel.LEFT);
        guideLabel2.setVerticalTextPosition(JLabel.CENTER);

        JLabel picLabel = new JLabel();
        picLabel.setBounds(0, 150, 564, 397);
        picLabel.setIcon(new ImageIcon("src/main/resources/icons/SignUp/sign.jpg"));

        JLabel name = new JLabel("FULL NAME");
        name.setFont(new Font("Segoe UI", Font.BOLD, 15));
        name.setBounds(600, 150, 200, 50);
        name.setForeground(Color.RED);

        nameField = new JTextField();
        nameField.setBounds(600, 200, 400, 50);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Please fill in your full name");

        // Birthday
        JLabel birthday = new JLabel("BIRTHDAY");
        birthday.setFont(new Font("Segoe UI", Font.BOLD, 15));
        birthday.setBounds(1000, 150, 200, 50);
        birthday.setForeground(Color.RED);

        birthdayField = new JFormattedTextField();
        birthdayField.setBounds(1020, 200, 140, 50);
        birthdayField.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JLabel idCard = new JLabel("ID CARD");
        idCard.setFont(new Font("Segoe UI", Font.BOLD, 15));
        idCard.setBounds(600, 250, 200, 50);
        idCard.setForeground(Color.RED);

        idCardField = new JTextField();
        idCardField.setBounds(600, 300, 280, 50);
        idCardField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        idCardField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Please fill in your ID card");

        JLabel validDate = new JLabel("VALID DATE");
        validDate.setFont(new Font("Segoe UI", Font.BOLD, 15));
        validDate.setBounds(900, 250, 200, 50);
        validDate.setForeground(Color.RED);

        validDateField = new JFormattedTextField();
        validDateField.setBounds(900, 300, 140, 50);
        validDateField.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        // Address
        JLabel address = new JLabel("ADDRESS");
        address.setFont(new Font("Segoe UI", Font.BOLD, 15));
        address.setBounds(600, 350, 200, 50);
        address.setForeground(Color.RED);

        addressField = new JTextField();
        addressField.setBounds(600, 400, 400, 50);
        addressField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        addressField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Please fill in your address");

        JLabel phoneLabel = new JLabel("Your phone number: " + account.getPhoneNo());
        phoneLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        phoneLabel.setBounds(600, 450, 400, 50);
        phoneLabel.setForeground(Color.BLACK);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonsPanel.setBounds(600, 550, 500, 100);
        buttonsPanel.setBackground(null);
        buttonsPanel.setOpaque(false);

        JButton next = new JButton("NEXT");
        next.setFont(new Font("Segoe UI", Font.BOLD, 20));
        next.setForeground(Color.WHITE);
        next.setBackground(Color.RED);
        next.setPreferredSize(new Dimension(150, 50));
        next.addActionListener(e -> {
            if (checkValidInfo()) {
                if (DB_Manager.checkExistAccount(idCardField.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "ID card already exists");
                    idCardField.requestFocus();
                    return;
                }
                account.setFullName(nameField.getText());
                account.setIdCard(idCardField.getText());
                account.setAddress(addressField.getText());
                account.setBirthDay(convertDate(birthdayField.getText()));
                account.setValidDate(convertDate(validDateField.getText()));
                new SignUpSteps2(account);
                dispose();
            } else {
                guideLabel.setText("Please correct your information");
                guideLabel.setForeground(Color.RED);
            }
        });

        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.RED);
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> {
            try {
                new LogIn_Frame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(next);


        mainPanel.add(back, JLayeredPane.DEFAULT_LAYER);
        mainPanel.add(titleLabel, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(picLabel, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(guideLabel, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(guideLabel2, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(name, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(nameField, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(idCard, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(idCardField, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(validDate, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(validDateField, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(address, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(addressField, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(birthday, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(birthdayField, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(phoneLabel, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(buttonsPanel, JLayeredPane.PALETTE_LAYER);


        mainPanel.setVisible(true);

        DatePicker datePicker = new DatePicker();
        datePicker.setUsePanelOption(true);
        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
        datePicker.setEditor(birthdayField);
        datePicker.setEditor(validDateField);

    }

    private void addKeyStrokes() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("control W");
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "W");
        getRootPane().getActionMap().put("W", escapeAction);
    }

    private void customizeMask() {
        mask = null;
        try {
            mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
        } catch (Exception e) {
            System.out.println("Error in mask");
        }
    }

    private boolean checkValidInfo() {
        // Check valid info or show JOptionPane
        if (nameField.getText().isEmpty() || nameField.getText().equals("Please fill in your full name")) {
            JOptionPane.showMessageDialog(null, "Please fill in your full name");
            nameField.requestFocus();
            return false;
        }
        if (birthdayField.getText().isEmpty() || birthdayField.getText().equals("__/__/____")) {
            JOptionPane.showMessageDialog(null, "Please fill in your birthday");
            birthdayField.requestFocus();
            return false;
        }
        // ID card length must be 12
        if (idCardField.getText().length() != 12) {
            JOptionPane.showMessageDialog(null, "ID card must be 12 digits");
            idCardField.requestFocus();
            return false;
        }
        if (validDateField.getText().isEmpty() || validDateField.getText().equals("__/__/____")) {
            JOptionPane.showMessageDialog(null, "Please fill in your valid date");
            validDateField.requestFocus();
            return false;
        }
        if (addressField.getText().isEmpty() || addressField.getText().equals("Your address")) {
            JOptionPane.showMessageDialog(null, "Please fill in your address");
            addressField.requestFocus();
            return false;
        }
        if (checkInvalidDate(birthdayField.getText())) {
            JOptionPane.showMessageDialog(null, "Birthday must be valid");
            birthdayField.requestFocus();
            return false;
        }
        if (checkInvalidDate(validDateField.getText())) {
            JOptionPane.showMessageDialog(null, "Valid date must be valid");
            validDateField.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkInvalidDate(String date) {
        String[] dateArr = date.split("/");
        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]);
        if (day < 1 || day > 31) {
            return true;
        }
        if (month < 1 || month > 12) {
            return true;
        }
        if (month == 2) {
            if (day > 29) {
                return true;
            }
        }
        return year < 1900 || year > 2050;
    }

    private String convertDate(String date) {
        String[] dateArr = date.split("/");
        String day = dateArr[0];
        String month = dateArr[1];
        String year = dateArr[2];
        return year + "-" + month + "-" + day;
    }
}



