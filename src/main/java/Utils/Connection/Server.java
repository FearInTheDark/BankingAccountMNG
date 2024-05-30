package Utils.Connection;

import Models.Account;
import lombok.Getter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static Models.SignedAccounts.clients;

@Getter
public class Server {
    private ServerSocket serverSocket;
    private final int port = 7777;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private BufferedReader bufferedReader;
    private Socket socket;

    public Server() {
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
                socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
//                assignAccount();
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
                            }
                            System.out.println("Client: " + message);
                        }
                    } catch (Exception e) {
                        System.err.println("Receive_Thread:" + " Error: " + e.getMessage() + " - " + e.getCause());
                        if (socket.isClosed())
                            System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress());
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

    private void assignAccount() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Account account = (Account) objectInputStream.readObject();
            if (clients.containsValue(account)) return;
            System.out.println(account.getFullName());
            if (!clients.containsValue(account)) {
                clients.put(socket, account);
                System.out.println("Account assigned: " + account.getFullName());
                System.out.println(clients.size());
            }
        } catch (IOException e) {
            System.err.println("Error while assigning account: " + e.getMessage() + " - " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println("Error while casting object: " + e.getMessage() + " - " + e.getCause());
        } catch (Exception e) {
            System.err.println("Error assigning account: " + e.getMessage() + " - " + e.getCause());
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
