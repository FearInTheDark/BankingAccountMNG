package org.example.Others;

import org.example.Models.Account;
import org.example.Views.LiveChat.ChatFrame_Client;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DraggableIcon extends JXPanel {
    private int mouseX, mouseY;
    private ImageIcon icon;
    private Account account;
    private JXFrame chatFrame;

    public DraggableIcon(ImageIcon icon) {
        super();
        this.icon = icon;
        setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    if (chatFrame == null) {
                        chatFrame = new ChatFrame_Client(account);
                        chatFrame.setVisible(true);
                    } else chatFrame.setVisible(true);
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("Right clicked");
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - mouseX;
                int deltaY = e.getY() - mouseY;

                setLocation(getLocation().x + deltaX, getLocation().y + deltaY);

                if (getLocation().x < 0) {
                    setLocation(0, getLocation().y);
                }
                if (getLocation().y < 0) {
                    setLocation(getLocation().x, 0);
                }
                if (getLocation().x + getWidth() > getParent().getWidth()) {
                    setLocation(getParent().getWidth() - getWidth(), getLocation().y);
                }
                if (getLocation().y + getHeight() > getParent().getHeight()) {
                    setLocation(getLocation().x, getParent().getHeight() - getHeight());
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        icon.paintIcon(this, g, 0, 0);
    }

    public static void main(String[] args) {
        JXFrame frame = new JXFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setBackground(Color.BLACK);
        DraggableIcon icon = new DraggableIcon(new ImageIcon("src/main/java/org/example/Views/icons/InApp/Addition.png"));
        icon.setBounds(100, 100, 50, 50);
        frame.add(icon);

        frame.setVisible(true);
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
