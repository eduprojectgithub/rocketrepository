package main;

import java.util.ArrayList;
import java.util.List;

public class Calculator1 {

    private List<Double> arg = new ArrayList<>();
    private List<Double> func = new ArrayList<>();

    public Calculator1() {
        for(Double var = 190.0; var < 300; var = var + 1) {
            arg.add(var);
        }

        for(Double var : arg) {
            func.add(Resistance.soundSpeed(var));
        }
    }

    public List<Double> getArg() {
        return arg;
    }

    public List<Double> getFunc() {
        return func;
    }
}
