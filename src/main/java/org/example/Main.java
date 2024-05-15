package org.example;

import org.example.Views.LogIn_Frame;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
//        Account account = DB_Manager.queryAccount("12345678979799");
//        assert account != null;
//        new InApp(account);
//        ATM_Controller atm_controller = new ATM_Controller();
//        atm_controller.run();
        new LogIn_Frame();

    }
}