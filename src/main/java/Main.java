import Views.app.LogIn_Frame;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.io.IOException;
import java.text.ParseException;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        FlatLaf.registerCustomDefaultsSource("FlatLaf.theme");
        FlatRobotoFont.installBasic();
        FlatMacLightLaf.setup();

        new LogIn_Frame();

    }
}