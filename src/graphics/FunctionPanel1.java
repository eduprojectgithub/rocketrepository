package graphics;

import main.Calculator;
import main.Calculator1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FunctionPanel1 extends JPanel {

    Calculator1 calc;

    private int originX = 100;
    private int originY = 500;
    int scaleRatio = 10;
    double scaleRatioX = 0.2;
    double scaleRatioY = 0.3;

    int xOffset = 185;
    int yOffset = 260;

    public FunctionPanel1() {
        super();
        setVisible(true);
        setBackground(Color.WHITE);

        calc = new Calculator1();
    }

    public void paint(Graphics g)  {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        paintAxis(g2D);
        paintLine(g2D);
        paintAxisValues(g2D);
    }

    public void paintLine(Graphics2D g2D) {
        int x;
        int y;
        int x1;
        int y1;
        g2D.setColor(Color.BLACK);
        for(int i = 0; i < calc.getArg().size() - 1; i++) {
            x = (int)Math.round((calc.getArg().get(i) - xOffset) * 1/scaleRatioX);
            y = (int)Math.round((calc.getFunc().get(i) - yOffset) * 1/scaleRatioY);
            x1 = (int)Math.round((calc.getArg().get(i+1) - xOffset)* 1/scaleRatioX);
            y1 = (int)Math.round((calc.getFunc().get(i+1) - yOffset) * 1/scaleRatioY);
            g2D.drawLine(originX + x, originY - y, originX + x1, originY - y1);
        }
    }

    public void paintAxis(Graphics2D g2D) {
        g2D.drawLine(0, originY, 1920, originY);
        g2D.drawLine(originX, 0, originX, 1200);
    }

    private void paintGrid(Graphics2D g2D) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                g2D.fillOval(originX + i * 100 - 2, originY - j * 100, 4, 4);
            }
        }
    }

    private void paintAxisValues(Graphics2D g2D) {
        int xValue = 100;
        for(int i = 0; i < 20; i++) {
            g2D.drawString(String.valueOf(i*xValue), originX + Math.round((i*xValue - xOffset)/scaleRatioX) - 30, originY + 20);
        }

        double yValue = 10;
        for(int i = 1; i < 20; i++) {
            g2D.drawString(String.valueOf(i * yValue + yOffset).substring(0, 3), originX - 50, originY - Math.round(i*yValue/scaleRatioY) + 5);
        }
    }
}
