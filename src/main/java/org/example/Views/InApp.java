package org.example.Views;

import org.example.Data.DB_Manager;
import org.example.Models.Account;
import org.example.Models.Card;
import org.example.Others.DraggableIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;

public class InApp extends JFrame {
    private final boolean isMinimized = false;
    String[] quickAccessesText = {"Dashboard", "Accounts", "Transactions", "Cards", "Saving", "Manage Account"};
    String[] qAIcons = {"Dashboard.png", "Accountss.png", "Transactionss.png", "CreCard.png", "Saving.png", "ManageAccount.png"};
    private Account account;
    private Card card;
    private JLabel logo, welcome, bgPic, frameLabel;
    private JSplitPane splitPane;
    private JLayeredPane mainPanel, bankInfo, mainLayeredPane;
    private JPanel menuPanel;
    private int frameWidth = 1600, frameHeight = 1100;
    private int menuWidth = 400, mainWidth = 1200, mainHeight = frameHeight;
    private String bankNum;
    private Icon frameIcon, bgIcon;
    private Box box;
    private FunctionsGUI fGUI;
    private boolean isHiddenBalance = false;
    private boolean isHiddenCard = false;
    private boolean isHidden = false;
    private boolean isAdmin = false;

    public InApp() {
    }

    public InApp(Account account) {
        this.account = account;
        isAdmin = (account.getFullName().equals("Admin"));
        card = DB_Manager.queryCard(account.getCardNumber());
        fGUI = new FunctionsGUI(account, card);
        init();

        add(splitPane);
        setTitle("Banking Management System");
        setSize(frameWidth, frameHeight);
        setMinimumSize(new Dimension(frameWidth, frameHeight));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void init() {
        splitPane = new JSplitPane();
        splitPane.setDividerLocation(400);

        createMenuPanel();
        createMainPanel();

        splitPane.setLeftComponent(menuPanel);
        splitPane.setRightComponent(mainLayeredPane);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(400);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                mainWidth = width - menuWidth;
                mainHeight = height;
            }
        });
    }

    private void createMainPanel() {
        this.mainPanel = new JLayeredPane();
        this.mainLayeredPane = new JLayeredPane();
        mainLayeredPane.setBackground(Color.cyan);
        mainLayeredPane.setOpaque(false);
        mainPanel.setLayout(new BorderLayout(5, 5));
//        mainPanel.setPreferredSize(new Dimension(mainWidth, frameHeight));
        mainPanel.setBounds(0, 0, mainWidth, mainHeight);
//        mainPanel.setBackground(Color.WHITE);

        JLayeredPane background = new JLayeredPane();
        background.setLayout(null);

        bgPic = new JLabel();
        ImageIcon bg = new ImageIcon("src/main/java/org/example/Views/icons/InApp/streets.jpg");
        bgIcon = new ImageIcon(bg.getImage().getScaledInstance(mainWidth, 600, Image.SCALE_SMOOTH));
        bgPic.setIcon(bgIcon);
        bgPic.setPreferredSize(new Dimension(mainWidth, 600));
        bgPic.setBounds(0, 0, mainWidth, 600);

        JLayeredPane cardPane = fGUI.getCard();
        cardPane.setBounds((mainWidth - 743) / 2, (600) / 2 + 150, 743, 233);
        JLabel showBankAcc = new JLabel();
        showBankAcc.setText("Show all accounts");
        showBankAcc.setFont(new Font("Arial", Font.ITALIC, 15));
        showBankAcc.setBounds(20, 160, 150, 30);
        showBankAcc.addMouseListener(new MouseAdapter() {
            final String text = showBankAcc.getText();

            @Override
            public void mouseEntered(MouseEvent e) {
                String underlineText = "<html><u>" + text + "</u></html>";
                showBankAcc.setText(underlineText);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showBankAcc.setText("Show all accounts");
            }
        });

        JLabel showTransactions = new JLabel();
        showTransactions.setText("Show all transactions");
        showTransactions.setFont(new Font("Arial", Font.ITALIC, 15));
        showTransactions.setBounds(20, 190, 150, 30);
        showTransactions.addMouseListener(new MouseAdapter() {
            final String text = showTransactions.getText();

            @Override
            public void mouseClicked(MouseEvent e) {
                JLayeredPane listTransactions = fGUI.getListTransactions(mainWidth, mainHeight);
                splitPane.setRightComponent(listTransactions);
                splitPane.setDividerLocation(400);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                String underlineText = "<html><u>" + text + "</u></html>";
                showTransactions.setText(underlineText);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showTransactions.setText("Show all transactions");
            }
        });

        cardPane.add(showBankAcc, JLayeredPane.PALETTE_LAYER);
        cardPane.add(showTransactions, JLayeredPane.PALETTE_LAYER);

        background.add(bgPic, JLayeredPane.DEFAULT_LAYER);
        background.add(cardPane, JLayeredPane.DRAG_LAYER);

        JPanel functions = new JPanel(new GridLayout(2, 3, 5, 5));

        String[] functionsText = {"Transfer", "Card Services", "Payment", "Phone Top-up", "Saving", "Account Nickname"};
        Arrays.stream(functionsText).forEach(text -> {

            ImageIcon functionIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/functions.png");
            Icon functionIconResize = new ImageIcon(functionIcon.getImage().getScaledInstance(mainWidth / 3 - 30, 150, Image.SCALE_SMOOTH));


            JLabel label = new JLabel();
            label.setIcon(functionIconResize);
            label.setText(text);
            label.setFont(new Font("Segoe UI", Font.BOLD, 20));

            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setIconTextGap(-50);
            label.setPreferredSize(new Dimension(mainWidth / 3 - 30, 160));

            label.addMouseListener(new MouseAdapter() {
                final String text = label.getText();

                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (text) {
                        case "Transfer":
//                            JLayeredPane transfer = fGUI.getTransferGUI(bankNo);
//                            splitPane.setRightComponent(transfer);
                            splitPane.setDividerLocation(400);
                            break;
                        case "Card Services":
//                            JLayeredPane cardServices = fGUI.CardInfo();
//                            splitPane.setRightComponent(cardServices);
                            splitPane.setDividerLocation(400);
                            break;
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    String underlineText = "<html><u>" + text + "</u></html>";
                    label.setText(underlineText);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    String removeUnderlineText = "<html>" + text + "</html>";
                    label.setText(removeUnderlineText);
                }
            });
            functions.add(label);
        });

        mainPanel.add(background, BorderLayout.CENTER);
        mainPanel.add(functions, BorderLayout.SOUTH);

        DraggableIcon draggableIcon = new DraggableIcon(new ImageIcon("src/main/java/org/example/Views/icons/InApp/robot.png"));
        draggableIcon.setBounds(9, 9, 100, 100);


        mainLayeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        mainLayeredPane.add(draggableIcon, JLayeredPane.DRAG_LAYER);

        splitPane.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                menuWidth = splitPane.getDividerLocation();
                mainWidth = frameWidth - menuWidth;
            }
        });

        mainPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                mainHeight = mainPanel.getHeight();
                mainWidth = mainPanel.getWidth();
                frameWidth = getWidth();
                frameHeight = getHeight();
                bgIcon = new ImageIcon(bg.getImage().getScaledInstance(mainWidth, mainWidth / 2, Image.SCALE_DEFAULT));
                bgPic.setIcon(bgIcon);
                bgPic.setBounds(0, 0, mainWidth, mainWidth / 2);
                cardPane.setBounds((mainWidth - 743) / 2, mainHeight - functions.getHeight() - cardPane.getHeight() - 10, 743, 233);
            }
        });

        mainLayeredPane.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                super.componentResized(evt);
                mainHeight = mainLayeredPane.getHeight();
                mainWidth = mainLayeredPane.getWidth();
                mainPanel.setBounds(0, 0, mainWidth, mainHeight);
            }
        });

    }

    private void createMenuPanel() {
        this.menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(76, 173, 206));
        menuPanel.setMinimumSize(new Dimension(400, 0));

        logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/CircleLogo.png");
        Icon logoIconResize = new ImageIcon(logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
        logo.setIcon(logoIconResize);
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
        logo.setPreferredSize(new Dimension(300, 300));

        welcome = new JLabel();
        String name = account.getFullName() != null ? account.getFullName() : "";
        String welcomeText = "<html><div style='text-align: center;'>Welcome <br/>" + name + "</div></html>";
        welcome.setText(welcomeText);
        welcome.setFont(new Font("Segeo UI", Font.BOLD, 30));
        welcome.setForeground(Color.WHITE);
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setVerticalAlignment(JLabel.CENTER);
        welcome.setPreferredSize(new Dimension(300, 100));

        JPanel quickAccesses = new JPanel();
        quickAccesses.setLayout(new BoxLayout(quickAccesses, BoxLayout.Y_AXIS));
        quickAccesses.setBackground(new Color(76, 173, 206));
        quickAccesses.setPreferredSize(new Dimension(300, 700));

        box = getBox();
        quickAccesses.add(box);

        JPanel others = new JPanel();
        others.setLayout(new FlowLayout());
        others.setOpaque(false);
        others.setPreferredSize(new Dimension(400, 100));

        JLabel settings = new JLabel();
        ImageIcon settingsIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/settings.png");
        Icon settingsIconResize = new ImageIcon(settingsIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        settings.setIcon(settingsIconResize);
        settings.setPreferredSize(new Dimension(48, 48));

        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                splitPane.setRightComponent(fGUI.getMenuGUI(bankNo));
                splitPane.setDividerLocation(300);
            }
        });

        JLabel logout = new JLabel();
        ImageIcon logoutIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/log-out.png");
        Icon logoutIconResize = new ImageIcon(logoutIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        logout.setIcon(logoutIconResize);
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new LogIn_Frame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        others.add(settings);

        menuPanel.add(logo);
        menuPanel.add(welcome);
        menuPanel.add(quickAccesses);
        menuPanel.add(others);
    }

    private Box getBox() {
        Box box = new Box(BoxLayout.Y_AXIS);
        Box.createVerticalBox();
        box.add(Box.createVerticalGlue());

        for (int i = 0; i < (isAdmin ? 6 : 5); i++) {
            ImageIcon qAIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/" + qAIcons[i]);
            Icon qAIconResize = new ImageIcon(qAIcon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));

            JLabel label = new JLabel();
            label.setIcon(qAIconResize);
            label.setText(quickAccessesText[i]);
            label.setFont(new Font("Arial", Font.BOLD, 20));

            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setIconTextGap(10);
            label.setPreferredSize(new Dimension(300, 100));
            label.setSize(new Dimension(300, 100));
            final int index = i;
            label.addMouseListener(new MouseAdapter() {
                final String text = label.getText();

                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (index) {
                        case 0:
                            splitPane.setRightComponent(mainPanel);
                            splitPane.setDividerLocation(400);
                            break;
                        case 1:
                            JLayeredPane accounts = fGUI.AccountInformation(mainWidth, mainHeight);
                            splitPane.setRightComponent(accounts);
                            splitPane.setDividerLocation(400);
                            break;
                        case 2:
                            JLayeredPane listTransactions = fGUI.getListTransactions(mainWidth, mainHeight);
                            splitPane.setRightComponent(listTransactions);
                            splitPane.setDividerLocation(400);
                            break;
                        case 3:
//                            JLayeredPane cards = fGUI.CardInfo();
//                            splitPane.setRightComponent(cards);
                            splitPane.setDividerLocation(400);
                            break;
                        case 5:
//                            new ManageFrame();
//                            splitPane.setDividerLocation(300);
                            break;
                    }
                }


                @Override
                public void mouseEntered(MouseEvent e) {
                    String underlineText = "<html><u>" + text + "</u></html>";
                    label.setText(underlineText);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setText(text);
                }
            });
            box.add(label);
        }
        box.add(Box.createVerticalGlue());
        return box;
    }

    private Box getSmallBox() {
        Box box = new Box(BoxLayout.Y_AXIS);
        Box.createVerticalBox();
        box.add(Box.createVerticalGlue());

        for (int i = 0; i < 5; i++) {
            ImageIcon qAIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/" + qAIcons[i]);
            Icon qAIconResize = new ImageIcon(qAIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

            JLabel label = new JLabel();
            label.setIcon(qAIconResize);

            label.setPreferredSize(new Dimension(50, 50));
            label.setSize(new Dimension(50, 50));
//            final String text = quickAccessesText[i];
//            final int index = i;

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {


                }

            });
            box.add(label);
        }
        box.add(Box.createVerticalGlue());
        return box;
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

    private void minimizeQA() {
        splitPane.setDividerLocation(50);
        box = getSmallBox();
        logo.setVisible(false);
        welcome.setVisible(false);
        repaint();
    }

    private void maximizeQA() {
        splitPane.setDividerLocation(300);
        box = getBox();
        logo.setVisible(true);
        welcome.setVisible(true);
        repaint();
    }

    public JLabel getBgPic() {
        return bgPic;
    }

    public JLayeredPane getBankInfo() {
        return bankInfo;
    }

    public JLabel getFrameLabel() {
        return frameLabel;
    }

    public JLabel getLogo() {
        return logo;
    }

    public JLabel getWelcome() {
        return welcome;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }
}
