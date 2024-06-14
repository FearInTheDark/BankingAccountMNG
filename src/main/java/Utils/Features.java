package Utils;

import Models.ModelAccount;
import Models.SignedAccounts;
import org.jdesktop.swingx.JXFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class Features {
    public static void toggleLog(JXFrame frame) {
        JRootPane rootPane = frame.getRootPane();
        ActionMap actionMap = rootPane.getActionMap();
        actionMap.put("toggleLog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(SignedAccounts.signedAccounts.keySet());
                System.out.println(SignedAccounts.leftAccounts.keySet());
            }
        });
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("control alt L"), "toggleLog");
    }

    public static String getFirstName(ModelAccount modelAccount) {
        return Arrays.stream(modelAccount.getFullName().split(" ")).reduce((first, second) -> second).orElse("");
    }
}

