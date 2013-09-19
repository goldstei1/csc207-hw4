package edu.grinnell.csc207.goldstei1.calc;

import java.math.BigInteger;

/**
 * An implementation of a calculator.
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
	
	Fraction[] r = new Fraction[8];
	
	// +--------------+-------------------------------------------------
	// | Constructors |
	// +--------------+
	
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
	public static BigInteger eval0(String expression) throws Exception {

		StringBuffer evaluator = new StringBuffer(expression);
		BigInteger endValue = new BigInteger("0"); //first number in string is added later
		char nextOperation = '+'; // first operation is always add (add first number to 0)
		int begOfNum;
		int i = 0; //starting index of the evaluator string

		if (evaluator.charAt(i) == 'r') {
			i++;
			int rIndex = Character.getNumericValue(evaluator.charAt(i));
			if (evaluator.charAt(i + 1) != ' '|| evaluator.charAt(i + 2) != '=' ||
					evaluator.charAt(i + 3) != ' ' ||
					!Character.isDigit(evaluator.charAt(i + 4))) {
				throw new Exception("Incorrect format for storing expressions");
			}
			
		}
			

		while (i < evaluator.length()) {
			//Check for which operation is going to be performed.
			// Skipped first time through the loop
			switch (evaluator.charAt(i)) {
			case '+': nextOperation = '+';
			i += 2;
			break;
			case '-': nextOperation = '-';
			i += 2;
			break;
			case '*': nextOperation = '*';
			i += 2;
			break;
			case '/': nextOperation = '/';
			i += 2;
			break;
			case '^': nextOperation = '^';
			i += 2;
			break;
			}//switch

			//find all digits of the next number

			begOfNum = i; //index of the beginning of the next number

			while(i < evaluator.length() && evaluator.charAt(i) != ' ') { //find any more digits of the next number
				i++;
			}//while
			//end of next number is now i

			//perform nextOperation on endValue and the number represented by the
			// substring between begNum and i
			switch (nextOperation) {
			case '+': endValue = endValue.add(new BigInteger(evaluator.substring(begOfNum, i).toString()));
			break;
			case '-': endValue = endValue.subtract(new BigInteger(evaluator.substring(begOfNum, i).toString()));
			break;
			case '*': endValue = endValue.multiply(new BigInteger(evaluator.substring(begOfNum, i).toString()));
			break;
			case '/': endValue = endValue.divide(new BigInteger(evaluator.substring(begOfNum, i).toString()));
			break;
			case '^': endValue = endValue.pow(new Integer(evaluator.substring(begOfNum, i).toString()));
			break;
			}//switch

			i++;
		}//While

		return endValue;
	}//eval0

}
