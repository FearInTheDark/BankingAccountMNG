package org.example.Views;


import org.example.Data.DB_Manager;
import org.example.Models.Account;
import org.example.Models.SignedAccounts;
import org.example.Others.PlaceHolder.JPlaceholderPasswordField;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static org.example.Utils.Features.toggleLog;

/**
 * <p> This class is used to create the LogIn_Frame.
 * <p> It extends JXFrame in order to create the frame.
 * <p> It contains the components of the frame such as the username, password, login button, and sign up button.
 * <p> It also contains the methods to initialize the components, confirm the input, and validate the input.
 *
 * @see org.example.Models.Account
 * @see org.example.Models.SignedAccounts
 */

public class LogIn_Frame extends JXFrame {
    private final int WIDTH = 1350, HEIGHT = 768;
    private JTextField txtUsername;
    private JPasswordField txtPassword, txtPassword2;
    private JLabel btnLogin;
    private JLabel btnSignUp;
    private JXPanel passwordPanel2;
    private boolean inSignUp = false;

    public LogIn_Frame() throws IOException {
        if (!DB_Manager.isHibernateInitialized) new Thread(DB_Manager::initHibernate).start();

        initComponents();

        ImageIcon frameIcon = new ImageIcon("src/main/java/org/example/Views/icons/Login/frame.png");
        Image image1 = frameIcon.getImage();

        setIconImage(image1);
        setTitle("ATM MANAGEMENT");
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(WIDTH, HEIGHT);
        toggleLog(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * <p> This method initializes the components of the frame.
     * <p> It creates the main icon, the input panel, the info panel, the title panel, the username panel, the password panel, the password panel 2, the button panel, the show password label, and the show password label 2.
     *
     * @see org.example.Views.LogIn_Frame#initComponents()
     */

    private void initComponents() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, WIDTH, HEIGHT);
//        Title icon
        JXLabel mainIcon = new JXLabel();
        ImageIcon icon = new ImageIcon("src/main/java/org/example/Views/icons/Login/BluBank.jpg");
        icon = new ImageIcon(icon.getImage().getScaledInstance(HEIGHT, HEIGHT, Image.SCALE_SMOOTH));
        mainIcon.setIcon(icon);
        mainIcon.setBounds(0, 0, HEIGHT, HEIGHT);

        JXPanel inputPanel = new JXPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBounds(HEIGHT, 0, WIDTH - HEIGHT, HEIGHT);
        inputPanel.setBackground(new Color(80, 172, 206));

        JXPanel infoPanel = new JXPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBounds(HEIGHT, 0, WIDTH - HEIGHT, HEIGHT - 100);
        infoPanel.setBackground(null);
        infoPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));
        titlePanel.setOpaque(false); // Make it transparent

        JLabel lblIcon = new JLabel();
        lblIcon.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/titleBank.png"));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the icon

        JLabel lblTitleText = new JLabel("WELCOME TO VKU BANK");
        lblTitleText.setFont(new Font("Freeman", Font.BOLD, 50));
        lblTitleText.setForeground(Color.WHITE);
        lblTitleText.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text

        titlePanel.add(lblIcon);
        titlePanel.add(lblTitleText);

        JXPanel usernamePanel = new JXPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setOpaque(false);

        JLabel usernameIcon = new JLabel();
        usernameIcon.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/userResized.png"));
        usernameIcon.setPreferredSize(new Dimension(50, 50));

        txtUsername = new JTextField();
        txtUsername.setText("Enter phone number here");
        txtUsername.setMaximumSize(new Dimension(400, 50));
        txtUsername.setMargin(new Insets(0, 10, 0, 0));
        txtUsername.setFont(new Font("Freeman", Font.ITALIC, 24));
        txtUsername.setBorder(null);
        txtUsername.setOpaque(false);
        txtUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        txtUsername.addActionListener(e -> confirm());
        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (txtUsername.getText().equals("Enter phone number here")) {
                    txtUsername.setText("");
                    txtUsername.setFont(new Font("Freeman", Font.PLAIN, 30));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setText("Enter phone number here");
                    txtUsername.setFont(new Font("Freeman", Font.ITALIC, 24));

                }
            }
        });

        JXLabel blank = new JXLabel();
        blank.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/blank.png"));

        JXPanel passwordPanel = new JXPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setOpaque(false);

        JLabel passwordIcon = new JLabel();
        passwordIcon.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/lock_Resized.png"));
        passwordIcon.setPreferredSize(new Dimension(50, 50));

        txtPassword = new JPlaceholderPasswordField("Enter password here");
        txtPassword.setMaximumSize(new Dimension(400, 50));
        txtPassword.setMargin(new Insets(0, 10, 0, 0));
        txtPassword.setFont(new Font("Freeman", Font.ITALIC, 24));
