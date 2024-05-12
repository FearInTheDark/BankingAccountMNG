package org.example.Views;

import org.example.Data.DB_Manager;
import org.example.Models.Account;
import org.example.Models.Card;
import org.example.Models.Transaction;
import org.example.Views.CustomizeUI.MyComboBoxUI;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FunctionsGUI {
    private static JPopupMenu popupMenu;
    private final Account account;
    private final Card card;
    private int width;
    private int height;
    private boolean isHiddenCard = false;
    private boolean isHidden = false;
    private boolean isHiddenBalance = false;

    public FunctionsGUI(Account account, Card card) {
        this.account = account;
        this.card = card;
    }

    public static JLayeredPane createPanel(Transaction transaction, Account sender, Account receiver) {
        boolean isSender = transaction.getBankNoFrom().equals(sender.getId());
        JLayeredPane panel = new JLayeredPane();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBackground(null);
        panel.setPreferredSize(new Dimension(743, 129));

        JLabel frame = new JLabel("Test");
        ImageIcon icon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/frame_transaction.png");
        Icon resized = new ImageIcon(icon.getImage().getScaledInstance(743, 109, Image.SCALE_DEFAULT));
        frame.setIcon(resized);
        frame.setBounds(2, 10, 743, 109);

        JLayeredPane transInfo = new JLayeredPane();
        transInfo.setLayout(null);
        transInfo.setBounds(2, 10, 743, 109);
        transInfo.setBackground(null);
        transInfo.setOpaque(false);

        String senderString = sender.getFullName().toUpperCase();
        String receiverString = receiver.getFullName().toUpperCase();

        JLabel senderLabel = new JLabel(senderString);
        senderLabel.setFont(new Font("Arial", Font.BOLD, 28));
        senderLabel.setForeground((isSender ? new Color(25, 146, 217) : Color.BLACK));
        senderLabel.setBounds(20, 10, 300, 32);
        senderLabel.setHorizontalTextPosition(JLabel.LEFT);

        JLabel receiverLabel = new JLabel(receiverString);
        receiverLabel.setFont(new Font("Arial", Font.BOLD, 28));
        receiverLabel.setForeground((isSender ? Color.BLACK : new Color(25, 146, 217)));
        receiverLabel.setBounds(20, 62, 300, 28);
        receiverLabel.setHorizontalTextPosition(JLabel.LEFT);

        JLabel arrow = new JLabel();
        String arrowFile = isSender ? "src/main/java/org/example/Views/icons/InApp/arrowTrans.png" : "src/main/java/org/example/Views/icons/InApp/arrowTransGreen.png";
        ImageIcon arrowIcon = new ImageIcon(arrowFile);
        Icon arrowResized = new ImageIcon(arrowIcon.getImage().getScaledInstance(90, 30, Image.SCALE_DEFAULT));
        arrow.setIcon(arrowResized);
        arrow.setBounds(60, 38, 90, 30);
        arrow.setVisible(false);


        String transferType = "<html><u>" + (isSender ? "Transfer" : "Receive") + "</u></html>" + " money";
        JLabel type = new JLabel(transferType);
        type.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 20));
        type.setForeground((isSender ? Color.RED : new Color(32, 192, 48)));
        type.setBounds(350, 5, 80, 20);

        String amountSeperated = String.format("%,d", transaction.getAmount());
        String amountSrt = (isSender ? "- " : "+ ") + amountSeperated + " VND";
        JLabel amountLabel = new JLabel(amountSrt);
        amountLabel.setFont(new Font("Calibri", Font.BOLD, 28));
        amountLabel.setForeground((isSender ? Color.RED : new Color(32, 192, 48)));
        amountLabel.setBounds(260, 40, 250, 32);
        amountLabel.setHorizontalTextPosition(JLabel.CENTER);
        amountLabel.setHorizontalAlignment(JLabel.CENTER);
        amountLabel.setVerticalAlignment(JLabel.CENTER);
        amountLabel.setVerticalTextPosition(JLabel.CENTER);

        JLabel dateText = new JLabel("Date : ");
        dateText.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 15));
        dateText.setForeground(Color.BLACK);
        dateText.setBounds(310, 70, 50, 32);


        String dateFixed = "<html>" + transaction.getDate().substring(0, 10) + "<br>" + transaction.getDate().substring(11, 19) + "</html>";
        JLabel dateLabel = new JLabel(dateFixed);
        dateLabel.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 15));
        dateLabel.setForeground(Color.BLACK);
        dateLabel.setBounds(365, 70, 100, 36);

        JLabel letterLabel = new JLabel(transaction.getLetter());
        ImageIcon letterIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/letterFrame.png");
        Icon letterResized = new ImageIcon(letterIcon.getImage().getScaledInstance(240, 100, Image.SCALE_DEFAULT));
        letterLabel.setIcon(letterResized);
        letterLabel.setBounds(500, 4, 240, 100);
        letterLabel.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 15));

        JTextArea textArea = new JTextArea();
        String letterText = "From : " + sender.getFullName().toUpperCase() + "\n" + transaction.getLetter();
        textArea.setText(letterText);
        textArea.setBounds(510, 10, 220, 80);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 20));
        textArea.setForeground(Color.BLACK);
        textArea.setOpaque(false);
        textArea.setFocusable(false);

        transInfo.add(senderLabel);
        transInfo.add(receiverLabel);
        transInfo.add(arrow, JLayeredPane.MODAL_LAYER);
        transInfo.add(type);
        transInfo.add(amountLabel);
        transInfo.add(dateText);
        transInfo.add(dateLabel);
        transInfo.add(letterLabel);
        transInfo.add(textArea, JLayeredPane.MODAL_LAYER);

        panel.add(frame, JLayeredPane.DEFAULT_LAYER);
        panel.add(transInfo, JLayeredPane.PALETTE_LAYER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                arrow.setVisible(true);
            }

            public void mouseExited(MouseEvent evt) {
                arrow.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    if (popupMenu != null) {
                        popupMenu.setVisible(false);
                    }
                    popupMenu = new JPopupMenu();
                    JMenuItem copy = new JMenuItem("Copy");
                    copy.setFont(new Font("Segeo UI", Font.PLAIN, 25));
                    copy.addActionListener(e -> {
                        StringSelection selection = new StringSelection(letterText);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, selection);
                    });

                    popupMenu.add(copy);
                    popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());

                }
            }
        });


        return panel;
    }

    static JLabel getHideShow(String iconPath) {
        JLabel hide = new JLabel();
        ImageIcon hideIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/" + iconPath + ".png");
        Icon hideIconResize = new ImageIcon(hideIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        hide.setIcon(hideIconResize);
        hide.setPreferredSize(new Dimension(30, 30));

        return hide;
    }

    public JLayeredPane AccountInformation(int width, int height) {
        JLayeredPane accountInformation = new JLayeredPane();

        JLayeredPane header = new JLayeredPane();
        header.setLayout(null);
        header.setBounds((width - 800) / 2, 30, 800, 192);
        header.setBackground(null);
        header.setOpaque(false);


        JLabel headerForm = new JLabel();
        ImageIcon headerFormIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/Account/AccountForm.png");
        Icon headerFormIconResize = new ImageIcon(headerFormIcon.getImage().getScaledInstance(800, 192, Image.SCALE_DEFAULT));
        headerForm.setIcon(headerFormIconResize);
        headerForm.setBounds(0, 0, 800, 192);

        JLabel avatar = new JLabel();
        ImageIcon avatarIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/Account/avatar.png");
        Icon avatarIconResize = new ImageIcon(avatarIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        avatar.setIcon(avatarIconResize);
        avatar.setBounds(46, 46, 100, 100);

        JLabel name = new JLabel(account.getFullName().toUpperCase());
        name.setBounds(170, 30, 300, 30);
        name.setFont(new Font("Arial", Font.BOLD, 30));
        name.setForeground(Color.WHITE);
        name.setHorizontalTextPosition(JLabel.LEFT);
        name.setHorizontalAlignment(JLabel.LEFT);
        name.setVerticalAlignment(JLabel.CENTER);

        JLabel phoneNo = new JLabel("Phone Number: " + account.getPhoneNo());
        phoneNo.setBounds(170, 70, 300, 30);
        phoneNo.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneNo.setForeground(Color.WHITE);
        phoneNo.setHorizontalTextPosition(JLabel.LEFT);
        phoneNo.setHorizontalAlignment(JLabel.LEFT);
        phoneNo.setVerticalAlignment(JLabel.CENTER);

        JLabel citizenID = new JLabel("Citizen ID: " + account.getIdCard());
        citizenID.setBounds(170, 110, 300, 30);
        citizenID.setFont(new Font("Arial", Font.PLAIN, 20));
        citizenID.setForeground(Color.WHITE);
        citizenID.setHorizontalTextPosition(JLabel.LEFT);
        citizenID.setHorizontalAlignment(JLabel.LEFT);
        citizenID.setVerticalAlignment(JLabel.CENTER);

        JLabel birthday = new JLabel("Birthday: " + account.getBirthDay());
        birthday.setBounds(170, 150, 300, 30);
        birthday.setFont(new Font("Arial", Font.PLAIN, 20));
        birthday.setForeground(Color.WHITE);
        birthday.setHorizontalTextPosition(JLabel.LEFT);
        birthday.setHorizontalAlignment(JLabel.LEFT);
        birthday.setVerticalAlignment(JLabel.CENTER);

        JLayeredPane cardAccount = getCardPane();
        cardAccount.setBounds(500, 1, 280, 194);

        JLayeredPane main = new JLayeredPane();
        main.setLayout(null);
        main.setBounds((width - 800) / 2, 240, 800, 491);
        main.setBackground(null);
        main.setOpaque(false);

        JLabel mainForm = new JLabel();
        ImageIcon mainFormIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/Account/AccInfoForm.png");
        Icon mainFormIconResize = new ImageIcon(mainFormIcon.getImage().getScaledInstance(800, 491, Image.SCALE_DEFAULT));
        mainForm.setIcon(mainFormIconResize);
        mainForm.setBounds(0, 0, 800, 491);

        String[] keys = {"Full Name", "Phone Number", "Citizen ID", "Birthday", "Bank Number", "Balance", "Address"};
        String[] values = {account.getFullName(), account.getPhoneNo(), account.getIdCard(), account.getBirthDay(), account.getId(), String.format("%,d", card.getBalance()), account.getAddress()};
        for (int i = 0; i < keys.length; i++) {
            // 6 lines each line includes 2 JLabels 400 * 60 gap 10 font Jetbrains Mono 25 White color
            JLabel key = new JLabel(keys[i] + ": ");
            key.setBounds(0, 20 + i * 60, 400, 60);
            key.setFont(new Font("JetBrains Mono", Font.BOLD, 30));
            key.setForeground(Color.WHITE);
            key.setHorizontalTextPosition(JLabel.RIGHT);
            key.setHorizontalAlignment(JLabel.RIGHT);
            key.setVerticalAlignment(JLabel.CENTER);

            JLabel value = new JLabel(values[i]);
            value.setBounds(400, 20 + i * 60, 400, 60);
            value.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
            value.setForeground(Color.WHITE);
            value.setHorizontalTextPosition(JLabel.LEFT);
            value.setHorizontalAlignment(JLabel.LEFT);
            value.setVerticalAlignment(JLabel.CENTER);

            main.add(key, JLayeredPane.PALETTE_LAYER);
            main.add(value, JLayeredPane.PALETTE_LAYER);

        }

        JLabel note = new JLabel("Note: These information can not be changed!");
        note.setBounds(0, 430, 800, 60);
        note.setFont(new Font("JetBrains Mono", Font.PLAIN | Font.ITALIC, 20));
        note.setForeground(Color.WHITE);
        note.setHorizontalTextPosition(JLabel.CENTER);
        note.setHorizontalAlignment(JLabel.CENTER);
        note.setVerticalAlignment(JLabel.CENTER);

        JLayeredPane more = new JLayeredPane();
        more.setLayout(null);
        more.setBounds((width - 800) / 2, 750, 800, 491);
        more.setBackground(null);
        more.setOpaque(false);

        JLabel moreForm = new JLabel();
        ImageIcon moreFormIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/Account/moreForm.png");
        Icon moreFormIconResize = new ImageIcon(moreFormIcon.getImage().getScaledInstance(800, 112, Image.SCALE_DEFAULT));
        moreForm.setIcon(moreFormIconResize);
        moreForm.setBounds(0, 0, 800, 112);

        JLabel moreTitle = new JLabel("   Bank Account Specific Information: -> ");
        moreTitle.setBounds(0, 0, 800, 112);
        moreTitle.setFont(new Font("JetBrains Mono", Font.PLAIN, 25));
        moreTitle.setForeground(Color.WHITE);
        moreTitle.setHorizontalTextPosition(JLabel.LEFT);
        moreTitle.setHorizontalAlignment(JLabel.LEFT);
        moreTitle.setVerticalAlignment(JLabel.CENTER);

        JLabel arrowTo = new JLabel();
        ImageIcon arrowToIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/Account/AccountArrow.png");
        Icon arrowToIconResize = new ImageIcon(arrowToIcon.getImage().getScaledInstance(52, 52, Image.SCALE_DEFAULT));
        arrowTo.setIcon(arrowToIconResize);
        arrowTo.setBounds(700, (112 - 52) / 2, 52, 52);
        arrowTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                dispose();
//                JFrame specs = AccountInformationMore(bankNo);
//                specs.setVisible(true);
            }
        });

        header.add(headerForm, JLayeredPane.DEFAULT_LAYER);
        header.add(avatar, JLayeredPane.PALETTE_LAYER);
        header.add(name, JLayeredPane.PALETTE_LAYER);
        header.add(phoneNo, JLayeredPane.PALETTE_LAYER);
        header.add(citizenID, JLayeredPane.PALETTE_LAYER);
        header.add(birthday, JLayeredPane.PALETTE_LAYER);
        header.add(cardAccount, JLayeredPane.PALETTE_LAYER);
        main.add(mainForm, JLayeredPane.DEFAULT_LAYER);
        main.add(note, JLayeredPane.PALETTE_LAYER);
        more.add(moreForm, JLayeredPane.DEFAULT_LAYER);
        more.add(moreTitle, JLayeredPane.PALETTE_LAYER);
        more.add(arrowTo, JLayeredPane.MODAL_LAYER);

        JLabel bg = new JLabel();
        ImageIcon bgIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/bg_purple.jpg");
        Icon bgIconResize = new ImageIcon(bgIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        bg.setIcon(bgIconResize);

        bg.setBounds(0, 0, width, height);

        accountInformation.add(header, JLayeredPane.PALETTE_LAYER);
        accountInformation.add(main, JLayeredPane.PALETTE_LAYER);
        accountInformation.add(more, JLayeredPane.PALETTE_LAYER);
        accountInformation.add(bg, JLayeredPane.DEFAULT_LAYER);
        accountInformation.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                int width = accountInformation.getWidth();
                int height = accountInformation.getHeight();
                header.setBounds((width - 800) / 2, 30, 800, 192);
                main.setBounds((width - 800) / 2, 240, 800, 491);
                more.setBounds((width - 800) / 2, height - 125, 800, 112);
            }
        });

        return accountInformation;
    }

    public JLayeredPane getListTransactions(int InWidth, int InHeight) {
        this.width = InWidth;
        this.height = InHeight;
        JLayeredPane listTransactions = new JLayeredPane();
        listTransactions.setBounds(0, 0, width, height);
        listTransactions.setBackground(null);
        listTransactions.setOpaque(false);

        ImageIcon background = new ImageIcon("src/main/java/org/example/Views/icons/InApp/bg_dark1.jpg");
        JLabel bg = new JLabel();
        Icon bgIcon = new ImageIcon(background.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        bg.setIcon(bgIcon);

        JLabel title = new JLabel("List Transactions");
        title.setBounds(0, 0, width, 100);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

        JLayeredPane cardPanel = getCard();
        cardPanel.setBounds((width - 743) / 2, 110, 743, 233);

        JScrollPane listTransactionsScroll = new JScrollPane();
        listTransactionsScroll.setBounds((width - 760) / 2, 350, 760, height - 400);
        listTransactionsScroll.setBorder(null);
        listTransactionsScroll.getVerticalScrollBar().setValue(0);
        listTransactionsScroll.getVerticalScrollBar().setBackground(Color.WHITE);
        listTransactionsScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(null);
                button.setForeground(null);
                button.setBorder(null);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(null);
                button.setForeground(null);
                button.setBorder(null);
                return button;
            }
        });
        listTransactionsScroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 3));
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(null);
        listPanel.setOpaque(false);
        List<Transaction> transactions = DB_Manager.queryTransactions(account.getId());
        assert transactions != null;
        System.out.println("Number of rows: " + transactions.size());
        transactions.forEach(transaction -> {
            Account sender = DB_Manager.queryAccount(transaction.getBankNoFrom());
            Account receiver = DB_Manager.queryAccount(transaction.getBankNoTo());
            if (sender == null || receiver == null) {
                System.err.println("Sender or receiver is null");
                return;
            }
            listPanel.add(createPanel(transaction, sender, receiver));
        });
        listTransactionsScroll.setViewportView(listPanel);
        listTransactionsScroll.setBackground(null);
        listTransactionsScroll.setOpaque(false);
        listTransactionsScroll.getViewport().setOpaque(false);
        listTransactionsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listTransactionsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listTransactions.add(title, JLayeredPane.PALETTE_LAYER);
        listTransactions.add(cardPanel, JLayeredPane.PALETTE_LAYER);
        listTransactions.add(listTransactionsScroll, JLayeredPane.PALETTE_LAYER);
        listTransactions.add(bg, JLayeredPane.DEFAULT_LAYER);

        listTransactions.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = listTransactions.getWidth();
                int height = listTransactions.getHeight();
                title.setBounds(0, 0, width, 100);
                cardPanel.setBounds((width - cardPanel.getWidth()) / 2, 110, 743, 233);
                listTransactionsScroll.setBounds((width - 760) / 2, 350, 760, height - 400);
                bg.setIcon(new ImageIcon(background.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                bg.setBounds(0, 0, width, height);
            }
        });


        return listTransactions;
    }

    public JLayeredPane getCard() {
        JLabel frameLabel = new JLabel();
        ImageIcon frame = new ImageIcon("src/main/java/org/example/Views/icons/InApp/rectangle.png");
        ImageIcon frameIcon = new ImageIcon(frame.getImage().getScaledInstance(743, 233, Image.SCALE_DEFAULT));
        frameLabel.setIcon(frameIcon);
        frameLabel.setBounds(0, 0, 743, 233);

        JLayeredPane bankInfo = new JLayeredPane();
        bankInfo.setLayout(null);
        bankInfo.setBackground(null);
        bankInfo.setBounds((743 - 680) / 2 - 15, 5, 743, 233);

        JLabel bankAcc = new JLabel();
        bankAcc.setText("Payment Account: ");
        bankAcc.setFont(new Font("Arial", Font.PLAIN, 25));
        bankAcc.setBounds(20, 20, 300, 30);

        JLabel bankNoLB = new JLabel();
        String bankNum = card.getBankNo();
        StringBuilder bankNumHid = new StringBuilder();
        for (int i = 0; i < bankNum.length(); i++) {
            if (i >= 10 || bankNum.charAt(i) == ' ') {
                bankNumHid.append(bankNum.charAt(i));
            } else {
                bankNumHid.append('*');
            }
        }
        bankNoLB.setText(bankNum);
        bankNoLB.setFont(new Font("Arial", Font.BOLD, 30));
        bankNoLB.setBounds(20, 50, 260, 30);

        JLabel hide = getHideShow("show");
        hide.setBounds(290, 50, 30, 30);
        hide.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isHidden) {
                    bankNoLB.setText(bankNumHid.toString());
                    hide.setIcon(getHideShow("hide").getIcon());
                    isHidden = true;
                } else {
                    bankNoLB.setText(bankNum);
                    hide.setIcon(getHideShow("show").getIcon());
                    isHidden = false;
                }
            }
        });

        JLabel showMore = new JLabel();
        ImageIcon arrowIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/thinArrow.png");
        Icon arrowIconResize = new ImageIcon(arrowIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        showMore.setIcon(arrowIconResize);
        showMore.setBounds(320, 50, 30, 30);

        JLabel bankBalance = new JLabel();
        bankBalance.setText("Balance: ");
        bankBalance.setFont(new Font("Arial", Font.PLAIN, 20));
        bankBalance.setBounds(20, 90, 84, 30);

        JLabel bankBalanceNo = new JLabel();
        String balanceText = String.format("%,d", card.getBalance());
        bankBalanceNo.setText(balanceText);
        bankBalanceNo.setFont(new Font("Arial", Font.BOLD, 28));
        bankBalanceNo.setBounds(110, 90, 170, 30);

        JLabel hideBalance = getHideShow("show");
        hideBalance.setBounds(280, 90, 30, 30);
        hideBalance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isHiddenBalance) {
                    bankBalanceNo.setText("****");
                    hideBalance.setIcon(getHideShow("hide").getIcon());
                    isHiddenBalance = true;
                } else {
                    bankBalanceNo.setText(balanceText);
                    hideBalance.setIcon(getHideShow("show").getIcon());
                    isHiddenBalance = false;
                }
            }
        });

        String[] currencies = {"VND", "USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "HKD"};
        JComboBox<String> currencyBox = new JComboBox<>(currencies);
        currencyBox.setBounds(310, 90, 80, 30);
        currencyBox.setFont(new Font("Arial", Font.ITALIC, 15));
        currencyBox.setBackground(Color.WHITE);
        currencyBox.setForeground(Color.BLACK);
        currencyBox.setBorder(null);

        currencyBox.addActionListener(e -> {
            String currency = (String) currencyBox.getSelectedItem();
            switch (currency) {
                case "VND" -> bankBalanceNo.setText(balanceText);
                case "USD" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 23000.0));
                case "EUR" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 27000.0));
                case "JPY" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 210.0));
                case "GBP" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 30000.0));
                case "AUD" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 17000.0));
                case "CAD" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 18000.0));
                case "CHF" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 25000.0));
                case "CNY" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 3500.0));
                case "HKD" -> bankBalanceNo.setText(String.format("%,.2f", card.getBalance() / 3000.0));
                case null -> {

                }
                default -> throw new IllegalStateException("Unexpected value: " + currency);
            }
        });

        JLabel cardNo = new JLabel();
        String cardNum = card.getId();
        String cardNumFix = formatAccountNumber(cardNum);
        String hiddenCardNo = maskAccountNumber(cardNum);

        cardNo.setText(hiddenCardNo);
        cardNo.setFont(new Font("Arial", Font.BOLD, 20));
        cardNo.setBounds(403, 53, 270, 30);
        cardNo.setVerticalTextPosition(JLabel.CENTER);
        cardNo.setHorizontalTextPosition(JLabel.CENTER);
        cardNo.setHorizontalAlignment(JLabel.CENTER);
        cardNo.setVerticalAlignment(JLabel.CENTER);
        cardNo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel hideCard = getHideShow("hide");
        hideCard.setBounds(623, 83, 30, 30);
        hideCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isHiddenCard) {
                    cardNo.setText(cardNumFix);
                    hideCard.setIcon(getHideShow("show").getIcon());
                    isHiddenCard = true;
                } else {
                    cardNo.setText(hiddenCardNo);
                    hideCard.setIcon(getHideShow("hide").getIcon());
                    isHiddenCard = false;
                }
            }
        });

        ImageIcon card = new ImageIcon("src/main/java/org/example/Views/icons/InApp/ATM.png");
        Icon cardIcon = new ImageIcon(card.getImage().getScaledInstance(280, 194, Image.SCALE_DEFAULT));
        JLabel cardStatus = new JLabel();
        cardStatus.setIcon(cardIcon);
        cardStatus.setBounds(400, 10, 280, 194);

        bankInfo.add(bankAcc);
        bankInfo.add(bankNoLB);
        bankInfo.add(hide);
        bankInfo.add(bankBalance);
        bankInfo.add(bankBalanceNo);
        bankInfo.add(hideBalance);
        bankInfo.add(showMore);
        bankInfo.add(currencyBox);
        bankInfo.add(cardStatus);
        bankInfo.add(cardNo, JLayeredPane.PALETTE_LAYER);
        bankInfo.add(hideCard, JLayeredPane.PALETTE_LAYER);

        JLayeredPane cardPane = new JLayeredPane();
        cardPane.setPreferredSize(new Dimension(743, 233));

        cardPane.add(bankInfo, JLayeredPane.PALETTE_LAYER);
        cardPane.add(frameLabel, JLayeredPane.DEFAULT_LAYER);
        return cardPane;
    }

    private JLayeredPane getCardPane() {
        JLayeredPane card = new JLayeredPane();
        card.setLayout(null);
        card.setBounds(400, 10, 280, 194);
        card.setBackground(null);

        JLabel cardNo = new JLabel();
        String cardNum = this.card.getId();
        String cardNumFix = formatAccountNumber(cardNum);
        String hiddenCardNo = maskAccountNumber(cardNum);

        cardNo.setText(hiddenCardNo);
        cardNo.setFont(new Font("Arial", Font.BOLD, 20));
        cardNo.setBounds(5, 45, 270, 30);
        cardNo.setVerticalTextPosition(JLabel.CENTER);
        cardNo.setHorizontalTextPosition(JLabel.CENTER);
        cardNo.setHorizontalAlignment(JLabel.CENTER);
        cardNo.setVerticalAlignment(JLabel.CENTER);

        JLabel hideCard = getHideShow("hide");
        hideCard.setBounds(240, 80, 30, 30);
        hideCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isHiddenCard) {
                    cardNo.setText(cardNumFix);
                    hideCard.setIcon(getHideShow("show").getIcon());
                    isHiddenCard = true;
                } else {
                    cardNo.setText(hiddenCardNo);
                    hideCard.setIcon(getHideShow("hide").getIcon());
                    isHiddenCard = false;
                }
            }
        });

        ImageIcon cardIIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/ATM.png");
        Icon cardIcon = new ImageIcon(cardIIcon.getImage().getScaledInstance(280, 194, Image.SCALE_DEFAULT));
        JLabel cardStatus = new JLabel();
        cardStatus.setIcon(cardIcon);
        cardStatus.setBounds(0, 0, 280, 194);

        card.add(cardNo, JLayeredPane.PALETTE_LAYER);
        card.add(hideCard, JLayeredPane.PALETTE_LAYER);
        card.add(cardStatus, JLayeredPane.DEFAULT_LAYER);
        return card;
    }

    private String formatAccountNumber(String accountNumber) {
        if (accountNumber.length() != 16) {
            System.out.println("Invalid account number length");
            throw new IllegalArgumentException("Invalid account number length");
        }

        StringBuilder formattedNumber = new StringBuilder();

        for (int i = 0; i < accountNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedNumber.append(' ');
            }

            formattedNumber.append(accountNumber.charAt(i));
        }

        return formattedNumber.toString();
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() != 16) {
            System.out.println("Invalid account number length");
            throw new IllegalArgumentException("Invalid account number length");
        }
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < accountNumber.length(); i++) {
            if (i >= 12 || accountNumber.charAt(i) == ' ') {
                maskedNumber.append(accountNumber.charAt(i));
            } else {
                maskedNumber.append('*');
            }
            if ((i + 1) % 4 == 0 && (i + 1) < accountNumber.length()) {
                maskedNumber.append(' ');
            }
        }

        return maskedNumber.toString();
    }
}
