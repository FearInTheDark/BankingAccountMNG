package Views.chat;

import Models.ModelAccount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jdesktop.swingx.JXFrame;
import raven.chat.component.ChatArea;
import raven.chat.swing.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ChatFrame_Client extends JXFrame {
    private Background background1;
    private ChatArea chatArea;
    private Point point;
    private ModelAccount modelAccount;

    public ChatFrame_Client(ModelAccount modelAccount) {
        this.modelAccount = modelAccount;
        initComponents();
    }

    private void initComponents() {

        background1 = new Background();
        chatArea = new ChatArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setAlwaysOnTop(true);

        GroupLayout background1Layout = new GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(background1Layout.createSequentialGroup().addContainerGap().addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE).addContainerGap()));
        background1Layout.setVerticalGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(background1Layout.createSequentialGroup().addContainerGap().addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE).addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(background1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(background1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        chatArea.getLabelTitle().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                point = evt.getPoint();
            }
        });

        chatArea.getLabelTitle().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                Point p = getLocation();
                setLocation(p.x + evt.getX() - point.x, p.y + evt.getY() - point.y);
            }
        });

        chatArea.getCmdClose().addActionListener(evt -> dispose());
        chatArea.closeChat(modelAccount.getFullName());

        pack();
        setLocationRelativeTo(null);
    }

}

