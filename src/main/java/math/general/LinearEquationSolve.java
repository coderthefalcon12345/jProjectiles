package math.general;


public class LinearEquationSolve {
	
    public static String solve(String equation) {
        String[] sides = equation.replace(" ", "").toLowerCase().split("=");
        if (sides.length != 2) return "Needs exactly one '=' sign.";
 
        double[] left = LinearEquation.parseSide(sides[0]);
        double[] right = LinearEquation.parseSide(sides[1]);
 
        // Move x terms to the left, constants to the right: a*x = b
        double a = left[0] - right[0];
        double b = right[1] - left[1];	
 
        if (a == 0) {
            return (b == 0) ? "Infinitely many solutions." : "No solution.";
        }
        return "x = " + (b / a);

	


    }
}
