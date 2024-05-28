package Views;

import Models.Account;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jdesktop.swingx.JXFrame;
import raven.chat.component.ChatArea;
import raven.chat.model.ModelMessage;
import raven.chat.swing.Background;
import raven.chat.swing.ChatEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ChatFrame_Client extends JXFrame {
    private Background background1;
    private ChatArea chatArea;
    private Point point;
    private Account account;
    private Socket socket;
    private Thread receive, connectionThread;
    private String message;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String serverIcon = "/icons/LiveChat/p2.png";
    private String clientIcon = "/icons/LiveChat/p1.png";
    private String name;
    private GoogleMaterialIcon warningIcon = new GoogleMaterialIcon(GoogleMaterialDesignIcon.WARNING, GradientType.VERTICAL, new Color(217, 19, 19), new Color(222, 71, 71), 35);
    private GoogleMaterialIcon availIcon = new GoogleMaterialIcon(GoogleMaterialDesignIcon.WIFI, GradientType.VERTICAL, new Color(32, 166, 33), new Color(30, 150, 32), 35);

    public ChatFrame_Client(Account account) {
        this.account = account;
        initComponents();
        generateConnection();

        name = Arrays.stream(account.getFullName().split(" ")).reduce((first, second) -> second).orElse("");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
        chatArea.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                if (chatArea.getText().trim().isEmpty()) return;
                message = chatArea.getText().trim();
                String date = df.format(new Date());
                boolean direction = message.split(" ")[0].equalsIgnoreCase("/l");
                if (direction) message = message.replaceFirst("/l", "").trim();
                chatArea.addChatBox(new ModelMessage(direction ? serverIcon : clientIcon, name, date, message, direction));
                new Thread(() -> {
                    try {
                        if (message.isEmpty()) return;
                        if (dos == null) dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(message);
                    } catch (Exception e) {
                        System.err.println(Thread.currentThread().getName() + " -- Error: " + e.getMessage() + " - " + e.getCause());
                    }
                }).start();

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
        setAlwaysOnTop(true);

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
        connectionThread = new Thread(this::createConnection);
        receive = new Thread(this::receiveMessage);
        connectionThread.setName("Connection Thread");
        connectionThread.start();
        receive.setName("Receive Thread");
        receive.start();
    }

    private synchronized void createConnection() {
        while (true) {
            try {
                socket = new Socket("localhost", 7777);
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                chatArea.openChat(name);
                notify();
                wait();
            } catch (IOException | InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " -- Error: " + e.getMessage() + " - " + e.getCause());
            }
        }
    }

    private synchronized void receiveMessage() {
        while (true) {
            try {
                String message = dis.readUTF();
                if (message.equals("exit")) {
                    chatArea.closeChat(name);
                    break;
                }
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
                String date = df.format(new Date());
                chatArea.addChatBox(new ModelMessage(serverIcon, name, date, message, true));
            } catch (Exception e) {
                System.err.println(Thread.currentThread().getName() + " -- Error: " + e.getMessage() + " - " + e.getCause());
                chatArea.closeChat(name);
                notify();
                try {
                    Thread.sleep(3000);
                    wait();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void closeConnection() throws IOException {
        socket.close();
        dis.close();
        dos.close();
    }
}

