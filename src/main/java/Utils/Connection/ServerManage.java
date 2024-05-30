package Utils.Connection;

import Models.Account;
import lombok.Getter;

import java.net.Socket;

import static Models.SignedAccounts.clients;

@Getter
public class ServerManage {
    private final Socket socket;
    private final Account account;

    public ServerManage(Socket socket) {
        this.socket = socket;
        this.account = clients.get(socket);
    }

}
