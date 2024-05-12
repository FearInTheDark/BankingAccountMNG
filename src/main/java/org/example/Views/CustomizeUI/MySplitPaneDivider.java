package org.example.Views.CustomizeUI;

import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class MySplitPaneDivider extends BasicSplitPaneDivider {
    public MySplitPaneDivider(BasicSplitPaneUI ui) {
        super(ui);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0xadadad)); // Change the color as per your requirement
        g.fillRect(0, 0, getSize().width, getSize().height);
    }
}
