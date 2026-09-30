package math;

/**
 * Solves two-dimensional projectile motion by decomposing the projectile's
 * velocity into independent horizontal and vertical components.
 *
 * <p>The projectile is assumed to have constant acceleration. Horizontal
 * acceleration is normally zero, while vertical acceleration is normally
 * {@code -9.81 m/s²}.</p>
 *
 * <p>The supplied launch angle is measured in degrees counter-clockwise from
 * the positive X axis.</p>
 */


public class ConstantAccelerationProjectileSolve {

    private static final double EARTH_GRAVITY = 9.81;

    private final double gravity;
    private final ConstantAccelerationSolve Solver;


    /**
     * Creates a projectile solver using standard gravitational acceleration.
     */
    public ConstantAccelerationProjectileSolve() {
        this(EARTH_GRAVITY);
    }

    /**
     * Creates a projectile solver with a specified gravitational acceleration.
     *
     * @param gravity gravitational acceleration in metres per second squared
     */
    public ConstantAccelerationProjectileSolve(double gravity) {
        if (gravity <= 0) {
            throw new IllegalArgumentException(
                    "Gravity must be greater than zero."
            );
        }

        this.gravity = gravity;
        this.Solver = new ConstantAccelerationSolve();
    }

    /**
     * Solves the projectile's position after a specified amount of time.
     *
     * <p>The launch velocity is decomposed into horizontal and vertical
     * components using:</p>
     *
     * <pre>
     * vx = speed * cos(angle)
     * vy = speed * sin(angle)
     * </pre>
     *
     * <p>The X axis is solved with zero acceleration and the Y axis is solved
     * with gravitational acceleration.</p>
     *
     * @param speed initial projectile speed in metres per second
     * @param angleDegrees launch angle in degrees
     * @param time elapsed time in seconds
     * @return the projectile state after the specified time
     */
    public ConstantAccelerationProjectileState solve(
            double speed,
            double angleDegrees,
            double time) {

        if (speed < 0) {
            throw new IllegalArgumentException(
                    "Projectile speed cannot be negative."
            );
        }

        if (time < 0) {
            throw new IllegalArgumentException(
                    "Time cannot be negative."
            );
        }

        double angleRadians = Math.toRadians(angleDegrees);

        double horizontalVelocity =
                speed * Math.cos(angleRadians);

        double verticalVelocity =
                speed * Math.sin(angleRadians);

        ConstantAccelerationState xInput =
                ConstantAccelerationState.builder()
                        .u(horizontalVelocity)
                        .a(0)
                        .t(time)
                        .build();

        ConstantAccelerationState yInput =
                ConstantAccelerationState.builder()
                        .u(verticalVelocity)
                        .a(-gravity)
                        .t(time)
                        .build();

        ConstantAccelerationState x =
                Solver.solve(xInput);

        ConstantAccelerationState y =
                Solver.solve(yInput);

        return new ConstantAccelerationProjectileState(x, y);
    }

}
