package math.constantacceleration;

/**
 * Solves 1D constant acceleration problems using standard SUVAT equations.
 *
 * The solver repeatedly applies the equations until all five variables have been determined.
 * Time values must be non-negative.
 *
 * The equations are:
 * <pre>
 *     v=u+at
 *     s= 1/2(u+v)t
 *     v^2 = u^2 + 2as
 *     s = ut + 1/2at^2
 * </pre>
 */

public class ConstantAccelerationSolve {

    /**
     * Max permitted difference when comparing for consistency.
     */
    private final double EPSILON = 1e-6;

    /**
     * Solves a constant acceleration kinematics problem.
     *
     * Three SUVAT values must be known.
     * If > 3 are supplied, consistency check.
     *
     * @param input the initial state containing known values.
     * @return a fully solved {@link ConstantAccelerationState}
     *
     * @throws IllegalArgumentException if > 3 variables are provide, t is greater than 0, inconsistency in values
     * or physically invalid results.
     *
     * @throws ArithmeticException if division by zero is required by solution.
     *
     * @throws IllegalStateException if all 5 variables can't be determined.
     */

    public ConstantAccelerationState solve(ConstantAccelerationState input) {
        inputValidation(input);

        ConstantAccelerationState.Builder builder = copyToBuilder(input);
        boolean progressMade = true;

        while (progressMade) {
            ConstantAccelerationState current = builder.build();

            if (current.isFullySolved()) {
                break;
            }

            progressMade = false;

            Double s = current.getS();
            Double u = current.getU();
            Double v = current.getV();
            Double a = current.getA();
            Double t = current.getT();

            if (solveEquation1(builder, s, u, v, a, t)) progressMade = true;
            if (solveEquation2(builder, s, u, v, a, t)) progressMade = true;
            if (solveEquation3(builder, s, u, v, a, t)) progressMade = true;
            if (solveEquation4(builder, s, u, v, a, t)) progressMade = true;
        }

        ConstantAccelerationState finalState = builder.build();

        if (!finalState.isFullySolved()) {
            throw new IllegalStateException("Unable to resolve SUVAT state with provided variables.");
        }


        return finalState;
    }

    /**
     * Validates initial state.
     * At least three of the five SUVAT variables must be known
     *
     * If more than three variables are supplied, the values
     * are checked against a solution derived from the first three known variables
     *
     * @param state state to be validated
     *
     * @throws IllegalArgumentException if too few variables, negative time, or inconsistent values.
     */

    private void inputValidation(ConstantAccelerationState state) {
        if (state == null) {
            throw new IllegalArgumentException("Input state must not be null.");
        }

        validateFiniteStateValue("Displacement (s)", state.getS());
        validateFiniteStateValue("Initial velocity (u)", state.getU());
        validateFiniteStateValue("Final velocity (v)", state.getV());
        validateFiniteStateValue("Acceleration (a)", state.getA());
        validateFiniteStateValue("Time (t)", state.getT());

        if (state.countKnowns() < 3) {
            throw new IllegalArgumentException("At least 3 variables must be provided.");
        }

        if (state.getT() != null && state.getT() < 0) {
            throw new IllegalArgumentException("Time cannot be negative.");
        }

        if (state.countKnowns() > 3) {
            isConsistent(state);

        }
    }

    private void validateFiniteStateValue(String name, Double value) {
        if (value != null && !Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite.");
        }
    }

    /**
     * Checks whether a state containing more than three known variables is
     * consistent with the constant-acceleration equations.
     *
     * Three of the supplied variables are used to independently derive the complete state.
     * Every other supplied variable is then compared against its calculated value using EPSILON
     *
     * @param input the state whose supplied values should be checked
     * @throws IllegalArgumentException if the supplied values are inconsistent
     */

