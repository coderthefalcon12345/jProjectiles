package math.constantacceleration;

/**
 * Represents the state of a projectile moving in two dimensions.
 *
 * <p>The projectile is modelled as two independent constant-acceleration
 * systems: one for the X axis and one for the Y axis.</p>
 *
 * <p>The coordinate system assumes positive X points right and positive Y
 * points upward. Under normal projectile motion, horizontal acceleration is
 * zero while vertical acceleration is equal to negative gravitational
 * acceleration.</p>
 */
public class ConstantAccelerationProjectileState {

    private final ConstantAccelerationState x;
    private final ConstantAccelerationState y;

    /**
     * Creates a projectile state from its horizontal and vertical states.
     *
     * @param x the horizontal motion state
     * @param y the vertical motion state
     */
    public ConstantAccelerationProjectileState(
            ConstantAccelerationState x,
            ConstantAccelerationState y) {

        this.x = x;
        this.y = y;
    }



    /**
     * Returns the horizontal motion state.
     *
     * @return horizontal SUVAT state
     */
    public ConstantAccelerationState getX() {
        return x;
    }

    /**
     * Returns the vertical motion state.
     *
     * @return vertical SUVAT state
     */
    public ConstantAccelerationState getY() {
        return y;
    }


    /**
     * Determines whether both axes have been completely solved.
     *
     * @return {@code true} if both X and Y states are fully solved
     */
    public boolean isFullySolved() {
        return x.isFullySolved() && y.isFullySolved();
    }
}
