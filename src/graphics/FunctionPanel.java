package graphics;

import main.Calculator;

import javax.swing.*;
import java.awt.*;

public class FunctionPanel extends JPanel {
    private Calculator calc;

    private int beginX = 100;
    private int beginY = 500;
    int scaleRatio = 10;
    double scaleRatio1;
    double scaleRatio2;

    public FunctionPanel() {
        super();
        setVisible(true);
        setBackground(Color.WHITE);

        calc = new Calculator();
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        paintAxis(g2D);
        paintGrid(g2D);
        paintAxisValues(g2D);
        paintFunction(g2D);
    }

    public void paintAxis(Graphics2D g2D) {
        g2D.drawLine(0, beginY, 1920, beginY);
        g2D.drawLine(beginX, 0, beginX, 1200);
    }

    private void paintGrid(Graphics2D g2D) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                g2D.fillOval(beginX + i * 100 - 2, beginY - j * 100, 4, 4);
            }
        }
    }

    private void paintAxisValues(Graphics2D g2D) {
        for(int i = 0; i < 20; i++) {
            g2D.drawString(String.valueOf(i * 100), beginX + i*100 - 10, beginY + 20);
        }

        for(int i = 1; i < 10; i++) {
            g2D.drawString(String.valueOf(i * 100), beginX - 50, beginY - i*100 + 5);
        }
    }

    public void paintFunction(Graphics2D g2D) {
        scaleRatio1 = calc.getScaleRatio();
//        scaleRatio1 = 2;
        scaleRatio2 = calc.getScaleRatio();
//        scaleRatio2 = 0.2;
        int x;
        int y;
        int x1;
        int y1;
        g2D.setColor(Color.BLACK);
        for(int i = 0; i < calc.getxAxisValues().size() - 1; i++) {
            x = (int)Math.round(calc.getxAxisValues().get(i) * scaleRatio1);
            y = (int)Math.round(calc.getyAxisValues().get(i) * scaleRatio2);
            x1 = (int)Math.round(calc.getxAxisValues().get(i+1) * scaleRatio1);
            y1 = (int)Math.round(calc.getyAxisValues().get(i+1) * scaleRatio2);
            g2D.drawLine(beginX + x, beginY - y, beginX + x1, beginY - y1);
        }

        int firstStageX = (int)Math.round(calc.getxAxisValues().get(calc.stageOne.getMovementXValues().size()) * scaleRatio1);
        int firstStageY = (int)Math.round(calc.getyAxisValues().get(calc.stageOne.getMovementYValues().size()) * scaleRatio2);

        int secondStageX = (int)Math.round(calc.getxAxisValues().get(calc.stageOne.getMovementXValues().size() +
                calc.stageTwo.getMovementXValues().size()) * scaleRatio1);
        int secondStageY = (int)Math.round(calc.getyAxisValues().get(calc.stageOne.getMovementYValues().size() +
                calc.stageTwo.getMovementYValues().size()) * scaleRatio2);

        g2D.setColor(Color.RED);
        g2D.fillOval(beginX + firstStageX - 4, beginY - firstStageY - 4, 8, 8);
        g2D.fillOval(beginX + secondStageX - 4, beginY - secondStageY - 4, 8, 8);
    }
}
