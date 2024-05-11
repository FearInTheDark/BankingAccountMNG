package org.example.Views;

import org.example.Data.DB_Manager;
import org.example.Models.Account;
import org.example.Models.Card;
import org.example.Utils.ObjectToJson;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class SignUpSteps2 extends JFrame {
    private Account account;
    private Card card;
    private String PIN;
    private String bankNoTemp, cardNumberTemp, CVVTemp;
    private JLabel bankNoLabel;
    private JLabel cardNumberLabel;
    private JLayeredPane layeredPane;

    public SignUpSteps2(Account account)  {
        this.account = account;
        this.card = new Card();
        setInfo();
        genCard();
        init();

        setLayout(null);
        setIconImage(new ImageIcon("src/main/java/org/example/Views/icons/frame.png").getImage());
        setTitle("Sign Up Step 2");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyStrokes();
        add(layeredPane);
    }

    private void init() {

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1200, 700);
        layeredPane.setLayout(null);
        layeredPane.setOpaque(true);

        ImageIcon map = new ImageIcon("src/main/java/org/example/Views/icons/SignUp/map.png");
        Image mapImage = map.getImage();
        Image newMapImage = mapImage.getScaledInstance(1100, 609, Image.SCALE_SMOOTH);
        map = new ImageIcon(newMapImage);

        JLabel background = new JLabel(map);
        background.setBounds(0, 100, 1100, 609);
        background.setOpaque(true);


        JLabel title = new JLabel("Creating your ATM Card");
        title.setBounds(0, 0, 1200, 100);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
//        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.red));
        title.setForeground(Color.red);

        JLabel picLabel = new JLabel();
        picLabel.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/Steps2.png"));
        picLabel.setBounds(1200 - 509, 100, 509, 491);

        ImageIcon icon = new ImageIcon("src/main/java/org/example/Views/icons/SignUp/CardForm.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);

        JLabel cardLabel = new JLabel();
        cardLabel.setIcon(icon);
        cardLabel.setBounds(50, 60, 600, 600);
        cardLabel.setPreferredSize(new Dimension(600, 600));
//        cardLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));

        JPanel cardInfoPanel = new JPanel(null);
        cardInfoPanel.setBounds(150, 250, 450, 300);
        cardInfoPanel.setOpaque(false);

        ImageIcon VKUicon = new ImageIcon("src/main/java/org/example/Views/icons/frame.png");
        Image VKUimage = VKUicon.getImage();
        Image newVKUimage = VKUimage.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        VKUicon = new ImageIcon(newVKUimage);

        JLabel VKU = new JLabel();
        VKU.setIcon(VKUicon);
        VKU.setBounds(150, -10, 130, 130);
        VKU.setPreferredSize(new Dimension(130, 130));
        VKU.setHorizontalAlignment(JLabel.CENTER);
        VKU.setFont(new Font("Segoe UI", Font.BOLD, 40));
//        VKU.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.red));

        bankNoLabel = new JLabel("Bank No: " + this.bankNoTemp);
        bankNoLabel.setBounds(135, 130, 290, 20);
        bankNoLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        bankNoLabel.setForeground(Color.red);
        bankNoLabel.setHorizontalAlignment(JLabel.CENTER);
//        bankNoLabel.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        JLabel FROM_TO_LABEL = new JLabel("FROM    TO");
        FROM_TO_LABEL.setBounds(50, 160, 150, 30);
        FROM_TO_LABEL.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        FROM_TO_LABEL.setForeground(Color.black);
        FROM_TO_LABEL.setHorizontalAlignment(JLabel.LEFT);
//        FROM_TO_LABEL.setBorder(BorderFactory.createLineBorder(Color.red, 1));


        // Show date in format : MM/YY
        String beginDateShow = card.getCardIssueDate().substring(5, 7) + "/" + card.getCardIssueDate().substring(2, 4);
        String endDateShow = card.getCardExpiryDate().substring(5, 7) + "/" + card.getCardExpiryDate().substring(2, 4);

        JLabel beginDateLabel = new JLabel(beginDateShow);
        beginDateLabel.setBounds(50, 190, 75, 20);
        beginDateLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        beginDateLabel.setForeground(Color.black);
        beginDateLabel.setHorizontalAlignment(JLabel.RIGHT);
//        beginDateLabel.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        JLabel endDateLabel = new JLabel(endDateShow);
        endDateLabel.setBounds(125, 190, 75, 20);
        endDateLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        endDateLabel.setForeground(Color.black);
        endDateLabel.setHorizontalAlignment(JLabel.RIGHT);
//        endDateLabel.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        cardNumberLabel = new JLabel(this.cardNumberTemp);
        cardNumberLabel.setBounds(0, 212, 450, 50);
        cardNumberLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 40));
        cardNumberLabel.setForeground(Color.white);
        cardNumberLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel cvvLabel = new JLabel("CVV ");
        cvvLabel.setBounds(200, 174, 50, 40);
        cvvLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        cvvLabel.setForeground(Color.white);
        cvvLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel cvvNum = new JLabel(this.CVVTemp);
        cvvNum.setBounds(250, 174, 50, 40);
        cvvNum.setFont(new Font("JetBrains Mono", Font.ITALIC, 25));
        cvvNum.setForeground(Color.black);
        cvvNum.setHorizontalAlignment(JLabel.CENTER);

        cardInfoPanel.add(VKU);
        cardInfoPanel.add(bankNoLabel);
        cardInfoPanel.add(cardNumberLabel);
        cardInfoPanel.add(FROM_TO_LABEL);
        cardInfoPanel.add(beginDateLabel);
        cardInfoPanel.add(endDateLabel);
        cardInfoPanel.add(cvvLabel);
        cardInfoPanel.add(cvvNum);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBounds(0, 580, 600, 100);
        buttonsPanel.setOpaque(false);

        JLabel confirm = new JLabel();
        confirm.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/confirm.png"));
        confirm.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                confirm.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/confirm_focus.png"));
            }

            public void mouseExited(MouseEvent evt) {
                confirm.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/confirm.png"));
            }

            public void mouseClicked(MouseEvent evt) {
                setCardInfo(bankNoTemp, cardNumberTemp, CVVTemp);
                int cf = JOptionPane.showConfirmDialog(null, "Do you confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (cf == JOptionPane.YES_OPTION) {
                    PIN = JOptionPane.showInputDialog(null, "Your PIN: ", "PIN", JOptionPane.INFORMATION_MESSAGE);
                    while (!checkPIN(PIN)) {
                        JOptionPane.showMessageDialog(null, "PIN must be 4 digits!", "Error", JOptionPane.ERROR_MESSAGE);
                        PIN = JOptionPane.showInputDialog(null, "Your PIN: ", "PIN", JOptionPane.INFORMATION_MESSAGE);
                    }
                    card.setPin(PIN);
                    account.setCard(card);
                    JOptionPane.showMessageDialog(null, "Bank No: " + card.getBankNo() + "\nCard Number: " + card.getId() + "\nCVV: " + card.getCvv() + "\nPIN: " + PIN, "Card Info", JOptionPane.INFORMATION_MESSAGE);
                }

                System.out.println(ObjectToJson.convertToJson(account));
                System.out.println(ObjectToJson.convertToJson(card));

                if (DB_Manager.saveAccount(account) && DB_Manager.saveCard(card)) {
                    JOptionPane.showMessageDialog(null, "Successfully Save an Account and Card!");

                }
            }
        });

        JLabel genCard = new JLabel();
        genCard.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/genCard.png"));
        genCard.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                genCard.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/genCard_Focus.png"));
            }

            public void mouseExited(MouseEvent evt) {
                genCard.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/SignUp/genCard.png"));
            }

            public void mouseClicked(MouseEvent evt) {
                genCard();
                // repaint card info
                bankNoLabel.setText("Bank No: " + bankNoTemp);
                cardNumberLabel.setText(cardNumberTemp);
                cvvNum.setText(CVVTemp);

            }
        });

        buttonsPanel.add(confirm);
        buttonsPanel.add(genCard);

        layeredPane.add(title);
        layeredPane.add(picLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(cardLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttonsPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(cardInfoPanel, JLayeredPane.MODAL_LAYER);
    }

    private void setInfo() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        card.setCardIssueDate(currentDate.format(formatter));
        card.setCardExpiryDate(currentDate.plusYears(5).format(formatter));
        card.setBalance(0);
    }

    private void setCardInfo(String bankNo, String cardNumber, String CVV) {
        card.setBankNo(bankNo);
        card.setId(cardNumber);
        card.setCvv(CVV);
        card.setCardType("Debit");
        card.setCardStatus("Active");
        account.setCardNumber(cardNumber);
        account.setId(bankNo);
    }

    private void genCard() {
        this.bankNoTemp = randomBankNo();
        this.cardNumberTemp = randomCardNumber();
        this.CVVTemp = randomCVV();

        while (DB_Manager.checkExistAccount(bankNoTemp)) {
            this.bankNoTemp = randomBankNo();
        }
        while (DB_Manager.checkExistAccount(cardNumberTemp)) {
            this.cardNumberTemp = randomCardNumber();
        }
        System.out.println("New card has been generated!");
    }

    private String randomCVV() {
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append((int) (Math.random() * 10));
        }
        return cvv.toString();
    }

    private String randomBankNo() {
        StringBuilder bankNo = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            bankNo.append((int) (Math.random() * 10));
        }
        return bankNo.toString();
    }

    private String randomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append((int) (Math.random() * 10));
        }
        return cardNumber.toString();
    }

    private void addKeyStrokes() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("control W");
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "W");
        getRootPane().getActionMap().put("W", escapeAction);
    }

    private boolean checkPIN(String PIN) {
        if (PIN.length() != 4) {
            return false;
        }
        for (int i = 0; i < PIN.length(); i++) {
            if (!Character.isDigit(PIN.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
