import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import Views.LogIn_Frame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        FlatLaf.registerCustomDefaultsSource("FlatLaf.theme");
        FlatRobotoFont.install();
        FlatMacLightLaf.setup();
        new LogIn_Frame();
    }
}