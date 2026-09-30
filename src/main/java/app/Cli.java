package app;

import math.constantacceleration.ConstantAccelerationSolve;
import math.constantacceleration.ConstantAccelerationState;
import math.constantacceleration.ConstantAccelerationProjectileSolve;
import math.constantacceleration.ConstantAccelerationProjectileState;
import math.variableacceleration.VariableAccelerationSolve;

import java.util.Scanner;


/**
 * Command-line interface for the jProjectiles application.
 * <p>
 * Provides interactive text menus for solving 1D constant acceleration (SUVAT)
 * kinematics problems and 2D projectile motion calculations.
 * </p>
 * **/
public class Cli {


    /**
     * Scanner instance for reading console input from {@link System#in}.
     */
    private final Scanner scanner = new Scanner(System.in);


    /**
     * Solver engine for 1D constant acceleration (SUVAT) kinematic equations.
     */
    private final ConstantAccelerationSolve suvatSolver =
            new ConstantAccelerationSolve();


    /**
     * Solver engine for 2D projectile motion trajectories.
     */
    private final ConstantAccelerationProjectileSolve projectileSolver =
            new ConstantAccelerationProjectileSolve();

    /**
     * Solver engine for 2D variable acceleration equations, using euler's method.
     */
    private final VariableAccelerationSolve variableAccelerationSolver =
            new VariableAccelerationSolve();



