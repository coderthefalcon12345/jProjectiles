package constantaccelerationtests;

import math.constantacceleration.ConstantAccelerationProjectileSolve;
import math.constantacceleration.ConstantAccelerationProjectileState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstantAccelerationTests {

    private final ConstantAccelerationProjectileSolve solver =
            new ConstantAccelerationProjectileSolve();

    @Test
    void solvesBasicProjectile() {
        ConstantAccelerationProjectileState result =
                solver.solve(20, 30, 1);

        assertEquals(
                17.320508,
                result.getX().getS(),
                1e-5
        );

        assertEquals(
                5.095,
                result.getY().getS(),
                1e-5
        );
    }

    @Test
    void horizontalAccelerationIsZero() {
        ConstantAccelerationProjectileState result =
                solver.solve(20, 45, 1);

        assertEquals(
                0.0,
                result.getX().getA(),
                1e-6
        );
    }

    @Test
    void verticalAccelerationIsGravity() {
        ConstantAccelerationProjectileState result =
                solver.solve(20, 45, 1);

        assertEquals(
                -9.81,
                result.getY().getA(),
                1e-6
        );
    }

    @Test
    void rejectsNegativeSpeed() {
        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(-20, 30, 1)
        );
    }

    @Test
    void rejectsNegativeTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(20, 30, -1)
        );
    }

    @Test
    void zeroSpeedProducesGravityOnlyMotion() {
        ConstantAccelerationProjectileState result =
                solver.solve(0, 45, 1);

        assertEquals(
                0.0,
                result.getX().getS(),
                1e-6
        );

        assertEquals(
                -4.905,
                result.getY().getS(),
                1e-6
        );
    }





}

