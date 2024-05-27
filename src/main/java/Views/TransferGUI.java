package Views;

import Models.Account;
import org.jdesktop.swingx.JXFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TransferGUI extends JXFrame {

    public TransferGUI() {
    }

    public JLayeredPane getTransferGUI(Account account, int width, int height) {
        JLayeredPane transferGUI = new JLayeredPane();
        transferGUI.setBounds(0, 0, width, height);
        transferGUI.setLayout(null);

        JLayeredPane from = new JLayeredPane();
        from.setBounds(50, 50, 351, 351);
        from.setLayout(null);
        from.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JLabel fromLabel = new JLabel();
        ImageIcon fromIcon = new ImageIcon("src/icons/InApp/transferFrom.png");
        Icon fromIconResize = new ImageIcon(fromIcon.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        fromLabel.setIcon(fromIconResize);
        fromLabel.setBounds(0, 0, 350, 350);

        JLabel fromTitle = new JLabel("From");
        fromTitle.setBounds(50, 10, 250, 50);
        fromTitle.setFont(new Font("Arial", Font.BOLD, 30));
        fromTitle.setForeground(Color.BLACK);
        fromTitle.setVerticalTextPosition(JLabel.CENTER);
        fromTitle.setHorizontalTextPosition(JLabel.CENTER);
        fromTitle.setHorizontalAlignment(JLabel.CENTER);
        fromTitle.setVerticalAlignment(JLabel.CENTER);

        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/icons/InApp/frame.png");
        Icon logoIconResize = new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        logo.setIcon(logoIconResize);
        logo.setBounds(125, 60, 100, 100);

        JLabel fullName = new JLabel(account.getFullName().toUpperCase());
        fullName.setBounds(25, 170, 300, 30);
        fullName.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        fullName.setForeground(Color.BLACK);
        fullName.setVerticalTextPosition(JLabel.CENTER);
        fullName.setHorizontalTextPosition(JLabel.CENTER);
        fullName.setHorizontalAlignment(JLabel.CENTER);
        fullName.setVerticalAlignment(JLabel.CENTER);

        String bankNum = "<html><body style='text-align: center'>Bank Number: <br>" + account.getId() + "</body></html>";

        JLabel bankNumber = new JLabel(bankNum);
        bankNumber.setBounds(50, 210, 250, 40);
        bankNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        bankNumber.setForeground(Color.BLACK);
        bankNumber.setVerticalTextPosition(JLabel.CENTER);
        bankNumber.setHorizontalTextPosition(JLabel.CENTER);
        bankNumber.setHorizontalAlignment(JLabel.CENTER);
        bankNumber.setVerticalAlignment(JLabel.CENTER);

        JLabel currentBalance = new JLabel("Current Balance");
        currentBalance.setBounds(50, 260, 250, 25);
        currentBalance.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        currentBalance.setForeground(Color.BLACK);
        currentBalance.setVerticalTextPosition(JLabel.CENTER);
        currentBalance.setHorizontalTextPosition(JLabel.CENTER);
        currentBalance.setHorizontalAlignment(JLabel.CENTER);
        currentBalance.setVerticalAlignment(JLabel.CENTER);


        String seperatedBalance = String.format("%,d", account.getCard().getBalance());

        JLabel balanceFrom = new JLabel(seperatedBalance);
        balanceFrom.setBounds(50, 285, 250, 50);
        balanceFrom.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        balanceFrom.setForeground(Color.BLACK);
        balanceFrom.setVerticalTextPosition(JLabel.CENTER);
        balanceFrom.setHorizontalTextPosition(JLabel.CENTER);
        balanceFrom.setHorizontalAlignment(JLabel.CENTER);
        balanceFrom.setVerticalAlignment(JLabel.CENTER);


        from.add(fromLabel, JLayeredPane.DEFAULT_LAYER);
        from.add(fromTitle, JLayeredPane.PALETTE_LAYER);
        from.add(logo, JLayeredPane.PALETTE_LAYER);
        from.add(fullName, JLayeredPane.PALETTE_LAYER);
        from.add(bankNumber, JLayeredPane.PALETTE_LAYER);
        from.add(currentBalance, JLayeredPane.PALETTE_LAYER);
        from.add(balanceFrom, JLayeredPane.PALETTE_LAYER);

        JLabel arrow = new JLabel();
        ImageIcon arrowIcon = new ImageIcon("src/icons/InApp/transferArrow.png");
        Icon arrowIconResize = new ImageIcon(arrowIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        arrow.setIcon(arrowIconResize);
        arrow.setBounds(400, 175, 100, 100);


        JLayeredPane to = new JLayeredPane();
        to.setBounds(500, 50, 351, 351);
        to.setLayout(null);
        JLabel toLabel = new JLabel();
        ImageIcon toIcon = new ImageIcon("src/icons/InApp/transferFrom.png");
        Icon toIconResize = new ImageIcon(toIcon.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        toLabel.setIcon(toIconResize);
        toLabel.setBounds(0, 0, 350, 350);

        JLabel toTitle = new JLabel("To");
        toTitle.setBounds(50, 10, 250, 50);
        toTitle.setFont(new Font("Arial", Font.BOLD, 30));
        toTitle.setForeground(Color.BLACK);
        toTitle.setVerticalTextPosition(JLabel.CENTER);
        toTitle.setHorizontalTextPosition(JLabel.CENTER);
        toTitle.setHorizontalAlignment(JLabel.CENTER);
        toTitle.setVerticalAlignment(JLabel.CENTER);

        JLabel logoTo = new JLabel();
        ImageIcon logoToIcon = new ImageIcon("src/icons/InApp/frame.png");
        Icon logoToIconResize = new ImageIcon(logoToIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        logoTo.setIcon(logoToIconResize);
        logoTo.setBounds(125, 60, 100, 100);
//        logoTo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Full Name under logo Formatted BOLD UPPER CASE ITALIC Black color
        String receiverName = "Full Name";
        JLabel fullNameTo = new JLabel(receiverName.toUpperCase());
        fullNameTo.setBounds(25, 170, 300, 30);
        fullNameTo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        fullNameTo.setForeground(Color.BLACK);
        fullNameTo.setVerticalTextPosition(JLabel.CENTER);
        fullNameTo.setHorizontalTextPosition(JLabel.CENTER);
        fullNameTo.setHorizontalAlignment(JLabel.CENTER);
        fullNameTo.setVerticalAlignment(JLabel.CENTER);

        // Bank number: #bankNo in 2 lines Formatted Pain Black color
        String bankNumTo = "<html><body style='text-align: center'>Bank Number: <br>Bank Number</body></html>";

        JLabel bankNumberTo = new JLabel(bankNumTo);
        bankNumberTo.setBounds(50, 210, 250, 40);
        bankNumberTo.setFont(new Font("Arial", Font.PLAIN, 19));
        bankNumberTo.setForeground(Color.BLACK);
        bankNumberTo.setVerticalTextPosition(JLabel.CENTER);
        bankNumberTo.setHorizontalTextPosition(JLabel.CENTER);
        bankNumberTo.setHorizontalAlignment(JLabel.CENTER);
        bankNumberTo.setVerticalAlignment(JLabel.CENTER);
//        bankNumberTo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Label "Curren \n Balance" Formatted BOLD UPPER CASE ITALIC Black color
        JLabel currentBalanceTo = new JLabel("Current Balance");
        currentBalanceTo.setBounds(50, 260, 250, 25);
        currentBalanceTo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        currentBalanceTo.setForeground(Color.BLACK);
        currentBalanceTo.setVerticalTextPosition(JLabel.CENTER);
        currentBalanceTo.setHorizontalTextPosition(JLabel.CENTER);
        currentBalanceTo.setHorizontalAlignment(JLabel.CENTER);
        currentBalanceTo.setVerticalAlignment(JLabel.CENTER);

        // Balance: + #balance Formatted BOLD UPPER CASE ITALIC Black color
        JLabel balanceTo = new JLabel("+ 0");
        balanceTo.setBounds(50, 285, 250, 50);
        balanceTo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        balanceTo.setForeground(Color.BLACK);
        balanceTo.setVerticalTextPosition(JLabel.CENTER);
        balanceTo.setHorizontalTextPosition(JLabel.CENTER);
        balanceTo.setHorizontalAlignment(JLabel.CENTER);
        balanceTo.setVerticalAlignment(JLabel.CENTER);

        to.add(toLabel, JLayeredPane.DEFAULT_LAYER);
        to.add(toTitle, JLayeredPane.PALETTE_LAYER);
        to.add(logoTo, JLayeredPane.PALETTE_LAYER);
        to.add(fullNameTo, JLayeredPane.PALETTE_LAYER);
        to.add(bankNumberTo, JLayeredPane.PALETTE_LAYER);
        to.add(currentBalanceTo, JLayeredPane.PALETTE_LAYER);
        to.add(balanceTo, JLayeredPane.PALETTE_LAYER);

        JLabel enterAccount = new JLabel("Enter the recipient account here: ");
        // Under logo Formatted BOLD UPPER CASE ITALIC Black color
        enterAccount.setBounds(50, 430, 800, 50);
        enterAccount.setFont(new Font("Arial", Font.PLAIN | Font.ITALIC, 15));
        enterAccount.setForeground(Color.BLACK);
        enterAccount.setVerticalTextPosition(JLabel.CENTER);

        JTextField bankNoSearch = new JTextField();
        bankNoSearch.setBounds(50, 480, 700, 50);
        String please = "Enter the recipient account here";
        bankNoSearch.setText(please);
        bankNoSearch.setFont(new Font("Arial", Font.PLAIN | Font.ITALIC, 13));
        bankNoSearch.setForeground(Color.GRAY);
        bankNoSearch.setBorder(null);
        bankNoSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
                if (bankNoSearch.getText().length() >= 16) {
                    e.consume();
                }
                // If key == Enter then set Text to balanceTo with color = gray and set bankNoSearch to receiverBankNo and set receiverName to fullNameTo
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                }

            }
        });
//        bankNoSearch.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        bankNoSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (bankNoSearch.getText().equals("Enter the recipient account here"))
                    bankNoSearch.setText("");
                bankNoSearch.setFont(new Font("Arial", Font.PLAIN, 25));
                bankNoSearch.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (bankNoSearch.getText().isEmpty()) {
                    bankNoSearch.setText(please);
                    bankNoSearch.setFont(new Font("Arial", Font.PLAIN | Font.ITALIC, 13));
                    bankNoSearch.setForeground(Color.GRAY);
                }
            }
        });

        JLabel search = new JLabel();
        ImageIcon searchIcon = new ImageIcon("src/icons/InApp/search.png");
        Icon searchIconResize = new ImageIcon(searchIcon.getImage().getScaledInstance(52, 52, Image.SCALE_DEFAULT));
        search.setIcon(searchIconResize);
        search.setBounds(770, 480, 52, 52);
