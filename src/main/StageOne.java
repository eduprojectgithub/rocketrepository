package main;

public class StageOne extends Stages {

    public StageOne() {
        t = 118.0; // Время работы первой ступени, с
        F = 4997904.0; // Суммарная тяга двигателей первой ступени в ваккууме (4 + 1), Н
        Fmin = 4147360.0; // Суммарная тяга двигателей на старте (4 + 1), Н
        M = M0 + M1 + M2 + M3 + Mk; // Стартовая масса ракеты с космическим кораблём, кг
        angle = 59.0; // Конечный угол поворота ракеты относительно вертикальной оси за время работы первой ступени, град
//        k = 1688.0; // Суммарный расход массы первой и второй ступеней, кг/с
        k = (M1 - M1e)/t + (M2 - M21)/t;

        calculateTimeValues();
    }

    public void calculateFunction() {
        for(int i = 0; i < timeValues.size(); i++) {
            accelerationXValues.add(xFunction(timeValues.get(i)));
            accelerationYValues.add(yFunction(timeValues.get(i)));
            speedXValues.add(euler(setStartValues(speedXValues, null, i), accelerationXValues.get(i)));
            speedYValues.add(euler(setStartValues(speedYValues, null, i), accelerationYValues.get(i)));
            movementXValues.add(euler(setStartValues(movementXValues, null, i), speedXValues.get(i)));
            movementYValues.add(euler(setStartValues(movementYValues, null, i), speedYValues.get(i)));
        }

        printParameters();
    }

    private Double xFunction(Double arg) {
        return ((Fmin + engineForceIncrease()*arg) * Math.sin(rotationAngleFunction()*arg))/(M - k*arg);
    }

    private Double yFunction(Double arg) {
        return ((Fmin + engineForceIncrease()*arg) * Math.cos(rotationAngleFunction()*arg))/(M - k*arg) - g;
    }

    private Double engineForceIncrease() {
        return (F - Fmin)/t;
    }
}
