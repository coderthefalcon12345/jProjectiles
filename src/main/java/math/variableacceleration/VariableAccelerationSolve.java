package math.variableacceleration;


public class VariableAccelerationSolve {

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
