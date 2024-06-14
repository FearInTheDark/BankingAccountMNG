package Views;

import Data.DB_Manager;
import Models.ModelAccount;
import Models.ModelCard;
import Models.ModelTransaction;
import jnafilechooser.api.JnaFileChooser;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import static Utils.Formatter.formatAccountNumber;
import static Utils.Formatter.maskAccountNumber;

@Getter
public class FunctionsGUI {
    private static JPopupMenu popupMenu;
    private final ModelAccount modelAccount;
    private final ModelCard modelCard;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private int width;
    private int height;
    private boolean isHiddenCard = false;
    private boolean isHidden = false;
    private boolean isHiddenBalance = false;

    public FunctionsGUI(ModelAccount modelAccount, ModelCard modelCard) {
        this.modelAccount = modelAccount;
        this.modelCard = modelCard;
    }

    static JLabel getHideShow(String iconPath) {
        JLabel hide = new JLabel();
        ImageIcon hideIcon = new ImageIcon("src/main/resources/icons/InApp/" + iconPath + ".png");
        Icon hideIconResize = new ImageIcon(hideIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        hide.setIcon(hideIconResize);
        hide.setPreferredSize(new Dimension(30, 30));

        return hide;
    }

    public JLayeredPane createPanel(ModelTransaction modelTransaction, ModelAccount sender, ModelAccount receiver) {
        boolean isSender = modelTransaction.getBankNoFrom().equals(sender.getId());
        JLayeredPane panel = new JLayeredPane();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBackground(null);
        panel.setPreferredSize(new Dimension(743, 129));

        JLabel frame = new JLabel("Test");
        ImageIcon icon = new ImageIcon("src/main/resources/icons/InApp/frame_transaction.png");
        Icon resized = new ImageIcon(icon.getImage().getScaledInstance(743, 109, Image.SCALE_DEFAULT));
        frame.setIcon(resized);
        frame.setBounds((width - 743) / 2, 10, 743, 109);

        JLayeredPane transInfo = new JLayeredPane();
        transInfo.setLayout(null);
        transInfo.setBounds((width - 743) / 2, 10, 743, 109);
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
        String arrowFile = isSender ? "src/main/resources/icons/InApp/arrowTrans.png" : "src/main/resources/icons/InApp/arrowTransGreen.png";
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

        String amountSeperated = String.format("%,d", modelTransaction.getAmount());
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(modelTransaction.getDate());
        String dateFixed = "<html>" + date.substring(0, 10) + "<br>" + date.substring(11, 19) + "</html>";
        JLabel dateLabel = new JLabel(dateFixed);
        dateLabel.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 15));
        dateLabel.setForeground(Color.BLACK);
        dateLabel.setBounds(365, 70, 100, 36);

        JLabel letterLabel = new JLabel(modelTransaction.getLetter());
        ImageIcon letterIcon = new ImageIcon("src/main/resources/icons/InApp/letterFrame.png");
        Icon letterResized = new ImageIcon(letterIcon.getImage().getScaledInstance(240, 100, Image.SCALE_DEFAULT));
        letterLabel.setIcon(letterResized);
        letterLabel.setBounds(500, 4, 240, 100);
        letterLabel.setFont(new Font("Calibri", Font.ITALIC | Font.PLAIN, 15));

        JTextArea textArea = new JTextArea();
        String letterText = "From : " + sender.getFullName().toUpperCase() + "\n" + modelTransaction.getLetter();
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

