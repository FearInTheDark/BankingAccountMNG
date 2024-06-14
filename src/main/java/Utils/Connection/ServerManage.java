package Utils.Connection;

import Models.ModelAccount;
import lombok.Getter;

import java.net.Socket;

import static Models.SignedAccounts.clients;

@Getter
public class ServerManage {
    private final Socket socket;
    private final ModelAccount modelAccount;

    public ServerManage(Socket socket) {
        this.socket = socket;
        this.modelAccount = clients.get(socket);
    }

}
