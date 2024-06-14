package Utils.Connection;

import Data.DB_Manager;
import Models.ModelAccount;
import Models.ModelCard;
import lombok.Getter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static Models.SignedAccounts.clients;
import static Utils.Features.getFirstName;

@Getter
public class Server {
    private final int port = 7777;
    private ModelAccount modelAccount;
    private ServerSocket serverSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private BufferedReader bufferedReader;
    private Socket socket;

    public Server() {
        DB_Manager.initHibernate();
        generateServer();
    }

    public static void main(String[] args) {
        new Server();
    }

    private void generateServer() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("Server is running on port " + port);
            while (true) {
                System.out.println("Waiting for client...");
                socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                assignAccount();
                assignCard();
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                Thread receiveThread = new Thread(() -> {
                    try {
                        while (true) {
                            String message = dataInputStream.readUTF();
                            if (message.equals("exit")) {
                                System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress());
                                socket.close();
                                break;
                            } else if (message.startsWith("/")) {
                                handleMessage(message);
                            }
                            System.out.println(getFirstName(modelAccount) + ": " + message);
                        }
                    } catch (Exception e) {
                        System.err.println("Client disconnected: " + socket.getInetAddress().getHostAddress() + " - " + e.getMessage() + " - " + e.getCause());
                    }
                });
                Thread sendThread = new Thread(() -> {
                    while (true) {
                        try {
                            String message = bufferedReader.readLine();
                            if (message.isEmpty()) continue;
                            send(message);
                        } catch (Exception e) {
                            System.err.println("Send_Thread" + " Error: " + e.getMessage() + " - " + e.getCause());
                        }
                    }
                });

                receiveThread.start();
                sendThread.start();
            }
        } catch (Exception e) {
            System.err.println("Main_Thread: " + " Error: " + e.getMessage() + " - " + e.getCause());
        }
    }

    private void handleMessage(String message) {
        try {
            String[] split = message.split(" ");
            if (split.length < 2) return;
            switch (split[0].toLowerCase()) {
                case "/changepassword" -> {
                    String password = split[1];
                    if (password.length() < 8) {
                        send("Password must be at least 6 characters long.");
                    } else {
                        modelAccount.setPassword(password);
                        DB_Manager.updateAccount(modelAccount);
                        send("Password changed successfully.");
                    }
                }
                case "/changefullname" -> {
                    String fullName = String.join(" ", split).replace("/changefullname ", "");
                    if (fullName.matches(".*\\d.*") || !fullName.matches("^[a-zA-Z\\s]*$")) {
                        send("Full name must not contain numbers or special characters.");
                    } else {
                        modelAccount.setFullName(fullName);
                        DB_Manager.updateAccount(modelAccount);
                        send("Full name changed successfully.");
                    }
                }
                case "/changephone", "/changephoneno" -> {
                    String phoneNo = split[1];
                    if (phoneNo.length() < 10) {
                        send("Phone number must be at least 10 characters long.");
                    } else {
                        modelAccount.setPhoneNo(phoneNo);
                        DB_Manager.updateAccount(modelAccount);
                        send("Phone number changed successfully.");
                    }
                }
                case "/changepin" -> {
                    String pin = split[1];
                    if (pin.length() != 4) {
                        send("PIN must be at least 4 characters long.");
                    } else {
                        modelAccount.getModelCard().setPin(pin);
                        DB_Manager.updateCard(modelAccount.getModelCard());
                        send("PIN changed successfully.");
                    }
                }
                case "/deleteaccount" -> {
                    send("Are you sure you want to delete your account? (Y/N)");
                    String response = bufferedReader.readLine();
                    if (response.equalsIgnoreCase("y")) {
                        clients.remove(socket);
                        send("Account deleted successfully.");
                    } else {
                        send("Account deletion cancelled.");
                    }
                }
                case "/changebankno" -> {
                    String bankNo = split[1];
                    if (bankNo.length() != 14) {
                        send("Bank number must be 14 characters long.");
                    } else {
                        modelAccount.setId(bankNo);
                        modelAccount.getModelCard().setBankNo(bankNo);
                        DB_Manager.updateAccount(modelAccount);
                        DB_Manager.updateCard(modelAccount.getModelCard());
                        send("Bank number changed successfully.");
                    }
                }
                default -> send("Command not found.");
            }
        } catch (Exception e) {
            System.err.println("Error while handling message: " + e.getMessage() + " - " + e.getCause());
        }
    }

    private void assignAccount() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            modelAccount = (ModelAccount) objectInputStream.readObject();
            if (!clients.containsValue(modelAccount)) {
                clients.put(socket, modelAccount);
                System.out.println("Account assigned: " + modelAccount.getFullName());
            }
        } catch (IOException e) {
            System.err.println("Error while assigning account: " + e.getMessage() + " - " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println("Error while casting object: " + e.getMessage() + " - " + e.getCause());
        } catch (Exception e) {
            System.err.println("Error assigning account: " + e.getMessage() + " - " + e.getCause());
        }
    }

    private void assignCard() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            modelAccount.setModelCard((ModelCard) objectInputStream.readObject());
        } catch (IOException e) {
            System.err.println("Error while assigning card: " + e.getMessage() + " - " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println("Error while casting object: " + e.getMessage() + " - " + e.getCause());
        } catch (Exception e) {
            System.err.println("Error assigning card: " + e.getMessage() + " - " + e.getCause());
        }
    }

    private void send(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.err.println("Error while sending message: " + e.getMessage() + " - " + e.getCause());
        }
    }
}
