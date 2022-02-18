package main;

public class Resistance {

    static private Double S = Math.pow(2.95/2, 2)*Math.PI*5; // Площадь проекции ракеты
//    static private Double S = Math.pow(2.95/2, 2)*Math.PI + Math.pow(3.82/2, 2)*Math.PI*4; // Площадь проекции ракеты
    static private Double M_ = 0.0289644; // Молярная масса воздуха
    static private Double R = 8.31447; // Универсальная газовая постоянная

    static private Double[] H = {0.0, 11000.0, 20000.0, 32000.0, 47000.0, 51000.0, 71000.0, 85000.0}; // Высота, м
    static private Double[] L = {-6.5, 0.0, 1.0, 2.8, 0.0, -2.8, -2.0}; // Градиент температуры в зависимости от высоты H
    static private Double[] T = {288.0, 216.0, 216.0, 227.0, 270.0, 270.0, 216.0}; // Температура в зависисости от высоты H
    static private Double[] P = {1030.0, 229.8, 55.3, 8.7, 1.1, 0.6, 0.03}; // Давление в зависимости от высоты H

    // Число Маха
    static private Double M[] =            {0.0,    0.1,    0.3,    0.5,    0.7,    0.8,    0.9,    1.0,    1.1,    1.3,    1.5,    2.0,    2.5,    3.0,    3.5,    4.0,    4.5,    5.0, 5.5};
    // Коэффициент лобового сопротивления в зависимости от числа Маха
        static private Double cx_table[] = {0.2093, 0.2093, 0.2090, 0.1931, 0.2146, 0.2453, 0.2857, 0.3339, 0.3852, 0.4955, 0.5081, 0.5085, 0.4941, 0.4873, 0.4696, 0.4464, 0.4391, 0.4269, 0.4173};

    // Вспомогательный массив для вычисления кусочно-линейной аппроксимации
    static private Double k[] = new Double[18];

    static public Double resistance(Double h, Double vx, Double vy) {
        Double r = cxResult(h, Math.sqrt(vx*vx + vy*vy))*density(h)*(vx*vx + vy*vy)*S*0.5;
        System.out.println(r);
        return r;
    }

    // Зависимость плотности воздуха от высоты
    static public Double density(Double h) {
        Double result = 0.0;
        if(H[0] <= h && h < H[1]) {
            result = density1(h, 0);
        }
        if(H[1] <= h && h < H[2]) {
            result = density1(h, 1);
        }
        if(H[2] <= h && h < H[3]) {
            result = density1(h, 2);
        }
        if(H[3] <= h && h < H[4]) {
            result = density1(h, 3);
        }
        if(H[4] <= h && h < H[5]) {
            result = density1(h, 4);
        }
        if(H[5] <= h && h < H[6]) {
            result = density1(h, 5);
        }
        if(H[6] <= h && h <= H[7]) {
            result = density1(h, 6);
        }

        return result;
    }

    // Плотность воздуха на данной высоте в зависимости от слоя воздуха в атмосфере
    static public Double density1(Double h, int i) {
        Double result = 0.0;
        if(!L[i].equals(0.0)) {
            result = P[i]*Math.pow(T[i]/(T[i] + L[i]*(h - H[i])*0.001), 34.163/L[i]);
        }
        if(L[i].equals(0.0)) {
            result = P[i]*Math.exp(-34.163*(h - H[i])*0.001/T[i]);
        }

        Double density = (result*M_*100)/(R* temperature1(h, i));

        return density;
    }

    static public Double temperature(Double h) {
        Double result = 0.0;
        for(int i = 0; i < T.length - 1; i++) {
            if(H[i] <= h && h < H[i+1]) {
                result = T[i] + L[i]*(h - H[i])*0.001;
            }
        }
        return result;
    }

    // Зависимость температуры воздуха от данной высоты и слоя воздуха в атмосфере
    static public Double temperature1(Double h, int i) {

        return T[i] + L[i]*(h - H[i])*0.001;
    }

    static public Double cxResult(Double h, Double v) {
        return Cx(mach(v, soundSpeed(temperature(h))));
    }

    /** Зависимость коэффициента лобового сопротивления от числа Маха, диапазон от 0 до 5 Маха
     * кусочно-линейная аппроксимация*/
    static public Double Cx(Double m) {
        for(int i = 0; i < k.length; i++) {
            k[i] = (cx_table[i+1] - cx_table[i])/(M[i+1] - M[i]);
        }
        Double result = 0.0;
        for(int i = 0; i < k.length; i++) {
            if (M[i] <= m && m < M[i+1]) {
                result = cx_table[i] + k[i]*(m - M[i]);
            }
        }
        return result;
    }

    /** Зависимость числа Маха от скорости звука */
    static public Double mach(Double v, Double vs) {
        return v/vs;
    }

    /** Скорость звука в диапазоне от 173 К до 223 К с точностью до 0.0306 %,
     * аппроксимация квадратным полиномом */
    static public Double soundSpeed(Double temperature) {
        Double result = (-1)*0.0007*temperature*temperature + 0.9690*temperature + 115.7923;
        return result;
    }
}
