package main;

public class StageThree extends Stages {
    private StageTwo stageTwo;

    public StageThree() {
        t = 240.0; // Время работы третьей ступени, с
        F = 294000.0; // Тяга двигателя третьей ступени, Н
        M = M3 + Mk; // Масса ракеты с космическим кораблём после отделения второй ступени, кг
        angle = 5.5; // Конечный угол поворота ракеты относительно вертикальной оси за время работы третьей ступени, град
//        k = 95.41; // Расход массы третьей ступени, кг/с
        k = (M3 - M3e)/t;

        calculateTimeValues();
    }

    public void calculateFunction() {
        for(int i = 0; i < timeValues.size(); i++) {
            accelerationXValues.add(xFunction(timeValues.get(i)));
            accelerationYValues.add(yFunction(timeValues.get(i)));
            speedXValues.add(euler(setStartValues(speedXValues, stageTwo.getSpeedXValues(), i), accelerationXValues.get(i)));
            speedYValues.add(euler(setStartValues(speedYValues, stageTwo.getSpeedYValues(), i), accelerationYValues.get(i)));
            movementXValues.add(euler(setStartValues(movementXValues, stageTwo.getMovementXValues(), i), speedXValues.get(i)));
            movementYValues.add(euler(setStartValues(movementYValues, stageTwo.getMovementYValues(), i), speedYValues.get(i)));
        }
        printParameters();
    }

    public Double xFunction(Double arg) {
        return (F * Math.sin(stageTwo.getStageOne().getEndAngle() + stageTwo.getEndAngle() + rotationAngleFunction()*arg))/(M - k *arg);
    }

    public Double yFunction(Double arg) {
        return (F * Math.cos(stageTwo.getStageOne().getEndAngle() + stageTwo.getEndAngle() + rotationAngleFunction()*arg))/(M - k *arg) - g;
    }

    public void setStageTwo(StageTwo stageTwo) {
        this.stageTwo = stageTwo;
    }
}
