package org.example.Views.CustomizeUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class MyScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = Color.GRAY;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(null);
        button.setBorder(null);
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(null);
        button.setBorder(null);
        return button;
    }
}
