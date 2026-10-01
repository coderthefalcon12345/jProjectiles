package variableaccelerationtests;

import math.variableacceleration.AccelerationFunction;
import math.variableacceleration.VariableAccelerationSolve;
import math.variableacceleration.VariableAccelerationState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class VariableAccelerationTests {
    private final VariableAccelerationSolve solver =
            new VariableAccelerationSolve();



    @Test
    void solvesVariableAcceleration() {

        AccelerationFunction acceleration =
                (x, v, t) -> 2 * t;

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        0,
                        0
                );

        VariableAccelerationState result =
                solver.solve(
                        initial,
                        10,
                        0.001,
                        acceleration
                );

        assertEquals(
                100.0,
                result.getVelocity(),
                0.01
        );

        assertEquals(
                333.333,
                result.getPosition(),
                0.1
        );

        assertEquals(
                10.0,
                result.getTime(),
                1e-10
        );
    }



    @Test
    void solvesConstantAcceleration() {

        AccelerationFunction acceleration =
                (x, v, t) -> 9.81;

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        0,
                        0
                );

        VariableAccelerationState result =
                solver.solve(
                        initial,
                        2,
                        0.001,
                        acceleration
                );

        assertEquals(
                19.62,
                result.getVelocity(),
                0.01
        );

        assertEquals(
                19.62,
                result.getPosition(),
                0.02
        );
    }

    @Test
    void accelerationFunctionReceivesCurrentState() {

        AccelerationFunction acceleration =
                (x, v, t) -> v;

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        10,
                        0
                );

        VariableAccelerationState result =
                solver.solve(
                        initial,
                        1,
                        0.01,
                        acceleration
                );

        assertTrue(result.getVelocity() > 10);
        assertTrue(result.getPosition() > 0);
    }


    @Test
    void rejectsZeroTimestep() {

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        0,
                        0
                );

        AccelerationFunction acceleration =
                (x, v, t) -> 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(
                        initial,
                        10,
                        0,
                        acceleration
                )
        );
    }


    @Test
    void rejectsNegativeTimestep() {

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        0,
                        0
                );

        AccelerationFunction acceleration =
                (x, v, t) -> 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(
                        initial,
                        10,
                        -0.1,
                        acceleration
                )
        );
    }

    @Test
    void rejectsEndTimeBeforeInitialTime() {

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        10,
                        5
                );

        AccelerationFunction acceleration =
                (x, v, t) -> 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(
                        initial,
                        4,
                        0.01,
                        acceleration
                )
        );
    }

    @Test
    void rejectsNonFiniteInputs() {
        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        0,
                        0
                );

        AccelerationFunction acceleration =
                (x, v, t) -> 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(
                        initial,
                        Double.NaN,
                        0.01,
                        acceleration
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(
                        initial,
                        1,
                        Double.NaN,
                        acceleration
                )
        );
    }

    @Test
    void doesNotOvershootEndTime() {

        VariableAccelerationState initial =
                new VariableAccelerationState(
                        0,
                        10,
                        0
                );

        AccelerationFunction acceleration =
                (x, v, t) -> 0;

        VariableAccelerationState result =
                solver.solve(
                        initial,
                        1.0,
                        0.3,
                        acceleration
                );

        assertEquals(
                1.0,
                result.getTime(),
                1e-10
        );

        assertEquals(
                10.0,
                result.getVelocity(),
                1e-10
        );

        assertEquals(
                10.0,
                result.getPosition(),
                1e-10
        );
    }
}


