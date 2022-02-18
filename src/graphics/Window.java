package graphics;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private JPanel panel;

    public Window() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1920, 1200);

        setLocation(0, 0);
        setBackground(Color.WHITE);
        panel = new FunctionPanel();
//        panel = new FunctionPanel1();
        add(panel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
