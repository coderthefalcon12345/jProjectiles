package math;

public final class ConstantAccelerationState {

    /**
     * Immutable representation of the five variables used in the SUVAT equations
     * for constant-acceleration motion.
     *
     * <p>The variables are:</p>
     * <ul>
     *     <li>{@code s} - displacement</li>
     *     <li>{@code u} - initial velocity</li>
     *     <li>{@code v} - final velocity</li>
     *     <li>{@code a} - constant acceleration</li>
     *     <li>{@code t} - time</li>
     * </ul>
     *
     * <p>Any variable may be {@code null} to represent an unknown value. A state
     * containing at least three known variables can be passed to
     * {@link ConstantAccelerationSolve} to calculate the remaining values.</p>
     *
     * <p>Instances are immutable. Use {@link #builder()} to create a state.</p>
     */


    private static final double EPSILON = 1e-6;

    private final Double s;
    private final Double u;
    private final Double v;
    private final Double a;
    private final Double t;

    // Helper functions

    /**
     * Creates a constant-acceleration state.
     *
     * @param s displacement, or {@code null} if unknown
     * @param u initial velocity, or {@code null} if unknown
     * @param v final velocity, or {@code null} if unknown
     * @param a acceleration, or {@code null} if unknown
     * @param t time, or {@code null} if unknown
     */
    private ConstantAccelerationState(Double s, Double u, Double v, Double a, Double t) {
        this.s = s;
        this.u = u;
        this.v = v;
        this.a = a;
        this.t = t;
    }

    /**
     * Returns the displacement.
     *
     * @return displacement, or {@code null} if unknown
     */
    public Double getS() { return s; }

    /**
     * Returns the initial velocity.
     *
     * @return initial velocity, or {@code null} if unknown
     */
    public Double getU() { return u; }

    /**
     * Returns the final velocity.
     *
     * @return final velocity, or {@code null} if unknown
     */
    public Double getV() { return v; }

    /**
     * Returns the acceleration.
     *
     * @return acceleration, or {@code null} if unknown
     */
    public Double getA() { return a; }

    /**
     * Returns the elapsed time.
     *
     * @return time, or {@code null} if unknown
     */
    public Double getT() { return t; }


    /**
     * Determines whether all five SUVAT variables are known.
     *
     * @return {@code true} if all variables have values, otherwise {@code false}
     */
    public boolean isFullySolved() {
        return s != null && u != null && v != null && a != null && t != null;
    }


    /**
     * Counts the number of known SUVAT variables in this state.
     *
     * @return the number of non-null variables, from {@code 0} to {@code 5}
     */
    public int countKnowns() {
        int count = 0;
        if (s != null) count++;
        if (u != null) count++;
        if (v != null) count++;
        if (a != null) count++;
        if (t != null) count++;
        return count;
    }

    // Helper functions end

    /**
     * Builder used to construct {@link ConstantAccelerationState} instances.
     *
     * <p>Unset variables remain {@code null}, allowing a state to represent
     * partially known SUVAT values.</p>
     */
    public static class Builder {
        private Double s,u,v,a,t;

        public Builder s(double val) { this.s = val; return this; }
        public Builder u(double val) { this.u = val; return this; }
        public Builder v(double val) { this.v = val; return this; }
        public Builder a(double val) { this.a = val; return this; }
        public Builder t(double val) { this.t = val; return this; }



        /**
         * Constructs an immutable {@link ConstantAccelerationState} from the
         * values currently stored in this builder.
         *
         * @return a new constant-acceleration state
         */
        public ConstantAccelerationState build() {
            return new ConstantAccelerationState(s, u, v, a, t);
        }
    }

    /**
     * Creates a new builder for constructing a
     * {@link ConstantAccelerationState}.
     *
     * @return a new empty builder
     */
    public static Builder builder() {
        return new Builder();
    }










}
