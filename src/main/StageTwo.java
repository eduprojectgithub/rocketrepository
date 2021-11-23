package main;

public class StageTwo extends Stages {
    private StageOne stageOne;

    public StageTwo() {
        t = 182.0; // Время работы второй ступени после отделения первой, с
        F = 921200.0; // Тяга двигателя второй ступени, Н
        M = 96752.0; // Масса ракеты с космическим кораблём после отделения первой ступени, кг
        angle = 16.0; // Конечный угол поворота ракеты относительно вертикальной оси за время работы второй ступени, град
        k = 310.0; // Расход массы второй ступени, кг/с
        calculateTimeValues();
    }

    public void calculateFunction() {
        for(int i = 0; i < timeValues.size(); i++) {
            accelerationXValues.add(xFunction(timeValues.get(i)));
            accelerationYValues.add(yFunction(timeValues.get(i)));
            speedXValues.add(euler(setStartValues(speedXValues, stageOne.getSpeedXValues(), i), accelerationXValues.get(i)));
            speedYValues.add(euler(setStartValues(speedYValues, stageOne.getSpeedYValues(), i), accelerationYValues.get(i)));
            movementXValues.add(euler(setStartValues(movementXValues, stageOne.getMovementXValues(), i), speedXValues.get(i)));
            movementYValues.add(euler(setStartValues(movementYValues, stageOne.getMovementYValues(), i), speedYValues.get(i)));
        }

        printParameters();
    }

    public Double xFunction(Double arg) {
        return (F * Math.sin(stageOne.getEndAngle() + rotationAngleFunction() *arg))/(M - k *arg);
    }

    public Double yFunction(Double arg) {
        return (F * Math.cos(stageOne.getEndAngle() + rotationAngleFunction() *arg))/(M - k *arg) - g;
    }

    public void setStageOne(StageOne stageOne) {
        this.stageOne = stageOne;
    }

    public StageOne getStageOne() {
        return stageOne;
    }
}
