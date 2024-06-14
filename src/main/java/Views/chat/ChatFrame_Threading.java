package Views.chat;

import Models.ModelAccount;
import Others.ANSI_COLORS;
import jnafilechooser.api.JnaFileChooser;
import lombok.Getter;
import raven.chat.component.ChatArea;
import raven.chat.model.ModelMessage;
import raven.chat.swing.ChatEvent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Utils.Features.getFirstName;

@Getter
public class ChatFrame_Threading {
    private final ModelAccount modelAccount;
    private final String serverIcon = "/icons/LiveChat/p2.png";
    private final String clientIcon = "/icons/LiveChat/p1.png";
    private final ChatFrame_Client chatFrameClient;
    private final ChatArea chatArea;
    private final String name;
    private Thread receive, connectionThread;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String message;
    private boolean isGenerated = false;
    private boolean accountSent = false;
    private boolean isRunning = true;


    public ChatFrame_Threading(ChatFrame_Client chatFrameClient) {
        this.chatFrameClient = chatFrameClient;
        this.chatArea = chatFrameClient.getChatArea();
        this.modelAccount = chatFrameClient.getModelAccount();
        name = getFirstName(modelAccount);
        init();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
        this.chatArea.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                message = chatArea.getText().trim();
                if (message.isEmpty()) return;
                String date = df.format(new Date());
                boolean direction = message.split(" ")[0].equalsIgnoreCase("/l");
                if (direction) message = message.replaceFirst("/l", "").trim();
                new Thread(() -> {
                    try {
                        message = message.trim();
                        if (message.isEmpty()) return;
                        if (dos == null) dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(message);
                        chatArea.addChatBox(new ModelMessage(direction ? serverIcon : clientIcon, name, date, message, direction));
                    } catch (Exception e) {
                        System.err.println(Thread.currentThread().getName() + " -- Error: " + e.getMessage() + " - " + e.getCause());
                    }
                }).start();

                chatArea.clearTextAndGrabFocus();
            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {
                JnaFileChooser fileChooser = new JnaFileChooser();
                fileChooser.setMode(JnaFileChooser.Mode.Files);
                if (fileChooser.showOpenDialog(null)) {
                    String date = df.format(new Date());
                    String name = "David";
                    chatArea.addChatBox(new ModelMessage(clientIcon, name, date, fileChooser.getSelectedFile().getName()));
                    new Thread(() -> {
                        try {
                            if (dos == null) dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF(fileChooser.getSelectedFile().getAbsolutePath());
                        } catch (Exception e) {
                            System.err.println("Error while sending file: " + e.getMessage() + " - " + e.getCause());
                        }
                    }).start();
                }
            }

            @Override
            public void keyTyped(KeyEvent evt) {
            }
        });
    }

    private void init() {
        generateConnection();
    }

    public void generateConnection() {
        connectionThread = new Thread(this::createConnection);
        receive = new Thread(this::receiveMessage);
        connectionThread.setName("Connection_Thread");
        connectionThread.setPriority(Thread.NORM_PRIORITY);
        receive.setName("Receive_Thread");
        receive.setPriority(Thread.MIN_PRIORITY);
        connectionThread.start();
        receive.start();
        isGenerated = true;
    }

    private synchronized void createConnection() {
        while (isRunning) {
            try {
                socket = new Socket("localhost", 7777);
                sendAccount();
                sendCard();
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                chatFrameClient.getChatArea().openChat(name);
                System.out.println(ANSI_COLORS.GREEN.getCode() + "Connected to server" + ANSI_COLORS.RESET.getCode());
                notify();
                wait();
            } catch (IOException | InterruptedException e) {
                System.err.println("Connection Error, retrying in 3 seconds");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private synchronized void receiveMessage() {
        while (isRunning) {
            try {
                String message = dis.readUTF();
                if (message.equals("exit")) {
                    chatArea.closeChat(name);
                    break;
                }
                message = message.trim().replaceAll("\\s+", " ");
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
                String date = df.format(new Date());
                chatArea.addChatBox(new ModelMessage(serverIcon, name, date, message, true));
            } catch (Exception e) {
                System.err.println("Receive Error, ready to reconnect");
                chatArea.closeChat(name);
                notify();
                try {
                    wait();
                } catch (InterruptedException ex) {
                    System.out.println("Error: " + ex.getMessage() + " - " + ex.getCause());
                }
            }
        }
    }

    private void sendAccount() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(modelAccount);
            accountSent = true;
            oos.flush();
            System.out.println("Account sent");
        } catch (IOException e) {
            System.out.println("Failed to send account");
        }
    }

    private void sendCard() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(modelAccount.getModelCard());
            oos.flush();
            System.out.println("Card sent");
        } catch (IOException e) {
            System.out.println("Failed to send card");
        }
    }

    public void closeConnection() throws IOException {
        isRunning = false;
        socket.close();
        dis.close();
        dos.close();
        if (connectionThread != null) connectionThread.interrupt();
        if (receive != null) receive.interrupt();
        chatFrameClient.dispose();
    }
}
