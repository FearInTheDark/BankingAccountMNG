package Views;

import Models.Account;
import org.jdesktop.swingx.JXFrame;
import raven.chat.component.ChatArea;
import raven.chat.component.ChatBox;
import raven.chat.model.ModelMessage;
import raven.chat.swing.Background;
import raven.chat.swing.ChatEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ChatFrame_Client extends JXFrame {

    private Background background1;
    private ChatArea chatArea;
    private Point point;
    private Account account;
    private Icon icon1, icon2;
    private Socket socket;


    public ChatFrame_Client(Account account) {
        this.account = account;
        icon1 = new ImageIcon("src/main/resources/icons/LiveChat/p1.png");
        icon2 = new ImageIcon("src/main/resources/icons/LiveChat/p2.png");

        initComponents();
        chatArea.setTitle("WELCOME TO CHAT ROOM");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
        chatArea.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                if (chatArea.getText().trim().isEmpty()) return;
                String name = Arrays.stream(account.getFullName().split(" ")).reduce((first, second) -> second).orElse("");
                String date = df.format(new Date());
                String message = chatArea.getText().trim();
                boolean direction = false;
                if (message.split(" ")[0].equalsIgnoreCase("/l")) {
                    direction = true;
                    message = message.replaceFirst("/l", "").trim();
                }
                chatArea.addChatBox(new ModelMessage(!direction ? icon1 : icon2, name, date, message), !direction ? ChatBox.BoxType.RIGHT : ChatBox.BoxType.LEFT);
                chatArea.clearTextAndGrabFocus();
            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {

            }

            @Override
            public void keyTyped(KeyEvent evt) {
            }
        });
    }

    private void initComponents() {

        background1 = new Background();
        chatArea = new ChatArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        GroupLayout background1Layout = new GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(background1Layout.createSequentialGroup().addContainerGap().addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE).addContainerGap()));
        background1Layout.setVerticalGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(background1Layout.createSequentialGroup().addContainerGap().addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE).addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(background1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(background1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        chatArea.getLabelTitle().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                point = evt.getPoint();
            }
        });

        chatArea.getLabelTitle().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                Point p = getLocation();
                setLocation(p.x + evt.getX() - point.x, p.y + evt.getY() - point.y);
            }
        });

        chatArea.getCmdClose().addActionListener(evt -> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    private void generateConnection() {
        try {
            socket = new Socket("localhost", 7777);
            Thread send = new Thread(() -> {

            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