//        txtPassword.setBorder(null);
        txtPassword.setOpaque(false);
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        txtPassword.addActionListener(e -> confirm());

        JXLabel showPassword = getShowLabel(txtPassword);

        passwordPanel2 = new JXPanel();
        passwordPanel2.setLayout(new BoxLayout(passwordPanel2, BoxLayout.X_AXIS));
        passwordPanel2.setOpaque(false);
        passwordPanel2.setVisible(false);

        JLabel passwordIcon2 = new JLabel();
        passwordIcon2.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/lock_Resized.png"));
        passwordIcon2.setPreferredSize(new Dimension(50, 50));

        txtPassword2 = new JPlaceholderPasswordField("Re-enter password here");
        txtPassword2.setMaximumSize(new Dimension(400, 50));
        txtPassword2.setMargin(new Insets(0, 10, 0, 0));
        txtPassword2.setFont(new Font("Freeman", Font.ITALIC, 24));
        txtPassword2.setOpaque(false);
        txtPassword2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        txtPassword2.addActionListener(e -> confirm());

        JXLabel showPassword2 = getShowLabel(txtPassword2);

        usernamePanel.add(usernameIcon);
        usernamePanel.add(txtUsername);
        usernamePanel.add(blank);

        passwordPanel.add(passwordIcon);
        passwordPanel.add(txtPassword);
        passwordPanel.add(showPassword);

        passwordPanel2.add(passwordIcon2);
        passwordPanel2.add(txtPassword2);
        passwordPanel2.add(showPassword2);

        JXPanel btnPanel = new JXPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        btnPanel.setBounds(HEIGHT, HEIGHT - 100, WIDTH - HEIGHT, 100);

        btnLogin = new JLabel();
        btnLogin.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/Login.png"));

        btnSignUp = new JLabel();
        btnSignUp.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/SignUp.png"));

        btnPanel.add(btnLogin);
        btnPanel.add(btnSignUp);

        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(50));
        box.add(titlePanel);
        box.add(Box.createVerticalStrut(50));
        box.add(usernamePanel);
        box.add(Box.createVerticalStrut(30));
        box.add(passwordPanel);
        box.add(Box.createVerticalStrut(30));
        box.add(passwordPanel2);

        infoPanel.add(box, BorderLayout.CENTER);
        infoPanel.add(btnPanel, BorderLayout.PAGE_END);

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnLogin.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/Login_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnLogin.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/Login.png"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (inSignUp) {
                    inSignUp = false;
                    passwordPanel2.setVisible(false);
                    repaint();
                } else confirm();
            }
        });
        btnSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnSignUp.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/SignUp_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnSignUp.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/SignUp.png"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!inSignUp) {
                    inSignUp = true;
                    passwordPanel2.setVisible(true);
                    repaint();
                } else confirm();

            }
        });

        layeredPane.add(mainIcon, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(inputPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(infoPanel, JLayeredPane.MODAL_LAYER);

        add(layeredPane);
    }


    private void confirm() {
        if (txtUsername.getText().equals("admin") && String.valueOf(txtPassword.getPassword()).equals("admin")) {
            Account admin = DB_Manager.queryAccount("0000000000");
            assert admin != null;
            new InApp(admin);
            dispose();

            return;
        }
        if (!validateInput()) return;
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        if (inSignUp) {
            new SignUpSteps(username, password);
            dispose();
            return;
        }
        long startTime = System.currentTimeMillis(); // Start time
        Account account;
        if (SignedAccounts.leftAccounts.containsKey(username)) {
            account = SignedAccounts.leftAccounts.get(username);
            validatePassword(account, password);
            SignedAccounts.addSignedAccountFromLeft(username);
        } else {
            account = DB_Manager.queryAccount(username);
            validatePassword(account, password);
            SignedAccounts.addSignedAccount(account);
        }
        assert account != null;
        new InApp(account);
        dispose();
        long endTime = System.currentTimeMillis(); // End time

        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }


    private JXLabel getShowLabel(JPasswordField txtPassword) {
        JXLabel showLabel = new JXLabel();
        showLabel.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/hide.png"));
        showLabel.setPreferredSize(new Dimension(48, 48));
        showLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (txtPassword.getEchoChar() == (char) 0) {
                    txtPassword.setEchoChar('•');
                    showLabel.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/hide.png"));
                } else {
                    txtPassword.setEchoChar((char) 0);
                    showLabel.setIcon(new ImageIcon("src/main/java/org/example/Views/icons/Login/show.png"));
                }
            }
        });
        return showLabel;
    }

    private boolean validateInput() {
        if (txtUsername.getText().length() != 10) {
            JOptionPane.showMessageDialog(null, "Phone number must have 10 digits!");
            return false;
        }
        if (String.valueOf(txtPassword.getPassword()).length() < 6 || String.valueOf(txtPassword.getPassword()).length() > 20) {
            JOptionPane.showMessageDialog(null, "Password must have 6-20 characters!");
            return false;
        }
        if (inSignUp) {
            if (!String.valueOf(txtPassword.getPassword()).equals(String.valueOf(txtPassword2.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
                return false;
            }
        }
        return true;
    }

    private void validatePassword(Account account, String password) {
        if (account == null || !account.getPassword().equals(password))
            JXErrorPane.showDialog(null, new ErrorInfo("Error", "Account not found!", null, "OK", null, null, null));
    }

}
