package Views;

import Data.DB_Manager;
import Models.ModelAccount;
import Models.ModelCard;
import Models.ModelTransaction;
import Views.table_management.swing.ButtonAction;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import org.jdesktop.swingx.JXFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TransferGUI extends JXFrame {
    private static ModelAccount receiver;

    public TransferGUI() {
    }

    public static JPanel getTransferGUI(ModelAccount modelAccount, int width, int height) {
        JPanel transferGUI = new JPanel();
        transferGUI.setBounds(0, 0, width, height);
        transferGUI.setLayout(new BoxLayout(transferGUI, BoxLayout.Y_AXIS));
        transferGUI.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        transferGUI.setBackground(Color.WHITE);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLayeredPane from = new JLayeredPane();
        from.setPreferredSize(new Dimension(350, 350));
        from.setLayout(null);

        JLabel fromLabel = new JLabel();
        ImageIcon fromIcon = new ImageIcon("src/main/resources/icons/InApp/Transfer/transferFrom.png");
        Icon fromIconResize = new ImageIcon(fromIcon.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        fromLabel.setIcon(fromIconResize);
        fromLabel.setBounds(0, 0, 350, 350);

        JLabel fromTitle = new JLabel("From");
        fromTitle.setBounds(50, 10, 250, 50);
        fromTitle.setFont(new Font("Arial", Font.BOLD, 30));
        fromTitle.setVerticalTextPosition(JLabel.CENTER);
        fromTitle.setHorizontalTextPosition(JLabel.CENTER);
        fromTitle.setHorizontalAlignment(JLabel.CENTER);
        fromTitle.setVerticalAlignment(JLabel.CENTER);

        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/resources/icons/InApp/frame.png");
        Icon logoIconResize = new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        logo.setIcon(logoIconResize);
        logo.setBounds(125, 60, 100, 100);

        JLabel fullName = new JLabel(modelAccount.getFullName().toUpperCase());
        fullName.setBounds(25, 170, 300, 30);
        fullName.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        fullName.setForeground(Color.BLACK);
        fullName.setVerticalTextPosition(JLabel.CENTER);
        fullName.setHorizontalTextPosition(JLabel.CENTER);
        fullName.setHorizontalAlignment(JLabel.CENTER);
        fullName.setVerticalAlignment(JLabel.CENTER);

        String bankNum = "<html><body style='text-align: center'>Bank Number: <br>" + modelAccount.getId() + "</body></html>";

        JLabel bankNumber = new JLabel(bankNum);
        bankNumber.setBounds(50, 210, 250, 40);
        bankNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        bankNumber.setVerticalTextPosition(JLabel.CENTER);
        bankNumber.setHorizontalTextPosition(JLabel.CENTER);
        bankNumber.setHorizontalAlignment(JLabel.CENTER);
        bankNumber.setVerticalAlignment(JLabel.CENTER);

        JLabel currentBalance = new JLabel("Current Balance");
        currentBalance.setBounds(50, 260, 250, 25);
        currentBalance.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        currentBalance.setVerticalTextPosition(JLabel.CENTER);
        currentBalance.setHorizontalTextPosition(JLabel.CENTER);
        currentBalance.setHorizontalAlignment(JLabel.CENTER);
        currentBalance.setVerticalAlignment(JLabel.CENTER);


        String seperatedBalance = String.format("%,d", modelAccount.getModelCard().getBalance());

        JLabel balanceFrom = new JLabel(seperatedBalance);
        balanceFrom.setBounds(50, 285, 250, 50);
        balanceFrom.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
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
        ImageIcon arrowIcon = new ImageIcon("src/main/resources/icons/InApp/transferArrow.png");
        Icon arrowIconResize = new ImageIcon(arrowIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        arrow.setIcon(arrowIconResize);
        arrow.setPreferredSize(new Dimension(100, 100));


        JLayeredPane to = new JLayeredPane();
        to.setPreferredSize(new Dimension(350, 350));
        to.setLayout(null);

        JLabel toLabel = new JLabel();
        ImageIcon toIcon = new ImageIcon("src/main/resources/icons/InApp/transferFrom.png");
        Icon toIconResize = new ImageIcon(toIcon.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        toLabel.setIcon(toIconResize);
        toLabel.setBounds(0, 0, 350, 350);
        JLabel toTitle = new JLabel("To");
        toTitle.setBounds(50, 10, 250, 50);
        toTitle.setFont(new Font("Arial", Font.BOLD, 30));
        toTitle.setVerticalTextPosition(JLabel.CENTER);
        toTitle.setHorizontalTextPosition(JLabel.CENTER);
        toTitle.setHorizontalAlignment(JLabel.CENTER);
        toTitle.setVerticalAlignment(JLabel.CENTER);

        JLabel logoTo = new JLabel();
        ImageIcon logoToIcon = new ImageIcon("src/main/resources/icons/InApp/frame.png");
        Icon logoToIconResize = new ImageIcon(logoToIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        logoTo.setIcon(logoToIconResize);
        logoTo.setBounds(125, 60, 100, 100);

        String receiverName = "Full Name";
        JLabel fullNameTo = new JLabel(receiverName.toUpperCase());
        fullNameTo.setBounds(25, 170, 300, 30);
        fullNameTo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        fullNameTo.setVerticalTextPosition(JLabel.CENTER);
        fullNameTo.setHorizontalTextPosition(JLabel.CENTER);
        fullNameTo.setHorizontalAlignment(JLabel.CENTER);
        fullNameTo.setVerticalAlignment(JLabel.CENTER);

        String bankNumTo = "<html><body style='text-align: center'>Bank Number: <br>Bank Number</body></html>";

        JLabel bankNumberTo = new JLabel(bankNumTo);
        bankNumberTo.setBounds(50, 210, 250, 40);
        bankNumberTo.setFont(new Font("Arial", Font.PLAIN, 19));
        bankNumberTo.setVerticalTextPosition(JLabel.CENTER);
        bankNumberTo.setHorizontalTextPosition(JLabel.CENTER);
        bankNumberTo.setHorizontalAlignment(JLabel.CENTER);
        bankNumberTo.setVerticalAlignment(JLabel.CENTER);

        JLabel currentBalanceTo = new JLabel("Current Balance");
        currentBalanceTo.setBounds(50, 260, 250, 25);
        currentBalanceTo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        currentBalanceTo.setVerticalTextPosition(JLabel.CENTER);
        currentBalanceTo.setHorizontalTextPosition(JLabel.CENTER);
        currentBalanceTo.setHorizontalAlignment(JLabel.CENTER);
        currentBalanceTo.setVerticalAlignment(JLabel.CENTER);

        JLabel balanceTo = new JLabel("+ 0");
        balanceTo.setBounds(50, 285, 250, 50);
        balanceTo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
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

        labelPanel.add(from);
        labelPanel.add(arrow);
        labelPanel.add(to);

        JLabel enterAccount = new JLabel("Enter the recipient account here: ");
        enterAccount.setFont(new Font("Arial", Font.PLAIN | Font.ITALIC, 18));
        enterAccount.setForeground(Color.GRAY);
        enterAccount.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField bankNoSearch = new JTextField();
        bankNoSearch.setPreferredSize(new Dimension(800, 60));
        bankNoSearch.setFont(new Font("Arial", Font.PLAIN | Font.ITALIC, 25));
        bankNoSearch.setForeground(Color.GRAY);
        bankNoSearch.setMaximumSize(new Dimension(800, 60));
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
            }
        });

        bankNoSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter the phone number or bank number");
        bankNoSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon(TransferGUI.class.getResource("/icons/ManageIcons/search.svg")));
        bankNoSearch.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "margin:5,20,5,20;"
                + "background:$Panel.background");
        bankNoSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                receiver = DB_Manager.queryAccount(bankNoSearch.getText());
                if (receiver == null) return;
                ModelCard receiverCard = DB_Manager.queryCard(receiver.getIdCard());
                if (receiverCard != null) {
                    fullNameTo.setText(receiver.getFullName().toUpperCase());
                    bankNumberTo.setText("<html><body style='text-align: center'>Bank Number: <br>" + receiver.getId() + "</body></html>");
                    String seperatedBalance = String.format("%,d", receiverCard.getBalance());
                    balanceTo.setText("+ " + seperatedBalance);
                    balanceTo.setForeground(Color.GRAY);
                } else {
                    fullNameTo.setText("Full Name");
                    bankNumberTo.setText("<html><body style='text-align: center'>Bank Number: <br>Bank Number</body></html>");
                    balanceTo.setText("+ 0");
                    balanceTo.setForeground(Color.GRAY);
                }

            }
        });

        JLabel amount = new JLabel("Enter the amount: ");
        amount.setFont(new Font("Arial", Font.PLAIN, 18));
        amount.setForeground(Color.GRAY);
        amount.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel amountPanel = new JPanel();
        amountPanel.setMaximumSize(new Dimension(800, 60));
        amountPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField amountInput = new JTextField();
        amountInput.setForeground(Color.GRAY);
        amountInput.setPreferredSize(new Dimension(600, 50));
        amountInput.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 25));
        amountInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        amountInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Amount");
        amountInput.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "margin:5,20,5,20;"
                + "background:$Panel.background");


        String[] currency = {"VND", "USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "HKD"};
        JComboBox<String> currencyList = new JComboBox<>(currency);
        currencyList.setFont(new Font("Arial", Font.PLAIN, 18));
        currencyList.setBackground(Color.WHITE);
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

        amountPanel.add(amountInput);
        amountPanel.add(currencyList);

        JLabel letter = new JLabel("Enter the letter: ");
        letter.setFont(new Font("Arial", Font.PLAIN, 18));
        letter.setForeground(Color.GRAY);
        letter.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea letterInput = new JTextArea();
        letterInput.setFont(new Font("Arial", Font.PLAIN, 25));
        letterInput.setBackground(new Color(246, 246, 246));

        JScrollPane letterInputScrollPane = new JScrollPane(letterInput);
        letterInputScrollPane.setPreferredSize(new Dimension(800, 180));

        JPanel transferButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        transferButton.setBounds(50, 800, 800, 60);
        transferButton.setBackground(null);
        transferButton.setOpaque(false);

        ButtonAction transfer = new ButtonAction();
        transfer.setText("Transfer");
        transfer.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 25));
        transfer.setPreferredSize(new Dimension(200, 50));
        transfer.addActionListener(e -> {
            if (receiver == null) {
                JOptionPane.showMessageDialog(null, "Please enter the correct bank number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (amountInput.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (letterInput.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the letter", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ModelAccount receiver = DB_Manager.queryAccount(bankNoSearch.getText());
            if (receiver == null) {
                JOptionPane.showMessageDialog(null, "The recipient account does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ModelCard receiverCard = DB_Manager.queryCard(receiver.getIdCard());
            if (receiverCard == null) {
                JOptionPane.showMessageDialog(null, "The recipient account does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int amountTemp = Integer.parseInt(amountInput.getText());
            if (modelAccount.getModelCard().getBalance() < amountTemp) {
                JOptionPane.showMessageDialog(null, "Your balance is not enough", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            modelAccount.transferTo(receiverCard, amountTemp);
            ModelTransaction transaction = new ModelTransaction(modelAccount.getId(), receiver.getId(), amountTemp, sdf.format(new Date()), letterInput.getText());
            modelAccount.getModelCard().setBalance(modelAccount.getModelCard().getBalance() - amountTemp);
            receiverCard.setBalance(receiverCard.getBalance() + amountTemp);

            DB_Manager.saveTransaction(transaction);
            DB_Manager.updateCard(modelAccount.getModelCard());
            DB_Manager.updateCard(receiverCard);

            JOptionPane.showMessageDialog(null, "Transfer successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            amountInput.setText("");
            letterInput.setText("");
            balanceFrom.setText(String.format("%,d", modelAccount.getModelCard().getBalance()));
            balanceTo.setText("+ " + String.format("%,d", receiverCard.getBalance()));

        });

        ButtonAction cancel = new ButtonAction();
        cancel.setText("Cancel");
        cancel.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 25));
        cancel.setPreferredSize(new Dimension(200, 50));

        ButtonAction clear = new ButtonAction();
        clear.setText("Clear");
        clear.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 25));
        clear.setPreferredSize(new Dimension(200, 50));

        transferButton.add(transfer);
        transferButton.add(cancel);
        transferButton.add(clear);


        transferGUI.add(labelPanel);
        transferGUI.add(Box.createRigidArea(new Dimension(0, 20)));
        transferGUI.add(enterAccount);
        transferGUI.add(bankNoSearch);
        transferGUI.add(Box.createRigidArea(new Dimension(0, 20)));
        transferGUI.add(amount);
        transferGUI.add(amountPanel);
        transferGUI.add(Box.createRigidArea(new Dimension(0, 20)));
        transferGUI.add(letter);
        transferGUI.add(letterInputScrollPane, JLayeredPane.PALETTE_LAYER);
        transferGUI.add(Box.createRigidArea(new Dimension(0, 20)));
        transferGUI.add(transferButton, JLayeredPane.MODAL_LAYER);

        transferGUI.addComponentListener(new ComponentAdapter() {

        });

        return transferGUI;

    }
}
