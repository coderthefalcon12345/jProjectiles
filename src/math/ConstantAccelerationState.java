package math;

public final class ConstantAccelerationState {
    /**
     * ConstantAcceleration is the sole class in jPRojectiles for solving SUVAT equations.
     * All the equations of motion are looped through as long as unknown variables remain.
     * If unknown = 1, isolate & solve.
     */


    private static final double EPSILON = 1e-6;

    private final Double s;
    private final Double u;
    private final Double v;
    private final Double a;
    private final Double t;

    // Helper functions
    private ConstantAccelerationState(Double s, Double u, Double v, Double a, Double t) {
        this.s = s;
        this.u = u;
        this.v = v;
        this.a = a;
        this.t = t;
    }

    public Double getS() { return s; }
    public Double getU() { return u; }
    public Double getV() { return v; }
    public Double getA() { return a; }
    public Double getT() { return t; }

    public boolean isFullySolved() {
        return s != null && u != null && v != null && a != null && t != null;
    }

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

    public static class Builder {
        private Double s,u,v,a,t;

        public Builder s(double val) { this.s = val; return this; }
        public Builder u(double val) { this.u = val; return this; }
        public Builder v(double val) { this.v = val; return this; }
        public Builder a(double val) { this.a = val; return this; }
        public Builder t(double val) { this.t = val; return this; }

        public ConstantAccelerationState build() {
            return new ConstantAccelerationState(s, u, v, a, t);
        }
    }

    public static Builder builder() {
        return new Builder();
    }










}
