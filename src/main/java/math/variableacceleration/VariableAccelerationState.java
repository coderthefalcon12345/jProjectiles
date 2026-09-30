package math.variableacceleration;

public class VariableAccelerationState {

    private final double position;
    private final double velocity;
    private final double time;

    public VariableAccelerationState(
            double position,
            double velocity,
            double time) {
        this.position = position;
        this.velocity = velocity;
        this.time = time;
    }


    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getTime() {
        return time;
    }

}
