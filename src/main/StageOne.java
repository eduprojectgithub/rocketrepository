package main;

import java.util.Arrays;

public class StageOne extends Stages {

    private Double Cx = 0.3;
    private Double S = 22.9;
    private Double P0 = 101300.0;
    private Double M_ = 0.0289644;
    private Double R = 8.31447;
    private Double T0 = 288.15;

    private Double[] H = {0.0, 11000.0, 20000.0, 32000.0, 47000.0, 51000.0, 71000.0, 85000.0};
    private Double[] L = {-6.5, 0.0, 1.0, 2.8, 0.0, -2.8, -2.0};
    private Double[] T = {288.0, 216.0, 216.0, 227.0, 270.0, 270.0, 216.0};
    private Double[] P = {1030.0, 229.8, 55.3, 8.7, 1.1, 0.6, 0.03};

    public StageOne() {
        t = 140.0; // Время работы первой ступени, с
        F = 7080000.0*1.5 + 1390000.0; // Суммарная тяга двигателей первой ступени в ваккууме (4 + 1), Н
        Fmin = 7080000.0*1.5 + 960000.0; // Суммарная тяга двигателей на старте (4 + 1), Н
        M = M0 + M1 + M2 + M3 + Mk; // Стартовая масса ракеты с космическим кораблём, кг
        angle = 70.0; // Конечный угол поворота ракеты относительно вертикальной оси за время работы первой ступени, град
//        k = 1688.0; // Суммарный расход массы первой и второй ступеней, кг/с
        k = (M1 - M1e)/t + (M2 - M21)/t;

        calculateTimeValues();
    }

    public void calculateFunction() {
        for(int i = 0; i < timeValues.size(); i++) {
            if(i == 0) {
                accelerationXValues.add(xFunction(timeValues.get(i), 0.0, 0.0, 0.0));
                accelerationYValues.add(yFunction(timeValues.get(i), 0.0, 0.0, 0.0));
            }
            if(i > 0) {
                accelerationXValues.add(xFunction(timeValues.get(i), movementYValues.get(i - 1), speedXValues.get(i - 1), speedYValues.get(i - 1)));
                accelerationYValues.add(yFunction(timeValues.get(i), movementYValues.get(i - 1), speedXValues.get(i - 1), speedYValues.get(i - 1)));
            }
            speedXValues.add(euler(setStartValues(speedXValues, null, i), accelerationXValues.get(i)));
            speedYValues.add(euler(setStartValues(speedYValues, null, i), accelerationYValues.get(i)));
            movementXValues.add(euler(setStartValues(movementXValues, null, i), speedXValues.get(i)));
            movementYValues.add(euler(setStartValues(movementYValues, null, i), speedYValues.get(i)));
        }

        printParameters();
    }

    private Double xFunction(Double arg, Double y, Double vx, Double vy) {
        return (((Fmin + engineForceIncrease()*arg) - resistance(y, vx, vy)) * Math.sin(rotationAngleFunction()*arg))/(M - k*arg);
    }

    private Double yFunction(Double arg, Double y, Double vx, Double vy) {
        return (((Fmin + engineForceIncrease()*arg) - resistance(y, vx, vy)) * Math.cos(rotationAngleFunction()*arg))/(M - k*arg) - g;
    }

    private Double resistance(Double h, Double vx, Double vy) {
        Double r = Cx*density(h)*(vx*vx + vy*vy)*S*0.5;
        System.out.println(r);
        return r;
    }

    private Double engineForceIncrease() {
        return (F - Fmin)/t;
    }

    private Double density(Double h) {
        Double result = 0.0;
        if(H[0] <= h && h < H[1]) {
            result = function(h, 0);
        }
        if(H[1] <= h && h < H[2]) {
            result = function(h, 1);
        }
        if(H[2] <= h && h < H[3]) {
            result = function(h, 2);
        }
        if(H[3] <= h && h < H[4]) {
            result = function(h, 3);
        }
        if(H[4] <= h && h < H[5]) {
            result = function(h, 4);
        }
        if(H[5] <= h && h < H[6]) {
            result = function(h, 5);
        }
        if(H[6] <= h && h <= H[7]) {
            result = function(h, 6);
        }

        return result;
    }

    public Double temperature(Double h, int i) {
        return T[i] + L[i]*(h - H[i])*0.001;
    }

    public Double function(Double h, int i) {
        Double result = 0.0;
        if(!L[i].equals(0.0)) {
             result = P[i]*Math.pow(T[i]/(T[i] + L[i]*(h - H[i])*0.001), 34.163/L[i]);
        }
        if(L[i].equals(0.0)) {
            result = P[i]*Math.exp(-34.163*(h - H[i])*0.001/T[i]);
        }

        Double density = (result*M_*100)/(R*temperature(h, i));

        return density;
    }

    /*static public void main(String[] args) {
        StageOne st1 = new StageOne();
        for(Double h = 0.0; h < 85000; h = h + 100) {
            System.out.println(st1.density(h));
            if(0 <= h && h < 11000) {
                System.out.println(st1.temperature(h, 0));
            }
            if(11000 <= h && h < 20000) {
                System.out.println(st1.temperature(h, 1));
            }
            if(20000 <= h && h < 32000) {
                System.out.println(st1.temperature(h, 2));
            }
            if(32000 <= h && h < 47000) {
                System.out.println(st1.temperature(h, 3));
            }
            if(47000 <= h && h < 51000) {
                System.out.println(st1.temperature(h, 4));
            }
            if(51000 <= h && h < 71000) {
                System.out.println(st1.temperature(h, 5));
            }
            if(71000 <= h && h <= 85000) {
                System.out.println(st1.temperature(h, 6));
            }
        }
    }*/
}
