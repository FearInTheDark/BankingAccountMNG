import Views.login.LogIn_Frame;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        FlatLaf.registerCustomDefaultsSource("FlatLaf.theme");
        FlatRobotoFont.install();
        FlatMacLightLaf.setup();
//        new ChatFrame_Client(new Account("", "David James", "", "", "", "", "", "", "", null)).setVisible(true);
        new LogIn_Frame();
    }
}