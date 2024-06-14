package Others;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class InsetsAndBorder extends JFrame {
    public InsetsAndBorder() {
        begin();
    }

    private void begin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 6 6 6 6, fillx, debug", "3[]3[]3[]3", "5[]5[]5[]5"));

        JLabel label1 = new JLabel("1");
        JLabel label2 = new JLabel("2");

        JButton button = new JButton("No way!");
        button.setFocusable(false);

        panel.add(label1, "cell 1 2, grow, pushy");

        panel.add(label2, "cell 2 3, grow, pushy");

        panel.add(button, "cell 0 1, grow");

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InsetsAndBorder frame = new InsetsAndBorder();

        });
    }

    static void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
}