//        search.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        JLabel amount = new JLabel("Enter the amount: ");

        amount.setBounds(50, 550, 230, 50);
        amount.setFont(new Font("Arial", Font.PLAIN, 25));
        amount.setForeground(Color.BLACK);
        amount.setVerticalTextPosition(JLabel.CENTER);
//        amount.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JTextField amountInput = new JTextField();
        amountInput.setBounds(300, 550, 400, 50);
        amountInput.setText("Enter the amount");
        amountInput.setForeground(Color.GRAY);
        amountInput.setFont(new Font("Arial", Font.PLAIN | Font.ITALIC, 13));
        amountInput.setBorder(null);
        amountInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        amountInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (amountInput.getText().equals("Enter the amount"))
                    amountInput.setText("");
                amountInput.setFont(new Font("Arial", Font.PLAIN, 25));
                amountInput.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (amountInput.getText().isEmpty()) {
                    amountInput.setText("Enter the amount");
                    amountInput.setFont(new Font("Arial", Font.PLAIN, 13));
                    amountInput.setForeground(Color.GRAY);
                }
            }
        });
//        amountInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        String[] currency = {"VND", "USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "HKD"};
        JComboBox<String> currencyList = new JComboBox<>(currency);
        currencyList.setBounds(720, 550, 100, 50);
        currencyList.setFont(new Font("Arial", Font.PLAIN, 25));
        currencyList.setForeground(Color.BLACK);
        currencyList.setBackground(Color.WHITE);
        currencyList.setBorder(null);
        currencyList.addActionListener(e -> {
            double amountTemp = (amountInput.getText().isEmpty()) ? 0 : Double.parseDouble(amountInput.getText());
            switch (currencyList.getSelectedIndex()) {
                case 0 -> {
                    String seperatedBalance0 = String.format("%.2f", amountTemp);
                    balanceTo.setText("+ " + seperatedBalance0);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 1 -> {
                    String seperatedBalance1 = String.format("%.2f", amountTemp * 23000);
                    balanceTo.setText("+ " + seperatedBalance1);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 2 -> {
                    String seperatedBalance2 = String.format("%.2f", amountTemp * 27000);
                    balanceTo.setText("+ " + seperatedBalance2);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 3 -> {
                    String seperatedBalance3 = String.format("%.2f", amountTemp * 210);
                    balanceTo.setText("+ " + seperatedBalance3);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 4 -> {
                    String seperatedBalance4 = String.format("%.2f", amountTemp * 30000);
                    balanceTo.setText("+ " + seperatedBalance4);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 5 -> {
                    String seperatedBalance5 = String.format("%.2f", amountTemp * 17000);
                    balanceTo.setText("+ " + seperatedBalance5);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 6 -> {
                    String seperatedBalance6 = String.format("%.2f", amountTemp * 18000);
                    balanceTo.setText("+ " + seperatedBalance6);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 7 -> {
                    String seperatedBalance7 = String.format("%.2f", amountTemp * 25000);
                    balanceTo.setText("+ " + seperatedBalance7);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 8 -> {
                    String seperatedBalance8 = String.format("%.2f", amountTemp * 3500);
                    balanceTo.setText("+ " + seperatedBalance8);
                    balanceTo.setForeground(Color.GRAY);
                }
                case 9 -> {
                    String seperatedBalance9 = String.format("%.2f", amountTemp * 3000);
                    balanceTo.setText("+ " + seperatedBalance9);
                    balanceTo.setForeground(Color.GRAY);
                }
            }
        });
//        currencyList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel letter = new JLabel();
        ImageIcon letterIcon = new ImageIcon("src/icons/InApp/letter.png");
        Icon letterIconResize = new ImageIcon(letterIcon.getImage().getScaledInstance(800, 180, Image.SCALE_DEFAULT));
        letter.setIcon(letterIconResize);
        letter.setBounds(50, 610, 800, 180);

        JTextArea letterInput = new JTextArea();
        letterInput.setBounds(70, 630, 760, 140);
        letterInput.setFont(new Font("Arial", Font.PLAIN, 25));
        letterInput.setForeground(Color.BLACK);
        letterInput.setBackground(Color.WHITE);
        letterInput.setBorder(null);
        letterInput.setLineWrap(true);
        letterInput.setWrapStyleWord(true);

        JPanel transferButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        transferButton.setBounds(50, 800, 800, 60);
        transferButton.setBackground(null);
        transferButton.setOpaque(false);

        JLabel transfer = new JLabel();
        transfer.setIcon(new ImageIcon("src/icons/InApp/transferButton.png"));
        transfer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int amountTemp = (amountInput.getText().isEmpty()) ? 0 : Integer.parseInt(amountInput.getText());
                switch (currencyList.getSelectedIndex()) {
                    case 0 -> {
                    }
                    case 1 -> amountTemp *= 23000;
                    case 2 -> amountTemp *= 27000;
                    case 3 -> amountTemp *= 210;
                    case 4 -> amountTemp *= 30000;
                    case 5 -> amountTemp *= 17000;
                    case 6 -> amountTemp *= 18000;
                    case 7 -> amountTemp *= 25000;
                    case 8 -> amountTemp *= 3500;
                    case 9 -> amountTemp *= 3000;
                }
                String letterTemp = letterInput.getText();
                String date = getCurrentDate();
                System.out.println("Amount: " + amountTemp + " " + letterTemp + " " + date);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                transfer.setIcon(new ImageIcon("src/icons/InApp/transfer_entered.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                transfer.setIcon(new ImageIcon("src/icons/InApp/transferButton.png"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                transfer.setIcon(new ImageIcon("src/icons/InApp/transfer_press.png"));
            }

            private String getCurrentDate() {
//                LocalDateTime now = LocalDateTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                String formatDateTime = now.format(formatter);
//                System.out.println("Transfer date: " + formatDateTime);
//                return formatDateTime;
                return "";
            }
        });

        JLabel back = new JLabel();
        back.setIcon(new ImageIcon("src/icons/InApp/backButton.png"));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                back.setIcon(new ImageIcon("src/icons/InApp/back_entered.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                back.setIcon(new ImageIcon("src/icons/InApp/backButton.png"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                back.setIcon(new ImageIcon("src/icons/InApp/back_press.png"));
            }
        });

        JLabel clr = new JLabel();
        clr.setIcon(new ImageIcon("src/icons/InApp/clrBtn.png"));
        clr.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                clr.setIcon(new ImageIcon("src/icons/InApp/clrEntered.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                clr.setIcon(new ImageIcon("src/icons/InApp/clrBtn.png"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                clr.setIcon(new ImageIcon("src/icons/InApp/clrPress.png"));
            }
        });

//        transferButton.add(back);
        transferButton.add(clr);
        transferButton.add(transfer);

        transferGUI.add(from);
        transferGUI.add(arrow);
        transferGUI.add(to);
        transferGUI.add(enterAccount);
        transferGUI.add(bankNoSearch);
        transferGUI.add(search);
        transferGUI.add(amount);
        transferGUI.add(amountInput);
        transferGUI.add(currencyList);
        transferGUI.add(letter);
        transferGUI.add(letterInput, JLayeredPane.PALETTE_LAYER);
        transferGUI.add(transferButton, JLayeredPane.MODAL_LAYER);

        transferGUI.addComponentListener(new ComponentAdapter() {

        });

        return transferGUI;

    }
}
