package edu.grinnell.csc207.goldstei1.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * An implementation of a calculator that can solve expressions of fractions
 * and integers along with being able to store 9 fractions
 * 
 * @author Daniel Goldstein
 * 
 * I sat next to Earnest while writing evaluate and discussed some parts of how to make it more efficient and use less code
 */
public class Calculator {

	// +--------+-------------------------------------------------------
	// | Fields |
	// +--------+

	/**
	 * Storage field that can store up to 9 fractions that the user sets.
	 */
	static Fraction[] r = new Fraction[9];

	// +---------+------------------------------------------------------
	// | Methods |
	// +---------+

	/**
	 * Method to solve a mathematical expression given as a string with spaces
	 * between each number and operation. The numbers in the string can be
	 * larger than a normal integer. 
	 * Returns a fraction: the solution to the expression
	 * or throws an exception informing the user what is wrong with their input.
	 * Preconditions: a number following a '^' cannot be larger than Integer.MAX_VALUE
	 */
	public static Fraction evaluate(String expression) throws Exception {
		
        // Splits the expression at every space. Numbers and operations should be alternating
		String[] expSplit = expression.split(" "); 
		// expSplit.length should be odd or there is incorrect input. So subtracting by
		// half of it will subtract by the floor of half of it and return how many fractions
		// there are. 
		int fracCount = expSplit.length - (expSplit.length / 2);
		int rIndex; // stores the value of which storage space is being accessed
		Fraction[] fracs = new Fraction[fracCount]; // array to hold the fractions in expression
		Fraction finalFrac; // Changing fraction that will end as the anser to the expression
		
		// If the number of terms in expression is even then there is some form of malformed
		// input either ending with an operation or having two of either an operation or
		// number in a row.
		if (expSplit.length % 2 == 0) {
			throw new Exception("Malformed input: Numbers and operations must alternate " +
					"and your expression must not end or start with an operation.");
		}

		// Start putting the fractions/integers in an array looping through every 2 strings
		// in the split expression.
		for (int i = 0; i < expSplit.length; i += 2) {
			if (expSplit[i].charAt(0) == 'r') {
				// Make sure that if an r storage space is being used, that it is correct syntax
				if (expSplit[i].length() == 2
						&& Character.isDigit(expSplit[i].charAt(1))
						&& Character.getNumericValue(expSplit[i].charAt(1)) <= 8) {

					rIndex = Character.getNumericValue(expSplit[i].charAt(1));

					//If this is the only part of the expression, return r[rIndex]
					if (expSplit.length == 1) {
						// Make sure r[rIndex] has a value stored and if not, throw an
						// exception
						if (r[rIndex] != null) {
							return r[rIndex];
						} 
						else {
							throw new Exception("Malformed input: r" + rIndex
									+ " is not defined");
						}
					}
					
					// If it isn't the only part of expression and it is not the first element,
					// or if it is the first element and it is not followed by an equals sign,
					// put r[rIndex] into the array of fractions
					else if (i != 0 || !expSplit[1].equals("=")) {
						// Make sure that r[rIndex] is defined, otherwise throw an exception
						if (r[rIndex] != null) {
							fracs[i / 2] = r[rIndex];
						} else {
							throw new Exception("Malformed input: r" + rIndex
									+ " is not defined");
						}
					}
					
					// else if r[rIndex] is the first element in expression and it is followed
					// by an equals sign:
					else {
						// recurse and set r[rIndex] to be the evaluation of the rest of the 
						// expression. If there is a problem with part of the rest of the 
						// expression, pass on that exception's message
						try {
							r[rIndex] = evaluate(expression.substring(5));
							return r[rIndex];
						} catch (Exception e) {
							throw e;
						}
					}
				}
				
				// If the syntax used on the storage element is incorrect, throw an appropriate,
				// exception.
				else {
					throw new Exception("Malformed input: " + "\""
							+ expSplit[i] + "\"");
				}
			}
			
			// The element being looked at is not trying to access a storage element r0-r8
			else {
				// Try to make a new fraction from this element and add it to the array
				// of fractions. If it can not be converted to a fraction then the input
				// is malformed and an exception is thrown
				try {
					fracs[i / 2] = new Fraction(expSplit[i]);
				} catch (Exception e) {
					throw new Exception("Malformed input: " + "\""
							+ expSplit[i] + "\"");
				}
			}
		}
		
		// finalFrac starts as the first fraction in expression and the changed by the
		// operations done to it.
		finalFrac = fracs[0];

		// Start looping through the other half of the expression, finding the operations.
		for (int i = 1; i < expSplit.length; i += 2) {

			if (expSplit[i].length() != 1) {
				throw new Exception("Malformed Input: " + expSplit[i]);
			}
			// Check which operation is next and apply that operation to finalFrac and the
			// fraction after the operation.
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
				// Try to raise the fraction to the next number in the expression
				// if it is not an integer then it throws an appropriate exception.
				try {
					finalFrac = finalFrac.expt(Integer
							.parseInt(expSplit[i + 1]));
				} catch (Exception e) {
					throw new Exception("Malformed Input: The '^' operation "
							+ "must be followed by an integer.");
				}
				break;
			default:
				// If the operation does not match any of these then the input is malformed
				// and an appropriate exception is thrown.
				throw new Exception("Malformed input: " + expSplit[i]
						+ " is not an accepted operation here.");
			}// switch
		}
		// return the ending solution
		return finalFrac;
	}

	/**
	 * Method to evaluate an array of expressions with fractions and storage spaces.
	 * Returns an array of fractions: the solutions to the expressions.
	 * Throws an exception informing of which expression is incorrectly written and which
	 *  part of that expression is malformed.
	 */
	public static Fraction[] evaluate(String[] expressions) throws Exception {
		
		Fraction[] answers = new Fraction[expressions.length];
		// loop through the expressions given and evaluate each. Put them into the array of
		// answers and throw an exception if there is incorrect input in any of the expressions.
		for (int i = 0; i < expressions.length; i++) {
			try {
				answers[i] = evaluate(expressions[i]);
			} catch (Exception e) {
				throw new Exception("Expression " + i + ": " + e.getMessage());
			}
		}
		return answers;
	}

	// Main method that runs a simple user interface for the Calculator class
	public static void main(String[] args) throws Exception {
		
		PrintWriter pen = new PrintWriter(System.out, true);
		InputStreamReader istream = new java.io.InputStreamReader(System.in);
		BufferedReader eyes = new java.io.BufferedReader(istream);
		String[] expressions;
		boolean on = true;

		// Start up the calculator and print the instructions for using it
		pen.println("Welcome to the fraction calculator.\nThis calculater allows you to enter"
				+ " a string or list of strings containing fractions and integers that you want"
				+ " to be solved.\nAvailable operations include +,-,*,/,^\n"
				+ "Store fractions in storage spaces r0-r8 by typing ri = fraction\n"
				+ "Lists of expressions must be separated by a ':'.\nEnter quit to exit");

		// Loop where the user enters expressions to be computed until they type quit.
		// gives appropriate errors for incorrect input.
		while (on) {
			pen.println("Please enter an expression: ");
			expressions = eyes.readLine().split(":");
			if (expressions[0].equals("quit")) {
				pen.println("Goodbye");
				on = false;
			}// if(expression[0] = "quit")
			
			else {
				try {
					for (int i = 0; i < expressions.length; i++) {
						pen.print("{" + evaluate(expressions[i]) + "} ");
					}// for (int i = 0...)
					pen.println();
				}// try
				catch (Exception e) {
					pen.println(e.getMessage());
					pen.println("Numbers and operands must be separated by a space and they must alternate.");
				}// catch
			}// else
		}// while(on)
	}// main(String[] args)

}// Calculator
