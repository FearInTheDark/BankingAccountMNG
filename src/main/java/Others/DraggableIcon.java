package Others;

import Models.ModelAccount;
import Views.chat.ChatFrame_Client;
import Views.chat.ChatFrame_Threading;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import lombok.Getter;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;

@Getter
public class DraggableIcon extends JXPanel {
    private int mouseX, mouseY;
    private final ImageIcon icon;
    private ModelAccount modelAccount;
    private ChatFrame_Client chatFrame;
    private ChatFrame_Threading chatFrameThreading;

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
                        chatFrame = new ChatFrame_Client(modelAccount);
                        chatFrame.setVisible(true);
                        chatFrameThreading = new ChatFrame_Threading(chatFrame);
                        chatFrameThreading.generateConnection();
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

    /*Test Draggable Icon*/
    public static void main(String[] args) throws ParseException {
        FlatLaf.registerCustomDefaultsSource("FlatLaf.theme");
        FlatRobotoFont.install();
        FlatMacLightLaf.setup();
        JXFrame frame = new JXFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setBackground(Color.BLACK);
        ImageIcon robotIcon = new ImageIcon("src/main/resources/icons/InApp/robot.png");
        Image image = robotIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        DraggableIcon icon = new DraggableIcon(new ImageIcon(image));
        icon.setModelAccount(new ModelAccount("", "David Johns", "", "", "", "", "", "", "", null));

        icon.setBounds(100, 100, 50, 50);
        frame.add(icon);

        frame.setVisible(true);
    }

    public void setModelAccount(ModelAccount modelAccount) {
        this.modelAccount = modelAccount;
    }

    public void closeConnection() throws IOException {
        chatFrameThreading.closeConnection();
    }
}
