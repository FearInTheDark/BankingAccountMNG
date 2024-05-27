package org.example.Views;

import org.example.Data.DB_Manager;
import org.example.Models.Account;
import org.example.Models.Card;
import org.example.Models.SignedAccounts;
import org.example.Others.DraggableIcon;
import org.example.Views.CustomizeUI.MySplitPaneUI;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;

import static org.example.Utils.Features.toggleLog;

public class InApp extends JXFrame {
    String[] quickAccessesText = {"Dashboard", "Accounts", "Transactions", "Cards", "Saving", "Manage Accounts"};
    String[] qAIcons = {"Dashboard.png", "Accounts.png", "Transactions.png", "Cards.png", "Saving.png", "ManageAccount.png"};
    private Account account;
    private Card card;
    private JLabel logo, welcome, bgPic;
    private JSplitPane splitPane;
    private JLayeredPane bgPanel, mainLayeredPane, featuresPanel;
    private JPanel menuPanel;
    private int frameWidth = 1600, frameHeight = 1100;
    private int menuWidth = 400, mainWidth = 1200, mainHeight = frameHeight;
    private Icon frameIcon, bgIcon;
    private Box box;
    private FunctionsGUI fGUI;
    private boolean isAdmin = false;

    public InApp() {
    }

    public InApp(Account account) {
        this.account = account;
        isAdmin = (account.getFullName().equalsIgnoreCase("Admin"));
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
                frameWidth = getWidth();
                frameHeight = mainHeight = getHeight();
                mainWidth = frameWidth - menuWidth;
            }
        });
        ImageIcon icon = new ImageIcon("src/main/java/org/example/Views/icons/Login/frame.png");
        setIconImage(icon.getImage());
        toggleLog(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void init() {
        splitPane = new JSplitPane();

        setAccountAvatar();

        createMenuPanel();
        createMainPanel();

        splitPane.setLeftComponent(menuPanel);
        splitPane.setRightComponent(mainLayeredPane);
        splitPane.setDividerSize(5);
        splitPane.setUI(new MySplitPaneUI());
        splitPane.setDividerLocation(400);
    }

    private void setAccountAvatar() {
        ImageIcon avatar = new ImageIcon("src/main/java/org/example/Views/icons/InApp/Avatar.png");
    }

    private void createMainPanel() {
        this.bgPanel = new JLayeredPane();
        this.mainLayeredPane = new JLayeredPane();
        bgPanel.setLayout(new BorderLayout(5, 5));
        bgPanel.setPreferredSize(new Dimension(mainWidth, frameHeight));

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
            JLayeredPane f = new JLayeredPane();
            f.setPreferredSize(new Dimension(mainWidth / 3 - 30, 160));

            ImageIcon funcFrame = new ImageIcon("src/main/java/org/example/Views/icons/InApp/functions.png");
            Icon funcFrameResize = new ImageIcon(funcFrame.getImage().getScaledInstance(mainWidth / 3 - 30, 150, Image.SCALE_SMOOTH));
            JXLabel frameLabel = new JXLabel();
            frameLabel.setIcon(funcFrameResize);
            frameLabel.setPreferredSize(new Dimension(mainWidth / 3 - 30, 160));
            frameLabel.setHorizontalAlignment(JLabel.CENTER);
            frameLabel.setVerticalAlignment(JLabel.CENTER);
            frameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            ImageIcon functionIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/FunctionIcons/" + text.toLowerCase() + ".png");
            Icon funcIconResize = new ImageIcon(functionIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            JLabel label = new JLabel();
            label.setIcon(funcIconResize);
            label.setText(text);
            label.setFont(new Font("Segoe UI", Font.BOLD, 25));
            label.setIconTextGap(10);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setPreferredSize(new Dimension(mainWidth / 3 - 30, 160));
            label.setToolTipText("Click to " + text);

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
                    label.setForeground(new Color(211, 129, 34));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    String removeUnderlineText = "<html>" + text + "</html>";
                    label.setText(removeUnderlineText);
                    label.setForeground(Color.BLACK);
                }
            });

            f.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    label.setBounds(0, 0, f.getWidth(), f.getHeight());
                    frameLabel.setBounds(0, 0, f.getWidth(), f.getHeight());
                }
            });

            f.add(frameLabel, JLayeredPane.DEFAULT_LAYER);
            f.add(label, JLayeredPane.PALETTE_LAYER);

            functions.add(f);
        });

        bgPanel.add(background, BorderLayout.CENTER);
        bgPanel.add(functions, BorderLayout.SOUTH);

        DraggableIcon draggableIcon = new DraggableIcon(new ImageIcon("src/main/java/org/example/Views/icons/InApp/robot.png"));
        draggableIcon.setAccount(account);
        draggableIcon.setBounds(1000, mainHeight /2 + 50, 100, 100);


        mainLayeredPane.add(bgPanel, JLayeredPane.FRAME_CONTENT_LAYER);
        mainLayeredPane.add(draggableIcon, JLayeredPane.DRAG_LAYER);

        splitPane.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                menuWidth = splitPane.getDividerLocation();
                mainWidth = frameWidth - menuWidth;
            }
        });

        bgPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                mainHeight = bgPanel.getHeight();
                mainWidth = bgPanel.getWidth();
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
                bgPanel.setBounds(0, 0, mainWidth, mainHeight);
                if (draggableIcon.getX() + 100 > mainWidth) {
                    draggableIcon.setLocation(mainWidth - 100, draggableIcon.getY());
                }
            }
        });

    }

    private void createMenuPanel() {
        this.menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(51, 117, 138));
        menuPanel.setMinimumSize(new Dimension(400, 0));

        logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/CircleLogo.png");
        Icon logoIconResize = new ImageIcon(logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
        logo.setIcon(logoIconResize);
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
        logo.setPreferredSize(new Dimension(400, 400));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Welcome Panel
        welcome = new JLabel();
        String name = account.getFullName() != null ? account.getFullName() : "";
        String welcomeText = "<html><div style='text-align: center;'>Welcome <br/>" + name + "</div></html>";
        welcome.setText(welcomeText);
        welcome.setFont(new Font("Segeo UI", Font.BOLD, 30));
        welcome.setForeground(Color.WHITE);
        welcome.setVerticalAlignment(JLabel.CENTER);
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
//        welcome.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        box = getBox();

        JXPanel quickAccesses = new JXPanel();
        quickAccesses.setOpaque(false);
        quickAccesses.setLayout(new BoxLayout(quickAccesses, BoxLayout.X_AXIS));
        quickAccesses.add(box);

        JPanel others = new JPanel();
        others.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 0));
        others.setOpaque(false);
