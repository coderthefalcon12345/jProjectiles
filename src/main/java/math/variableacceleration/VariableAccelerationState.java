package math.variableacceleration;


/**
 * Represents the state of an object undergoing potentially variable
 * acceleration.
 *
 * <p>Instances are immutable. A new state is created when the simulation
 * advances.</p>
 */
public class VariableAccelerationState {

    private final double position;
    private final double velocity;
    private final double time;

    /**
     * Creates a variable-acceleration state.
     *
     * @param position position in metres
     * @param velocity velocity in metres per second
     * @param time elapsed time in seconds
     */
    public VariableAccelerationState(
            double position,
            double velocity,
            double time) {
        this.position = position;
        this.velocity = velocity;
        this.time = time;
    }

    /**
     * Returns the current position.
     *
     * @return position in metres.
     */
    public double getPosition() {
        return position;
    }
    /**
     * Returns the current velocity.
     *
     * @return velocity in ms^-1
     */
    public double getVelocity() {
        return velocity;
    }
    /**
     * Returns the current time.
     *
     * @return time in seconds
     */
    public double getTime() {
        return time;
    }

}