    public void isConsistent(ConstantAccelerationState input) {
        ConstantAccelerationState.Builder baseBuild = ConstantAccelerationState.builder();
        int counter = 0;

        if (input.getS() != null && counter < 3) { baseBuild.s(input.getS()); counter++; }
        if (input.getU() != null && counter < 3) { baseBuild.u(input.getU()); counter++; }
        if (input.getV() != null && counter < 3) { baseBuild.v(input.getV()); counter++; }
        if (input.getA() != null && counter < 3) { baseBuild.a(input.getA()); counter++; }
        if (input.getT() != null && counter < 3) { baseBuild.t(input.getT()); counter++; }

        ConstantAccelerationState derived = solve(baseBuild.build());

        assertConsistent("Displacement (s)", input.getS(), derived.getS());
        assertConsistent("Initial Velocity (u)", input.getU(), derived.getU());
        assertConsistent("Final Velocity (v)", input.getV(), derived.getV());
        assertConsistent("Acceleration (a)", input.getA(), derived.getA());
        assertConsistent("Time (t)", input.getT(), derived.getT());
    }

    private void assertConsistent(String fieldName, Double actual, Double expected) {
        if (actual != null && expected != null) {
            if (Math.abs(actual - expected) > EPSILON) {
                throw new IllegalArgumentException(String.format(
                        "Inconsistent input for %s: provided %.4f, but physics demands %.4f",
                        fieldName, actual, expected
                ));
            }
        }
    }


    /**
     * Creates a builder containing a copy of all values from the supplied state.
     * @param state the state to copy
     * @return a builder initialized with the state's values.
     */
    private ConstantAccelerationState.Builder copyToBuilder(
            ConstantAccelerationState state) {

        ConstantAccelerationState.Builder builder =
                ConstantAccelerationState.builder();

        if (state.getS() != null) {
            builder.s(state.getS());
        }

        if (state.getU() != null) {
            builder.u(state.getU());
        }

        if (state.getV() != null) {
            builder.v(state.getV());
        }

        if (state.getA() != null) {
            builder.a(state.getA());
        }

        if (state.getT() != null) {
            builder.t(state.getT());
        }

        return builder;
    }

    // v = u + at

    /**
     * Applies the first SUVAT equation:
     *
     * <pre>
     * v = u + at
     * </pre>
     *
     * <p>Uses the equation to calculate any one of {@code v}, {@code u},
     * {@code a}, or {@code t} when the other required variables are known.</p>
     *
     * @return {@code true} if a variable was calculated, otherwise {@code false}
     */
    private boolean solveEquation1(ConstantAccelerationState.Builder builder,
                                   Double s, Double u, Double v, Double a, Double t) {

        // Solve for v: v = u + a * t
        if (v == null && u != null && a != null && t != null) {
            builder.v(u + a * t);
            return true;
        }
        // Solve for u: u = v - a * t
        if (u == null && v != null && a != null && t != null) {
            builder.u(v - a * t);
            return true;
        }
        // Solve for a: a = (v - u) / t
        if (a == null && v != null && u != null && t != null) {
            if (t == 0) throw new ArithmeticException("Division by zero: t = 0");
            builder.a((v - u) / t);
            return true;
        }
        // Solve for t: t = (v - u) / a
        if (t == null && v != null && u != null && a != null) {
            if (a == 0) throw new ArithmeticException("Division by zero: a = 0");
            double calculatedT = (v - u) / a;
            if (calculatedT < 0) throw new IllegalArgumentException("Calculated negative time");
            builder.t(calculatedT);
            return true;
        }
        return false;
    }

    // s = 1/2(u+v)t

    /**
     * Applies the second SUVAT equation:
     *
     * <pre>
     * s = 1/2(u + v)t
     * </pre>
     *
     * <p>Uses the equation to calculate any one of {@code s}, {@code u},
     * {@code v}, or {@code t} when the other required variables are known.</p>
     *
     * @return {@code true} if a variable was calculated, otherwise {@code false}
     */
    private boolean solveEquation2(ConstantAccelerationState.Builder builder,
                                   Double s, Double u, Double v, Double a, Double t) {
        if (s == null && u != null && v != null && t != null) {
            builder.s(0.5 * (u + v) * t);
            return true;
        }
        if (u == null && s != null && v != null && t != null) {
            if (t == 0) throw new ArithmeticException("Division by zero: t = 0");
            builder.u((2 * s / t) - v);
            return true;
        }
        if (v == null && s != null && u != null && t != null) {
            if (t == 0) throw new ArithmeticException("Division by zero: t = 0");
            builder.v((2 * s / t) - u);
            return true;
        }
        if (t == null && s != null && u != null && v != null) {
            if (u + v == 0) throw new ArithmeticException("Division by zero: u + v = 0");
            double calculatedT = (2 * s) / (u + v);
            if (calculatedT < 0) throw new IllegalArgumentException("Calculated negative time");
            builder.t(calculatedT);
            return true;
        }
        return false;
    }

