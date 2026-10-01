package math.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinearEquation {
	
    static double[] parseSide(String side) {
        double coefficient = 0, constant = 0;
        Matcher m = Pattern.compile("[+-]?[^+-]+").matcher(side);
        while (m.find()) {
            String term = m.group();
            if (term.endsWith("x")) {
                String num = term.substring(0, term.length() - 1);
                if (num.isEmpty() || num.equals("+")) coefficient += 1;      
                else if (num.equals("-")) coefficient -= 1;                  
                else coefficient += Double.parseDouble(num);                
            } else {
                constant += Double.parseDouble(term);
            }
        }
        return new double[]{coefficient, constant};
    }

	


}
