package edu.grinnell.csc207.goldstei1.calc;

import java.io.PrintWriter;

/**
 * An implementation of a calculator that can store 9 
 * 
 * @author Daniel Goldstein
 */
public class Calculator {

	// +--------+-------------------------------------------------------
	// | Fields |
	// +--------+
	
	static Fraction[] r = new Fraction[9];
	
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

		String[] expSplit = expression.split(" ");
		int fracCount = expSplit.length - (expSplit.length / 2);
		int rIndex;
		Fraction[] fracs = new Fraction[fracCount];
		Fraction finalFrac;

		for (int i = 0; i < expSplit.length; i += 2) {
			if (expSplit[i].charAt(0) == 'r') {
				if (Character.isDigit(expSplit[i].charAt(1))
						&& Character.getNumericValue(expSplit[i].charAt(1)) <= 8
						&& expSplit[i].length() == 2) {

					rIndex = Character.getNumericValue(expSplit[i].charAt(1));

					if (expSplit.length == 1) {
						if (r[rIndex] != null) {
							fracs[i / 2] = r[rIndex];
						} else {
							throw new Exception("Malformed input: r" + rIndex
									+ " is not defined");
						}
					}

					else if (i != 0 || !expSplit[1].equals("=")) {
						if (r[rIndex] != null) {
							fracs[i / 2] = r[rIndex];
						} else {
							throw new Exception("Malformed input: r" + rIndex
									+ " is not defined");
						}
					}

					else {
						try {
							r[rIndex] = evaluate(expression.substring(5));
							return r[rIndex];
						} catch (Exception e) {
							throw e;
						}
					}
				} 
				
				else {
					throw new Exception("Malformed input: " + expSplit[i]);
				}
			}

			else {
				try {
					fracs[i / 2] = new Fraction(expSplit[i]);
				} catch (Exception e) {
					throw new Exception("Malformed input: " + expSplit[i]);
				}
			}
		}

		finalFrac = fracs[0];

		for (int i = 1; i < expSplit.length; i += 2) {

			switch (expSplit[i].charAt(0)) {
			case '+':
				finalFrac = finalFrac.add(fracs[i / 2 + 1]);
				break;
			case '-':
				finalFrac = finalFrac.subtract(fracs[i / 2 + 1]);
				break;
			case '*':
				finalFrac = finalFrac.multiply(fracs[i / 2 + 1]);
				break;
			case '/':
				finalFrac = finalFrac.divide(fracs[i / 2 + 1]);
				break;
			case '^':
				try {
					finalFrac = finalFrac.expt(Integer
							.parseInt(expSplit[i + 1]));
				} catch (Exception e) {
					throw new Exception("Malformed Input: The '^' operation "
							+ "must be followed by an integer.");
				}
				break;
			default:
				throw new Exception("Malformed input: " + expSplit[i]
						+ " is not an accepted operation here.");
			}// switch
		}
		return finalFrac;
	}

public static void main(String[] args) {
	PrintWriter pen = new PrintWriter(System.out, true);
	try {
	Calculator.evaluate("r0 = 1/2");
	Calculator.evaluate("r1 = 2/3");
	Fraction frac = Calculator.evaluate("r0 + r1");
	Fraction frac2 = Calculator.evaluate("3/4 / 2/2 + r1 - 1/3 * 1/7");
	pen.println(frac.toString());
	pen.println(frac2.toString());
	pen.println(Calculator.evaluate("r1 ^ 3"));
	}
	catch (Exception e) {
		pen.println(e.getMessage());
	}
}


}// Calculator
