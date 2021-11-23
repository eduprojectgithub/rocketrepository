package graphics;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private FunctionPanel panel;

    public Window() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1920, 1200);

        setLocation(0, 0);
        setBackground(Color.WHITE);
        //add(new SplinePanel());
        panel = new FunctionPanel();
        add(panel);
    }

    public FunctionPanel getPanel() {
        return panel;
    }
}
