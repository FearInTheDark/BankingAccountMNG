package Utils.Connection;

import java.net.Socket;

public class ConnectionThread extends Thread {
    private Socket socket;
    private final Object lock = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    socket = new Socket("localhost", 7777);
                    lock.wait();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        }
    }

    public void waitConnection() {
        synchronized (lock) {
            lock.notify();
        }
    }
}