    // v2 = u2 + 2as

    /**
     * Applies the third SUVAT equation:
     *
     * <pre>
     * v² = u² + 2as
     * </pre>
     *
     * <p>Uses the equation to calculate any one of {@code v}, {@code u},
     * {@code a}, or {@code s} when the other required variables are known.</p>
     *
     * <p>When calculating a velocity from a squared value, the solver must
     * select an appropriate sign for the resulting velocity.</p>
     *
     * @return {@code true} if a variable was calculated, otherwise {@code false}
     */
    private boolean solveEquation3(ConstantAccelerationState.Builder builder,
                                   Double s, Double u, Double v, Double a, Double t) {
        if (v == null && u != null && a != null && s != null) {
            double vSquared = (u * u) + (2 * a * s);
            if (vSquared < 0) throw new IllegalArgumentException("Impossible physics: v^2 < 0");
            // Note: Sign convention can be inferred from u or a
            double magnitude = Math.sqrt(vSquared);
            builder.v(a < 0 && u <= 0 ? -magnitude : magnitude);
            return true;
        }
        if (u == null && v != null && a != null && s != null) {
            double uSquared = (v * v) - (2 * a * s);
            if (uSquared < 0) throw new IllegalArgumentException("Impossible physics: u^2 < 0");
            builder.u(Math.sqrt(uSquared));
            return true;
        }
        if (a == null && v != null && u != null && s != null) {
            if (s == 0) throw new ArithmeticException("Division by zero: s = 0");
            builder.a(((v * v) - (u * u)) / (2 * s));
            return true;
        }
        if (s == null && v != null && u != null && a != null) {
            if (a == 0) throw new ArithmeticException("Division by zero: a = 0");
            builder.s(((v * v) - (u * u)) / (2 * a));
            return true;
        }
        return false;
    }

    // s = ut + 1/2at^2

    /**
     * Applies the fourth SUVAT equation:
     *
     * <pre>
     * s = ut + 1/2at²
     * </pre>
     *
     * <p>Uses the equation to calculate {@code s}, {@code u}, {@code a}, or
     * {@code t} when the other required variables are known.</p>
     *
     * <p>When calculating time, the equation becomes a quadratic equation.
     * The solver evaluates the resulting roots and selects a non-negative
     * solution.</p>
     *
     * @return {@code true} if a variable was calculated, otherwise {@code false}
     */
    private boolean solveEquation4(ConstantAccelerationState.Builder builder,
                                   Double s, Double u, Double v, Double a, Double t) {
        if (s == null && u != null && a != null && t != null) {
            builder.s((u * t) + (0.5 * a * t * t));
            return true;
        }
        if (u == null && s != null && a != null && t != null) {
            if (t == 0) throw new ArithmeticException("Division by zero: t = 0");
            builder.u((s - (0.5 * a * t * t)) / t);
            return true;
        }
        if (a == null && s != null && u != null && t != null) {
            if (t == 0) throw new ArithmeticException("Division by zero: t = 0");
            builder.a((2 * (s - (u * t))) / (t * t));
            return true;
        }
        if (t == null && s != null && u != null && a != null) {
            // Quadratic equation: 0.5*a*t^2 + u*t - s = 0
            if (a == 0) {
                if (u == 0) throw new ArithmeticException("Division by zero");
                double calculatedT = s / u;
                if (calculatedT >= 0) {
                    builder.t(calculatedT);
                    return true;
                }
            } else {
                double A = 0.5 * a;
                double B = u;
                double C = -s;
                double discriminant = (B * B) - (4 * A * C);
                if (discriminant >= 0) {
                    double t1 = (-B + Math.sqrt(discriminant)) / (2 * A);
                    double t2 = (-B - Math.sqrt(discriminant)) / (2 * A);
                    // Take positive time root
                    if (t1 >= 0) { builder.t(t1); return true; }
                    if (t2 >= 0) { builder.t(t2); return true; }
                }
            }
        }
        return false;
    }
}