        frame.addMouseListener(new MouseAdapter() {
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
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = panel.getWidth();
                frame.setBounds((width - 743) / 2, 10, 743, 109);
                transInfo.setBounds((width - 743) / 2, 10, 743, 109);
            }
        });

        return panel;
    }

    public JLayeredPane AccountInformation(int width, int height) {
        JLayeredPane accountInformation = new JLayeredPane();

        JLayeredPane header = new JLayeredPane();
        header.setLayout(null);
        header.setBounds((width - 800) / 2, 30, 800, 192);
        header.setBackground(null);
        header.setOpaque(false);


        JLabel headerForm = new JLabel();
        ImageIcon headerFormIcon = new ImageIcon("src/main/resources/icons/InApp/Account/AccountForm.png");
        Icon headerFormIconResize = new ImageIcon(headerFormIcon.getImage().getScaledInstance(800, 192, Image.SCALE_DEFAULT));
        headerForm.setIcon(headerFormIconResize);
        headerForm.setBounds(0, 0, 800, 192);

        JLabel avatar = new JLabel();
        System.out.println(modelAccount.getAvatar());
        ImageIcon avatarIcon = new ImageIcon("src/main/resources/icons/InApp/Account/avatar.png");
        Icon avatarIconResize = new ImageIcon(avatarIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        avatar.setIcon(avatarIconResize);
        avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        avatar.setBounds(46, 46, 100, 100);
        avatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JnaFileChooser fileChooser = new JnaFileChooser();
                fileChooser.setMode(JnaFileChooser.Mode.Files);
                boolean show = fileChooser.showOpenDialog(null);
                if (show) {
                    ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
                    Icon iconResize = new ImageIcon(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                    modelAccount.setAvatar(imageIcon);
                    avatar.setIcon(iconResize);
                    avatar.revalidate();
                    avatar.repaint();
                }
            }
        });

        JLabel name = new JLabel(modelAccount.getFullName().toUpperCase());
        name.setBounds(170, 30, 300, 30);
        name.setFont(new Font("Arial", Font.BOLD, 30));
        name.setForeground(Color.WHITE);
        name.setHorizontalTextPosition(JLabel.LEFT);
        name.setHorizontalAlignment(JLabel.LEFT);
        name.setVerticalAlignment(JLabel.CENTER);

        JLabel phoneNo = new JLabel("Phone Number: " + modelAccount.getPhoneNo());
        phoneNo.setBounds(170, 70, 300, 30);
        phoneNo.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneNo.setForeground(Color.WHITE);
        phoneNo.setHorizontalTextPosition(JLabel.LEFT);
        phoneNo.setHorizontalAlignment(JLabel.LEFT);
        phoneNo.setVerticalAlignment(JLabel.CENTER);

        JLabel citizenID = new JLabel("Citizen ID: " + modelAccount.getIdCard());
        citizenID.setBounds(170, 110, 300, 30);
        citizenID.setFont(new Font("Arial", Font.PLAIN, 20));
        citizenID.setForeground(Color.WHITE);
        citizenID.setHorizontalTextPosition(JLabel.LEFT);
        citizenID.setHorizontalAlignment(JLabel.LEFT);
        citizenID.setVerticalAlignment(JLabel.CENTER);

        JLabel birthday = new JLabel("Birthday: " + modelAccount.getBirthDay());
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
        ImageIcon mainFormIcon = new ImageIcon("src/main/resources/icons/InApp/Account/AccInfoForm.png");
        Icon mainFormIconResize = new ImageIcon(mainFormIcon.getImage().getScaledInstance(800, 491, Image.SCALE_DEFAULT));
        mainForm.setIcon(mainFormIconResize);
        mainForm.setBounds(0, 0, 800, 491);

        String[] keys = {"Full Name", "Phone Number", "Citizen ID", "Birthday", "Bank Number", "Balance", "Address"};
        String[] values = {modelAccount.getFullName(), modelAccount.getPhoneNo(), modelAccount.getIdCard(), sdf.format(modelAccount.getBirthDay()), modelAccount.getId(), String.format("%,d", modelCard.getBalance()), modelAccount.getAddress()};
        for (int i = 0; i < keys.length; i++) {
            JLabel key = new JLabel(keys[i] + ": ");
            key.setBounds(0, 20 + i * 60, 400, 60);
            key.setFont(new Font("Roboto", Font.BOLD, 30));
            key.setForeground(Color.WHITE);
            key.setHorizontalTextPosition(JLabel.RIGHT);
            key.setHorizontalAlignment(JLabel.RIGHT);
            key.setVerticalAlignment(JLabel.CENTER);

            JLabel value = new JLabel(values[i]);
            value.setBounds(400, 20 + i * 60, 400, 60);
            value.setFont(new Font("Roboto", Font.PLAIN, 30));
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
        ImageIcon moreFormIcon = new ImageIcon("src/main/resources/icons/InApp/Account/moreForm.png");
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
        ImageIcon arrowToIcon = new ImageIcon("src/main/resources/icons/InApp/Account/AccountArrow.png");
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
        ImageIcon bgIcon = new ImageIcon("src/main/resources/icons/InApp/bg_purple.jpg");
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
                bg.setIcon(new ImageIcon(bgIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                bg.setBounds(0, 0, width, height);
            }
        });

        return accountInformation;
    }

    public JLayeredPane getListTransactions(int InWidth, int InHeight) {
        this.width = InWidth;
        this.height = InHeight;
        JLayeredPane listTransactions = new JLayeredPane();
        listTransactions.setBounds(0, 0, width, height);
        listTransactions.setBackground(new Color(0, 0, 0, 0));

        ImageIcon background = new ImageIcon("src/main/resources/icons/InApp/bg_dark1.jpg");
        JLabel bg = new JLabel();
        Icon bgIcon = new ImageIcon(background.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        bg.setIcon(bgIcon);

        JLabel title = new JLabel("List Transactions");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

        List<ModelTransaction> modelTransactions = DB_Manager.queryTransactions(modelAccount.getId());
        assert modelTransactions != null;
        System.out.println("Number of rows: " + modelTransactions.size());

        JLayeredPane cardPanel = getModelCard();

        JLabel totalTrans = new JLabel("Total transactions: " + modelTransactions.size());
        totalTrans.setBounds(100, 200, 200, 30);
        totalTrans.setPreferredSize(new Dimension(200, 30));
        totalTrans.setFont(new Font("Arial", Font.ITALIC, 20));
        totalTrans.setForeground(Color.BLACK);
        totalTrans.setHorizontalTextPosition(JLabel.LEFT);
        totalTrans.setHorizontalAlignment(JLabel.LEFT);

        cardPanel.add(totalTrans, JLayeredPane.PALETTE_LAYER);


        JScrollPane listTransactionsScroll = new JScrollPane();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        modelTransactions.forEach(modelTransaction -> {
            ModelAccount sender = DB_Manager.queryAccount(modelTransaction.getBankNoFrom());
            ModelAccount receiver = DB_Manager.queryAccount(modelTransaction.getBankNoTo());
            if (sender == null || receiver == null) return;
            listPanel.add(createPanel(modelTransaction, sender, receiver));
        });

        listTransactionsScroll.setViewportView(listPanel);
        listTransactionsScroll.setOpaque(false);
        listTransactionsScroll.getViewport().setOpaque(false);
        listTransactionsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listTransactionsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel dataPanel = new JPanel(new MigLayout("al center center, fillx", "5[]5[240!]10[]10", ""));
        dataPanel.setOpaque(false);
        dataPanel.setBounds(0, 0, width, height);

        dataPanel.add(title, "span, growx, center");
        dataPanel.add(cardPanel, "span, center, height 233!");
        dataPanel.add(listTransactionsScroll, "span, growx, center, pushy");


        listTransactions.add(bg, JLayeredPane.DEFAULT_LAYER);
        listTransactions.add(dataPanel, JLayeredPane.PALETTE_LAYER);

        listTransactions.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = listTransactions.getWidth();
                int height = listTransactions.getHeight();
                bg.setIcon(new ImageIcon(background.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                bg.setBounds(0, 0, width, height);
                dataPanel.setBounds(0, 0, width, height);
            }
        });

        listTransactions.revalidate();
        listTransactions.repaint();
        return listTransactions;
    }

    public JLayeredPane CardInfo(ModelAccount account, ModelCard card) {
        JLayeredPane cardInfo = new JLayeredPane();
//        cardInfo.setBounds(0, 0, width, height);
        cardInfo.setBackground(null);

        JLabel title = new JLabel("Card Information");
        title.setBounds(0, 0, width, 100);
        title.setFont(new Font("Tahoma", Font.BOLD, 50));
        title.setForeground(Color.BLACK);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
//        title.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel cardBG = new JLabel();
        ImageIcon cardBGIcon = new ImageIcon("src/main/resources/icons/InApp/CardInfo/cardBG.png");
        Icon cardBGIconResize = new ImageIcon(cardBGIcon.getImage().getScaledInstance(600, 500, Image.SCALE_DEFAULT));
        cardBG.setIcon(cardBGIconResize);
        cardBG.setBounds((width - 600) / 2, 100, 600 + 2, 500);

        JLabel cardForm = new JLabel();
        ImageIcon cardFormIcon = new ImageIcon("src/main/resources/icons/InApp/CardInfo/cardForm.png");
        Icon cardFormIconResize = new ImageIcon(cardFormIcon.getImage().getScaledInstance(600, 500, Image.SCALE_DEFAULT));
        cardForm.setBounds((width - 600) / 2, 100, 600, 500);
        cardForm.setIcon(cardFormIconResize);
        cardForm.setPreferredSize(new Dimension(600, 500));
//        cardForm.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel cardInfoPanel = new JPanel(null);
//        cardInfoPanel.setBounds(150 + 100, 210, 450, 300);
        cardInfoPanel.setBounds((width - 600) / 2, 100, 600, 500);
        cardInfoPanel.setOpaque(false);
//        cardInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        ImageIcon VKUicon = new ImageIcon("src/icons/frame.png");
        Image VKUimage = VKUicon.getImage();
        Image newVKUimage = VKUimage.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        VKUicon = new ImageIcon(newVKUimage);

        JLabel VKU = new JLabel();
        VKU.setIcon(VKUicon);
        VKU.setBounds(235, 100, 130, 130);
        VKU.setPreferredSize(new Dimension(130, 130));
        VKU.setHorizontalAlignment(JLabel.CENTER);
        VKU.setFont(new Font("Segoe UI", Font.BOLD, 40));
//        VKU.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.red));

        JLabel bankNoLabel = new JLabel("Bank No: " + account.getId());
        bankNoLabel.setBounds(240, 240, 290, 20);
        bankNoLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        bankNoLabel.setForeground(Color.red);
        bankNoLabel.setHorizontalAlignment(JLabel.CENTER);
//        bankNoLabel.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        JLabel FROM_TO_LABEL = new JLabel("FROM    TO");
        FROM_TO_LABEL.setBounds(130, 270, 150, 30);
        FROM_TO_LABEL.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        FROM_TO_LABEL.setForeground(Color.black);
        FROM_TO_LABEL.setHorizontalAlignment(JLabel.LEFT);


        String beginDateShow = card.getCardIssueDate();
        String endDateShow = card.getCardExpiryDate();

        JLabel beginDateLabel = new JLabel(beginDateShow);
        beginDateLabel.setBounds(135, 305, 75, 20);
        beginDateLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        beginDateLabel.setForeground(Color.black);
        beginDateLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel endDateLabel = new JLabel(endDateShow);
        endDateLabel.setBounds(210, 305, 75, 20);
        endDateLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        endDateLabel.setForeground(Color.black);
        endDateLabel.setHorizontalAlignment(JLabel.RIGHT);

        String cardNoSeperated = maskAccountNumber(card.getId());
        System.out.println(cardNoSeperated);
        JLabel cardNumberLabel = new JLabel(cardNoSeperated);
        cardNumberLabel.setBounds(70, 335, 520, 50);
        cardNumberLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 40));
        cardNumberLabel.setForeground(Color.white);
        cardNumberLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel cvvLabel = new JLabel("CVV ");
        cvvLabel.setBounds(300, 294, 50, 40);
        cvvLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        cvvLabel.setForeground(Color.white);
        cvvLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel cvvNum = new JLabel(card.getCvv());
        cvvNum.setBounds(350, 294, 50, 40);
        cvvNum.setFont(new Font("JetBrains Mono", Font.ITALIC, 25));
        cvvNum.setForeground(Color.GRAY);
        cvvNum.setHorizontalAlignment(JLabel.CENTER);

        JLayeredPane cardSttPane = new JLayeredPane();
        cardSttPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        cardSttPane.setBounds(0, 430, 600, 80);
        cardSttPane.setBackground(null);

        JLabel cardStt = new JLabel();
        cardStt.setText("Card Status: ");
        cardStt.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        cardStt.setForeground(new Color(26, 35, 78));

        JLabel cardSttIcon = new JLabel();
        String cardSttIconPath = (card.getCardStatus().equals("Active")) ? "src/main/resources/icons/InApp/CardInfo/Active.png" : "src/main/resources/icons/InApp/CardInfo/cardLocked.png";
        cardSttIcon.setIcon(new ImageIcon(cardSttIconPath));

        cardSttIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (card.getCardStatus().equals("Active")) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to lock your card?", "Warning", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        String PIN = JOptionPane.showInputDialog(null, "Enter your PIN: ", "PIN", JOptionPane.INFORMATION_MESSAGE);
                        if (!PIN.equals(card.getPin())) {
                            JOptionPane.showMessageDialog(null, "Wrong PIN", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        card.setCardStatus("Locked");
                        DB_Manager.updateCard(card);
                        cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/cardLocked.png"));
                        JOptionPane.showMessageDialog(null, "Lock card successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Your card is still active", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to unlock your card?", "Warning", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        String PIN = JOptionPane.showInputDialog(null, "Enter your PIN: ", "PIN", JOptionPane.INFORMATION_MESSAGE);
                        if (!PIN.equals(card.getPin())) {
                            JOptionPane.showMessageDialog(null, "Wrong PIN", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        card.setCardStatus("Active");
                        DB_Manager.updateCard(card);
                        cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/Active.png"));
                        JOptionPane.showMessageDialog(null, "Unlock card successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Your card is still locked", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (card.getCardStatus().equals("Active")) {
                    cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/Active_press.png"));
                } else {
                    cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/cardLocked_press.png"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (card.getCardStatus().equals("Active")) {
                    cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/Active_Hover.png"));
                } else {
                    cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/cardLocked_Hover.png"));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (card.getCardStatus().equals("Active")) {
                    cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/Active.png"));
                } else {
                    cardSttIcon.setIcon(new ImageIcon("src/main/resources/icons/InApp/CardInfo/cardLocked.png"));
                }
            }
        });

        cardSttPane.add(cardStt);
        cardSttPane.add(cardSttIcon);

        cardInfoPanel.add(VKU);
        cardInfoPanel.add(bankNoLabel);
        cardInfoPanel.add(cardNumberLabel);
        cardInfoPanel.add(FROM_TO_LABEL);
        cardInfoPanel.add(beginDateLabel);
        cardInfoPanel.add(endDateLabel);
        cardInfoPanel.add(cvvLabel);
        cardInfoPanel.add(cvvNum);
        cardInfoPanel.add(cardSttPane);

        JScrollPane cardInfoScroll = new JScrollPane();
        cardInfoScroll.setBounds((width - 600) / 2, 625, 610, 250);
        cardInfoScroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 3));

        JPanel cardInfoPanelScroll = new JPanel();
        cardInfoPanelScroll.setLayout(new BoxLayout(cardInfoPanelScroll, BoxLayout.Y_AXIS));
        cardInfoPanelScroll.setBackground(null);
        cardInfoPanelScroll.setOpaque(false);

        String[] listInfo = {"Cardholder Name", "Source Account", "Card Number", "Card Type", "Card Status", "Begin Date", "End Date", "CVV"};
        String[] listInfoValue = {account.getFullName(), account.getId(), card.getId(), "Debit", card.getCardStatus(), card.getCardIssueDate(), card.getCardExpiryDate(), card.getCvv()};
        for (int i = 0; i < listInfo.length; i++) {

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            infoPanel.setBackground(null);
            infoPanel.setOpaque(false);
            infoPanel.setPreferredSize(new Dimension(600, 65));

            JLabel infoKey = new JLabel(listInfo[i] + ": ");
            infoKey.setFont(new Font("JetBrains Mono", Font.BOLD, 25));
            infoKey.setForeground(Color.black);
            infoKey.setHorizontalAlignment(JLabel.LEFT);

            JLabel infoValue = new JLabel(listInfoValue[i]);
            infoValue.setFont(new Font("JetBrains Mono", Font.PLAIN, 25));
            infoValue.setForeground(Color.black);
            infoValue.setHorizontalAlignment(JLabel.RIGHT);

            infoPanel.add(infoKey);
            infoPanel.add(infoValue);
            cardInfoPanelScroll.add(infoPanel);
        }


        cardInfoScroll.setViewportView(cardInfoPanelScroll);

        cardInfo.add(title, JLayeredPane.PALETTE_LAYER);
        cardInfo.add(cardBG, JLayeredPane.DEFAULT_LAYER);
        cardInfo.add(cardForm, JLayeredPane.PALETTE_LAYER);
        cardInfo.add(cardInfoPanel, JLayeredPane.MODAL_LAYER);
        cardInfo.add(cardInfoScroll, JLayeredPane.PALETTE_LAYER);

        cardInfo.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                width = cardInfo.getWidth();
                height = cardInfo.getHeight();
                title.setBounds(0, 0, width, 100);
                cardBG.setBounds((width - 600) / 2, 100, 600 + 2, 500);
                cardForm.setBounds((width - 600) / 2, 100, 600, 500);
                cardInfoPanel.setBounds((width - 600) / 2, 100, 600, 500);
                cardInfoScroll.setBounds((width - 600) / 2, 625, 610, (height - 625) - 25);
            }
        });

        return cardInfo;
    }


    public JLayeredPane getModelCard() {
        JLabel frameLabel = new JLabel();
        ImageIcon frame = new ImageIcon("src/main/resources/icons/InApp/rectangle.png");
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
        String bankNum = modelCard.getBankNo();
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
        hide.setBounds(280, 50, 30, 30);
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
        ImageIcon arrowIcon = new ImageIcon("src/main/resources/icons/InApp/thinArrow.png");
        Icon arrowIconResize = new ImageIcon(arrowIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        showMore.setIcon(arrowIconResize);
        showMore.setBounds(320, 50, 30, 30);

        JLabel bankBalance = new JLabel();
        bankBalance.setText("Balance: ");
        bankBalance.setFont(new Font("Arial", Font.PLAIN, 20));
        bankBalance.setBounds(20, 90, 84, 30);

        JLabel bankBalanceNo = new JLabel();
        String balanceText = String.format("%,d", modelCard.getBalance());
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

        currencyBox.addActionListener(e -> {
            String currency = (String) currencyBox.getSelectedItem();
            switch (currency) {
                case "VND" -> bankBalanceNo.setText(balanceText);
                case "USD" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 23000.0));
                case "EUR" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 27000.0));
                case "JPY" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 210.0));
                case "GBP" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 30000.0));
                case "AUD" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 17000.0));
                case "CAD" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 18000.0));
                case "CHF" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 25000.0));
                case "CNY" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 3500.0));
                case "HKD" -> bankBalanceNo.setText(String.format("%,.2f", modelCard.getBalance() / 3000.0));
                case null -> {

                }
                default -> throw new IllegalStateException("Unexpected value: " + currency);
            }
        });

        JLabel cardNo = new JLabel();
        String cardNum = modelCard.getId();
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

        ImageIcon card = new ImageIcon("src/main/resources/icons/InApp/ATM.png");
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
        String cardNum = this.modelCard.getId();
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

        ImageIcon cardIIcon = new ImageIcon("src/main/resources/icons/InApp/ATM.png");
        Icon cardIcon = new ImageIcon(cardIIcon.getImage().getScaledInstance(280, 194, Image.SCALE_DEFAULT));
        JLabel cardStatus = new JLabel();
        cardStatus.setIcon(cardIcon);
        cardStatus.setBounds(0, 0, 280, 194);

        card.add(cardNo, JLayeredPane.PALETTE_LAYER);
        card.add(hideCard, JLayeredPane.PALETTE_LAYER);
        card.add(cardStatus, JLayeredPane.DEFAULT_LAYER);
        return card;
    }

}
