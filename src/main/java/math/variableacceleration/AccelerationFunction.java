package math.variableacceleration;

@FunctionalInterface
public interface AccelerationFunction {

    double calculate(
            double position,
            double velocity,
            double time
    );

}