    /**
     * Starts the main application loop, displaying the primary menu and handling user selection.
     * <p>
     * Continues execution until the user selects the exit option. Closes the underlying
     * {@link Scanner} prior to termination.
     * </p>
     */
    public void run() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("jProjectiles cli v1.0");
            System.out.println("1. SUVAT (1D)");
            System.out.println("2. Projectile (2D, now with angles)");
            System.out.println("3. Variable Acceleration (2D)");
            System.out.println("4. Exit app");
            System.out.print("Select >> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> runSuvat();
                case "2" -> runProjectile();
                case "3" -> runVariableAcceleration();
                case "4" -> running = false;
                default -> System.out.println("Invalid selection.");
            }
        }

        scanner.close();
    }


    /**
     * Executes the interactive flow for the 1D SUVAT solver.
     * <p>
     * Prompts the user for optional kinematic parameters (displacement, initial velocity,
     * final velocity, acceleration, and time), constructs a state object, and displays
     * the computed result. Displays an error message if the input parameters are
     * mathematically unsolvable or invalid.
     * </p>
     */
    private void runSuvat() {
        System.out.println();
        System.out.println("1D SUVAT Solver");
        System.out.println("If unknown: do NOT input anything.");
        System.out.println();

        Double s = readOptionalDouble("Displacement, s -> ");
        Double u = readOptionalDouble("Initial velocity, u -> ");
        Double v = readOptionalDouble("Final velocity, v -> ");
        Double a = readOptionalDouble("Acceleration, a -> ");
        Double t = readOptionalDouble("Time, t -> ");

        ConstantAccelerationState.Builder builder =
                ConstantAccelerationState.builder();

        if (s != null) builder.s(s);
        if (u != null) builder.u(u);
        if (v != null) builder.v(v);
        if (a != null) builder.a(a);
        if (t != null) builder.t(t);

        try {
            ConstantAccelerationState input = builder.build();
            ConstantAccelerationState result = suvatSolver.solve(input);

            System.out.println();
            System.out.println("Result.");
            printSuvat(result);

        } catch (IllegalArgumentException | ArithmeticException |
                 IllegalStateException e) {

            System.out.println();
            System.out.println("Unable to solve: " + e.getMessage());
        }
    }


    /**
     * Executes the interactive flow for the 2D projectile motion solver.
     * <p>
     * Prompts the user for initial speed, launch angle, and target elapsed time.
     * Computes and prints the horizontal and vertical positions and velocities
     * resulting from the trajectory.
     * </p>
     */
    private void runProjectile() {
        System.out.println();
        System.out.println("2D Projectile Solver");

        double speed = readRequiredDouble("Initial speed, ms^-1 -> ");
        double angle = readRequiredDouble("Launch angle in degrees -> ");
        double time = readRequiredDouble("Time in seconds -> ");

        try {
            ConstantAccelerationProjectileState result =
                    projectileSolver.solve(speed, angle, time);

            System.out.println();
            System.out.println("Result");

            System.out.printf(
                    "X position: %.4f m%n",
                    result.getX().getS()
            );

            System.out.printf(
                    "Y position: %.4f m%n",
                    result.getY().getS()
            );

            System.out.printf(
                    "X velocity: %.4f m/s%n",
                    result.getX().getV()
            );

            System.out.printf(
                    "Y velocity: %.4f m/s%n",
                    result.getY().getV()
            );

        } catch (IllegalArgumentException | ArithmeticException |
                 IllegalStateException e) {

            System.out.println();
            System.out.println("Unable to solve: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter a numeric value or leave the input empty.
     * <p>
     * Re-prompts continuously until a valid double-precision floating point value
     * or an empty line is provided.
     * </p>
     *
     * @param prompt the text message displayed to the user requesting input
     * @return the parsed {@link Double} value, or {@code null} if the user entered blank input
     */
    private Double readOptionalDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or leave it blank.");
            }
        }
    }

    private double readRequiredDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void printSuvat(ConstantAccelerationState state) {
        System.out.printf("Displacement, s: %.4f%n", state.getS());
        System.out.printf("Initial velocity, u: %.4f%n", state.getU());
        System.out.printf("Final velocity, v: %.4f%n", state.getV());
        System.out.printf("Acceleration, a: %.4f%n", state.getA());
        System.out.printf("Time, t: %.4f%n", state.getT());
    }








    private void runVariableAcceleration() {
        System.out.println();
        System.out.println("Variable Acceleration Solver");

        double position = readRequiredDouble("Initial position, x -> ");
        double velocity = readRequiredDouble("Initial velocity, v -> ");
        double endTime = readRequiredDouble("End time, t -> ");
        double dt = readRequiredDouble("Time step, dt -> ");

        System.out.println();
        System.out.println("Acceleration function:");
        System.out.println("1. Constant acceleration");
        System.out.println("2. Acceleration proportional to velocity");
        System.out.println("3. Acceleration proportional to position");
        System.out.println("4. Acceleration depending on time");
        System.out.print("Select >> ");

        String choice = scanner.nextLine().trim();

        math.variableacceleration.AccelerationFunction acceleration;

        switch (choice) {
            case "1" -> {
                double value =
                        readRequiredDouble("Acceleration, a -> ");

                acceleration = (x, v, t) -> value;
            }

            case "2" -> {
                double coefficient =
                        readRequiredDouble("Velocity coefficient -> ");

                acceleration = (x, v, t) -> coefficient * v;
            }

            case "3" -> {
                double coefficient =
                        readRequiredDouble("Position coefficient -> ");

                acceleration = (x, v, t) -> coefficient * x;
            }

            case "4" -> {
                double coefficient =
                        readRequiredDouble("Time coefficient -> ");

                acceleration = (x, v, t) -> coefficient * t;
            }

            default -> {
                System.out.println("Invalid acceleration function.");
                return;
            }
        }

        try {
            math.variableacceleration.VariableAccelerationState initial =
                    new math.variableacceleration.VariableAccelerationState(
                            position,
                            velocity,
                            0
                    );

            math.variableacceleration.VariableAccelerationState result =
                    variableAccelerationSolver.solve(
                            initial,
                            endTime,
                            dt,
                            acceleration
                    );

            System.out.println();
            System.out.println("Result");

            System.out.printf(
                    "Position: %.4f m%n",
                    result.getPosition()
            );

            System.out.printf(
                    "Velocity: %.4f m/s%n",
                    result.getVelocity()
            );

            System.out.printf(
                    "Time: %.4f s%n",
                    result.getTime()
            );

        } catch (IllegalArgumentException | ArithmeticException |
                 IllegalStateException e) {

            System.out.println();
            System.out.println("Unable to solve: " + e.getMessage());
        }
    }
}