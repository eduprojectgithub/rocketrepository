package graphics;

import main.Calculator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FunctionPanel extends JPanel {
    private Calculator calc;

    private int originX = 100;
    private int originY = 500;
    int scaleRatio = 10;
    double scaleRatioX;
    double scaleRatioY;

    public FunctionPanel() {
        super();
        setVisible(true);
        setBackground(Color.WHITE);

        calc = new Calculator();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        paintAxis(g2D);
        paintGrid(g2D);
        paintAxisValues(g2D);
        paintFunction(g2D);
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
        for(int i = 0; i < 20; i++) {
            g2D.drawString(String.valueOf(i * 100), originX + i*100 - 10, originY + 20);
        }

        for(int i = 1; i < 10; i++) {
            g2D.drawString(String.valueOf(i * 100), originX - 50, originY - i*100 + 5);
        }
    }

    public void paintFunction(Graphics2D g2D) {
        scaleRatioX = calc.getScaleRatio();
        scaleRatioY = calc.getScaleRatio();
        int x;
        int y;
        int x1;
        int y1;
        g2D.setColor(Color.BLACK);
        for(int i = 0; i < calc.getxAxisValues().size() - 1; i++) {
            x = (int)Math.round(calc.getxAxisValues().get(i) * scaleRatioX);
            y = (int)Math.round(calc.getyAxisValues().get(i) * scaleRatioY);
            x1 = (int)Math.round(calc.getxAxisValues().get(i+1) * scaleRatioX);
            y1 = (int)Math.round(calc.getyAxisValues().get(i+1) * scaleRatioY);
            g2D.drawLine(originX + x, originY - y, originX + x1, originY - y1);
        }

        int firstStageX = (int)Math.round(calc.getxAxisValues().get(calc.stageOne.getMovementXValues().size()) * scaleRatioX);
        int firstStageY = (int)Math.round(calc.getyAxisValues().get(calc.stageOne.getMovementYValues().size()) * scaleRatioY);

        int secondStageX = (int)Math.round(calc.getxAxisValues().get(calc.stageOne.getMovementXValues().size() +
                calc.stageTwo.getMovementXValues().size()) * scaleRatioX);
        int secondStageY = (int)Math.round(calc.getyAxisValues().get(calc.stageOne.getMovementYValues().size() +
                calc.stageTwo.getMovementYValues().size()) * scaleRatioY);

        g2D.setColor(Color.RED);
        g2D.fillOval(originX + firstStageX - 4, originY - firstStageY - 4, 8, 8);
        g2D.fillOval(originX + secondStageX - 4, originY - secondStageY - 4, 8, 8);

//        g2D.setColor(Color.BLACK);
//        int R = (int) Math.round(6371000*scaleRatioX);
//        g2D.drawOval(originX - R, originY, 2*R, 2*R);
    }
}
