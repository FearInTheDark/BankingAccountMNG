package org.example.Views.CustomizeUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class MyComboBoxUI extends BasicComboBoxUI {

    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton();
        button.setBackground(Color.WHITE);
        button.setIcon(new ImageIcon("path_to_your_icon")); // Set your own icon
        return button;
    }

    @Override
    protected ComboPopup createPopup() {
        return new CustomComboPopup(comboBox);
    }

    private static class CustomComboPopup extends BasicComboPopup {

        public CustomComboPopup(JComboBox combo) {
            super(combo);
        }

        @Override
        protected JScrollPane createScroller() {
            JScrollPane scroller = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scroller.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0)); // Customize scrollbar width
            return scroller;
        }

        @Override
        protected void configureList() {
            super.configureList();
            list.setBackground(Color.WHITE); // Customize list background color
            list.setSelectionBackground(Color.LIGHT_GRAY); // Customize list selection background color
        }
    }
}