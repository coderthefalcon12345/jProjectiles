package math.variableacceleration;

/**
 * Represents an acceleration function for one-dimensional motion.
 *
 * The function determines the acceleration of an object from its current
 * position, velocity, and time. This allows the acceleration to vary during
 * the simulation.
 *
 * Acceleration depending on velocity can be represented:
 *
 * <pre>
 * (x, v, t) -&gt; -0.1 * v
 * </pre>
 *
 * @see VariableAccelerationSolve
 */

@FunctionalInterface
public interface AccelerationFunction {


    /**
     * Calculates acceleration at a particular point in simulation.
     *
     * @param position current position in metres
     * @param velocity current velocity in ms^-1
     * @param time current time in seconds
     * @return acceleration in ms^-2
     */

    double calculate(
            double position,
            double velocity,
            double time
    );

}
