package org.example.Controllers;

import org.example.Views.LogIn_Frame;

import java.io.IOException;

public class ATM_Controller implements Runnable {

    public ATM_Controller() {
    }

    @Override
    public void run() {
        try {
            new LogIn_Frame();
            new LogIn_Frame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
