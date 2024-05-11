package org.example.Others;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MyCursor extends Cursor {
    public MyCursor(int type) {
        super(type);
    }

    protected MyCursor(String name) {
        super(name);
    }

    public static Cursor getCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/main/java/org/example/Views/icons/ld.cur");
        Point hotSpot = new Point(0, 0);
        return toolkit.createCustomCursor(image, hotSpot, "Cursor");
    }


    public static void setCursor(Component component) {
        component.setCursor(getCursor());
    }
}