//        others.setPreferredSize(new Dimension(400, 100));

        JLabel settings = new JLabel();
        settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon settingsIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/settings.png");
        Icon settingsIconResize = new ImageIcon(settingsIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        settings.setIcon(settingsIconResize);
        settings.setPreferredSize(new Dimension(50, 50));
        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                splitPane.setRightComponent(fGUI.getMenuGUI(bankNo));
                splitPane.setDividerLocation(400);
            }
        });

        JLabel logout = new JLabel();
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon logoutIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/logout_red.png");
        Icon logoutIconResize = new ImageIcon(logoutIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        logout.setIcon(logoutIconResize);
        logout.setPreferredSize(new Dimension(50, 50));
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new LogIn_Frame();
                    SignedAccounts.addLeftAccountFromSigned(account.getPhoneNo());
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon logoutIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/logout_red_hover.png");
                Icon logoutIconResize = new ImageIcon(logoutIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                logout.setIcon(logoutIconResize);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                logout.setIcon(logoutIconResize);
            }
        });
        logout.setToolTipText("Logout");

        others.add(logout);
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
            ImageIcon qAIcon = new ImageIcon("src/main/java/org/example/Views/icons/InApp/" + quickAccessesText[i] + ".png");
            Icon qAIconResize = new ImageIcon(qAIcon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));

            JLabel label = new JLabel();
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.setIcon(qAIconResize);
            label.setText(quickAccessesText[i]);
            label.setFont(new Font("Arial", Font.BOLD, 25));

            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setIconTextGap(10);
            label.setPreferredSize(new Dimension(300, 100));
            final int index = i;
            label.addMouseListener(new MouseAdapter() {
                final String text = label.getText();

                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (index) {
                        case 0:
                            splitPane.setRightComponent(mainLayeredPane);
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
                            revalidate();
                            repaint();
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
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
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

            label.addMouseListener(new MouseAdapter() {

            });
            box.add(label);
        }
        box.add(Box.createVerticalGlue());
        return box;
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
}
