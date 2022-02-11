package main;

import java.util.ArrayList;

public class Stages {

    protected Double M0 = 1200.0; // Прочая масса, кг
    protected Double M1 = 278000.0*2; // Полная масса первой ступени, кг
    protected Double M1e = 33000.0*2; // Масса первой ступени в конце работы первой ступени, кг
    protected Double M2 = 184700.0; // Полная масса второй ступени, кг
    protected Double M21 = 184700.0 - 45220.0; // Масса второй ступени после отделения первой, кг
    protected Double M2e = 14700.0; // Масса второй ступени в конце работы блока второй ступени, кг
    protected Double M3 = 19440.0; // Полная масса третьей ступени, кг
    protected Double M3e = 4540.0; // Масса в конце работы блока третьей ступени, кг
    protected Double Mk = 6161.0; // Масса космического корабля, кг

    protected Double t; // Время работы ступени, с
    protected Double F; // Тяга двигателя в вакууме, Н
    protected Double Fmin; // Минимальная тяга двигателя, Н
    protected Double M; // Масса ракеты, кг
    protected Double angle; // Конечный угол поворота ракеты относительно вертикальной оси за время работы ступени, град
    protected Double k; // Расход массы ступени, кг/с

    protected Double g = 9.8; // Ускорение свободного падения, Н/кг

    protected ArrayList<Double> timeValues = new ArrayList<>();

    protected ArrayList<Double> movementXValues = new ArrayList<Double>();
    protected ArrayList<Double> movementYValues = new ArrayList<Double>();

    protected ArrayList<Double> speedXValues = new ArrayList<Double>();
    protected ArrayList<Double> speedYValues = new ArrayList<Double>();

    ArrayList<Double> accelerationXValues = new ArrayList<Double>();
    ArrayList<Double> accelerationYValues = new ArrayList<Double>();

    protected Double step = 0.01;

    public void calculateTimeValues() {
        Double startNumber = 0.0;
        Double endNumber = t;

        for(Double i = startNumber; i < endNumber; i = i + step) {
            timeValues.add(i);
        }
    }

    /**
    Уточнённый метод Эйлера
    y[x+1] = y[i-1] + 2hf(x[i]y[])
     */
    public Double euler(Double arg1, Double arg2) {
        return arg1 + 2*step*arg2;
    }

    protected Double setStartValues(ArrayList<Double> values, ArrayList<Double> previousValues, int i) {
        if(i >= 2) {
            return values.get(i - 2);
        } else if(previousValues != null) {
            return previousValues.get(previousValues.size() - 1 - (2 - i));
        } else return 0.0;
    }

    protected Double rotationAngleFunction() {
        return angle*((Math.PI/2)/90)/ t;
    }

    protected Double getEndAngle() {
        return rotationAngleFunction()* t;
    }

    protected void printParameters() {
        System.out.println("M = " + (M0 + M1 + M2 + M3 + Mk));
        System.out.println("M123 = " + (M1 + M2 + M3));
        System.out.println("Расход массы: " + k);
        System.out.println("Параметры в конце работы ступени " + this.getClass().getSimpleName());
        System.out.println("X = " + String.format("%.0f", movementXValues.get(movementXValues.size()-1)) + " м");
        System.out.println("Y = " + String.format("%.0f", movementYValues.get(movementYValues.size()-1)) + " м");

        System.out.println("Скорость по X: = " + String.format("%.0f", speedXValues.get(speedXValues.size()-1)) + " м/с" + " (" + String.format("%.0f", speedXValues.get(speedXValues.size()-1)*3.6) + " км/ч)");
        System.out.println("Скорость по Y: = " + String.format("%.0f", speedYValues.get(speedYValues.size()-1)) + " м/с" + " (" + String.format("%.0f", speedYValues.get(speedYValues.size()-1)*3.6) + " км/ч)");
        Double sp = Math.sqrt(Math.pow(speedXValues.get(speedXValues.size()-1), 2.0) + Math.pow(speedYValues.get(speedYValues.size()-1), 2.0));
        System.out.println("Суммарная скорость: = " + String.format("%.0f", sp) + " м/с" + " (" + String.format("%.0f", sp*3.6) + " км/ч)");

        System.out.println("Ускорение по X: = " + String.format("%.2f", accelerationXValues.get(accelerationXValues.size()-1)) + " м/с^2");
        System.out.println("Ускорение по Y: = " + String.format("%.2f", accelerationYValues.get(accelerationYValues.size()-1)) + " м/с^2");
        System.out.println("Суммарное ускорение: = " + String.format("%.2f", Math.sqrt(Math.pow(accelerationXValues.get(accelerationXValues.size()-1), 2.0) + Math.pow(accelerationYValues.get(accelerationYValues.size()-1), 2.0))) + " м/с^2");
        System.out.println();
    }

    public ArrayList<Double> getTimeValues() {
        return timeValues;
    }

    public ArrayList<Double> getMovementXValues() {
        return movementXValues;
    }

    public ArrayList<Double> getMovementYValues() {
        return movementYValues;
    }

    public ArrayList<Double> getSpeedXValues() {
        return speedXValues;
    }

    public ArrayList<Double> getSpeedYValues() {
        return speedYValues;
    }

    public ArrayList<Double> getAccelerationXValues() {
        return accelerationXValues;
    }

    public ArrayList<Double> getAccelerationYValues() {
        return accelerationYValues;
    }
}
