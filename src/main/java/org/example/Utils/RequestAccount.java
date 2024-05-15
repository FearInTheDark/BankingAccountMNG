package org.example.Utils;

import org.example.Data.DB_Manager;
import org.example.Models.Account;

import java.util.concurrent.*;

public class RequestAccount {

    public static Account requestAccount(String userName, String password) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Account> task = () -> {
            Account account = DB_Manager.queryAccount(userName);
            return (account == null || !account.getPassword().equals(password)) ? null : account;
        };

        Future<Account> future = executor.submit(task);

        return future.get();
    }
}
