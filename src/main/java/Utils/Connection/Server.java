package Utils.Connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private int port = 7777;
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
                System.out.println(socket.getOutputStream());
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
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
                        System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage() + " - " + e.getCause());
                        if (socket.isClosed())
                            System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress());
                    }
                });
                Thread sendThread = new Thread(() -> {
                    while (true) {
                        try {
                            String message = bufferedReader.readLine();
                            if (message.isEmpty()) continue;
                            dataOutputStream.writeUTF(message);
                        } catch (Exception e) {
                            System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage() + " - " + e.getCause());
                        }
                    }
                });

                receiveThread.start();
                sendThread.start();
            }
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage() + " - " + e.getCause());
        }
    }
}
