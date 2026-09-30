package math.variableacceleration;



/**
 * Solves one-dimensional motion with potentially variable acceleration
 * using numerical integration.
 *
 * <p>The solver uses the Euler integration method. Instead of assuming that
 * acceleration remains constant, acceleration is evaluated at every timestep
 * using an {@link AccelerationFunction}.</p>
 *
 * <p>The equations being numerically integrated are:</p>
 *
 * <pre>
 * dx/dt = v
 * dv/dt = a(x, v, t)
 * </pre>
 *
 * <p>The accuracy of the solution depends on the chosen timestep. Smaller
 * timesteps generally produce more accurate results at the cost of requiring
 * more calculations.</p>
 *
 */
public class VariableAccelerationSolve {



    /**
     * Solves a variable-acceleration problem using Euler integration.
     *
     * @param initial initial position, velocity, and time
     * @param endTime time at which the simulation should finish
     * @param dt integration timestep in seconds
     * @param accelerationFunction function describing the acceleration
     * @return the calculated state at {@code endTime}
     *
     * @throws IllegalArgumentException if {@code dt} is not positive or
     *         {@code endTime} occurs before the initial time
     */
    public VariableAccelerationState solve(
            VariableAccelerationState initial,
            double endTime,
            double dt,
            AccelerationFunction accelerationFunction
    ) {
        if (dt <= 0) {
            throw new IllegalArgumentException("Time step must > 0");
        }

        if (endTime < initial.getTime()) {
            throw new IllegalArgumentException("End time cannot be before initial time.");
        }


        double position = initial.getPosition();
        double velocity = initial.getVelocity();
        double time = initial.getTime();

        while (time < endTime) {
            double step = Math.min(dt, endTime - time);

            double acceleration =
                    accelerationFunction.calculate(
                            position,
                            velocity,
                            time
                    );


            position += velocity * step;
            velocity += acceleration * step;
            time += step;

        }

        return new VariableAccelerationState(
                position,
                velocity,
                time
        );
    }
}
