package Utils.Connection;

public class ServerCommands {
    private final Server server;
    public ServerCommands(Server server) {
        this.server = server;
    }

    public void executeCommand(String message) {
        String[] command = message.substring(1).split(" ");
        switch (command[0]) {
            case "update", "change"  -> {

            }
            default -> System.out.println("Command not found");
        }
    }
}
