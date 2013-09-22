package edu.grinnell.csc207.goldstei1.calc;

import java.io.PrintWriter;

/**
 * An implementation of a calculator that can store 9 
 * 
 * @author Daniel Goldstein
 */
public class Calculator {

	
	// +------------------+---------------------------------------------
	// | Design Decisions |
	// +------------------+

		/*
		 * 
		 */

	// +--------+-------------------------------------------------------
	// | Fields |
	// +--------+
	
	static Fraction[] r = new Fraction[9];
	
	// +--------------+-------------------------------------------------
	// | Constructors |
	// +--------------+
	
	public Calculator() {
		return;
	}
	
	// +-------------------------+--------------------------------------
	// | Standard Object Methods |
	// +-------------------------+
	
	// +-----------------+----------------------------------------------
	// | Private Methods |
	// +-----------------+
	
	// +---------+------------------------------------------------------
	// | Methods |
	// +---------+
	
	/**
	 * Method to solve a mathematical expression given as a string
	 * with spaces between each number and operation. The numbers
	 * in the string can be larger than a normal integer.
	 * Preconditions: a number following a '^' cannot be larger than Integer.MAX_VALUE
	 */
	public static Fraction evaluate(String expression) throws Exception {

		StringBuffer evaluator = new StringBuffer(expression);
		char nextOperation; // first operation is always add (add first number to 0)
		int i = 0; //starting index of the evaluator string
		int begOfNum;
		int rIndex;
		Fraction frac1;
		Fraction frac2;

		if (evaluator.charAt(i) == 'r') {
			i++;

			// Check for correct formatting of the form r'digit' = Fraction(or int)
			if (Character.isDigit(evaluator.charAt(i)) &&
					Character.getNumericValue(evaluator.charAt(i)) <= 8 &&
					evaluator.charAt(++i) == ' ') {

				rIndex = Character.getNumericValue(evaluator.charAt(1));

				if (evaluator.charAt(++i) == '=') {
					if (evaluator.charAt(++i) == ' ' && Character.isDigit(evaluator.charAt(++i))) {
						try {
							frac1 = new Fraction(evaluator.substring(i, evaluator.length()));
						}// try
						
						catch (Exception e){
							// Find the location of the incorrect input by adding the 
							// number given in the exception that is caught to the start of
							// the fraction that was input.
							int malformedChar = Character.getNumericValue(e.getMessage().charAt(0)) + 5;
							throw new Exception("Malformed input at character: " + malformedChar);
						}// catch(Exception e)

						r[rIndex] = frac1;
						return r[rIndex];
					}// if (evaluator(4) == ' ' and evaluator(5) is a digit)
					else {
						throw new Exception("Malformed input at character: " + i);
					}// else
				}// if (evaluator(3) == '=')
				
				else {
					
					if(r[rIndex] != null) {
						frac1 = r[rIndex];
					}// if (r[rIndex != null])
					
					else {
						throw new Exception("r" + rIndex + "is currently empty");
					}// else
				}// else
			}// if (evaluator(1)) is a digit and evaluator(2) == ' ')
			
			else {
				throw new Exception("Malformed input at character: " + i);
			}
			
		}// if(evaluator(0) == 'r')
		
		else if (Character.isDigit(evaluator.charAt(i))) {
			while (i < evaluator.length() && evaluator.charAt(i) != ' ') {
				if (evaluator.charAt(i) != '/' && !Character.isDigit(evaluator.charAt(i))) {
					throw new Exception("Malformed input at character: " + i);
				}
				else {
					i++;
				}
			}
			
			try {
				frac1 = new Fraction(evaluator.substring(0, i));
				i++;
			}
			catch (Exception e) {
				int malformedChar = Character.getNumericValue(e.getMessage().charAt(0));
				throw new Exception("Malformed input at character: " + malformedChar);
			}// catch
		}// else if(evaluator(i)) is a digit
		
		else {
			throw new Exception("Malformed input at character: " + i);
		}

		while (i < evaluator.length()) {
			//Check for which operation is going to be performed.
			// Skipped first time through the loop
			switch (evaluator.charAt(i)) {
			case '+': nextOperation = '+';
			break;
			case '-': nextOperation = '-';
			break;
			case '*': nextOperation = '*';
			break;
			case '/': nextOperation = '/';
			break;
			case '^': nextOperation = '^';
			break;
			default : throw new Exception("Malformed input at character: " + i);
			}//switch

			if ( evaluator.charAt(++i) != ' ') {
				throw new Exception("Malformed input at character: " + i);
			}
			
			if (evaluator.charAt(++i) == 'r') {
				if(Character.isDigit(evaluator.charAt(++i)) &&
						Character.getNumericValue(evaluator.charAt(i)) <= 8) {
					rIndex = Character.getNumericValue(evaluator.charAt(i));
				}
				else {
					throw new Exception("Malformed input at character: " + i);
				}
				
				if (r[rIndex] != null) {
					frac2 = r[rIndex];
				}// if (r[rIndex != null])
				
				else {
					throw new Exception("r" + rIndex + "is currently empty");
				}// else
				
				if (++i < evaluator.length() && evaluator.charAt(i) != ' ') {
					throw new Exception("Malformed input at character: " + i);
				}
			}
			
			else if (Character.isDigit(evaluator.charAt(i))) {
				begOfNum = i;
				i++;
				while (i < evaluator.length() && evaluator.charAt(i) != ' ') {
					if (evaluator.charAt(i) != '/' && !Character.isDigit(evaluator.charAt(i))) {
						throw new Exception("Malformed input at character: " + i);
					}
					else {
						i++;
					}
				}
				frac2 = new Fraction(evaluator.substring(begOfNum, i));
			}
			
			else {
				throw new Exception("Malformed input at character: " + i);
			}
			
			switch (nextOperation) {
			case '+': frac1 = frac1.add(frac2);
			break;
			case '-': frac1 = frac1.subtract(frac2);
			break;
			case '*': frac1 = frac1.multiply(frac2);
			break;
			case '/': frac1 = frac1.divide(frac2);
			break;
			case '^': frac1 = frac1.Expt(2);
			break;
			}//switch
			i++;			
	
		}//While

		return frac1;
	}// evaluate


public static void main(String[] args) {
	PrintWriter pen = new PrintWriter(System.out, true);
	try {
	Calculator.evaluate("r0 = 1/2"); Calculator.evaluate("r1 = 2/3");
	Fraction frac = Calculator.evaluate("r0 + r 1");
	pen.println(frac.toString());
	}
	catch (Exception e) {
		pen.println(e.getMessage());
	}
}


}// Calculator
