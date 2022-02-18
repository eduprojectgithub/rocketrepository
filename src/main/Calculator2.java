package main;

import java.util.ArrayList;
import java.util.Collections;

public class Calculator2 {

    private ArrayList<Double> xAxisValues = new ArrayList<>();
    private ArrayList<Double> yAxisValues = new ArrayList<>();

    Double r = 6371000.0; // Радиус Земли

    double scaleRatio;

    public StageOne stageOne = new StageOne();
    public StageTwo stageTwo = new StageTwo();
    public StageThree stageThree = new StageThree();

    public Calculator2() {
        evaluateParameters();
        calculateTrajectory();
    }

    private void evaluateParameters() {
        stageTwo.setStageOne(stageOne);
        stageThree.setStageTwo(stageTwo);

        stageOne.calculateFunction(0);
        stageTwo.calculateFunction();
        stageThree.calculateFunction();
    }

    private void calculateTrajectory() {
        scaleRatio = 0.001;
        xAxisValues.addAll(stageOne.getMovementXValues());
        xAxisValues.addAll(stageTwo.getMovementXValues());
        xAxisValues.addAll(stageThree.getMovementXValues());
        yAxisValues.addAll(stageOne.getMovementYValues());
        yAxisValues.addAll(stageTwo.getMovementYValues());
        yAxisValues.addAll(stageThree.getMovementYValues());

        curvatureCorrection();

        System.out.println("Высота полёта после отстыковки второй ступени с учётом поправки кривизны: " + String.format("%.0f", yAxisValues.get(stageOne.getTimeValues().size() + stageTwo.getTimeValues().size())/1000) + " км");
        System.out.println("Конечная высота орбиты с учётом поправки кривизны: " + String.format("%.0f", yAxisValues.get(yAxisValues.size() - 1)/1000) + " км");
        System.out.println("Vmax1 = " + Collections.max(stageOne.speedYValues));
        System.out.println("Vmax2 = " + Collections.max(stageTwo.speedYValues));
    }

    // Расчёт поправки кривизны
    private void curvatureCorrection() {
        for(int i = 0; i < xAxisValues.size(); i++) {
            Double h = r/(Math.cos(Math.atan(xAxisValues.get(i)/r))) - r;
            yAxisValues.set(i, yAxisValues.get(i) + h);
        }
    }

    public ArrayList<Double> getyAxisValues() {
        return yAxisValues;
    }

    public ArrayList<Double> getxAxisValues() {
        return xAxisValues;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }
}
