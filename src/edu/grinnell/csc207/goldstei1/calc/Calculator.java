package edu.grinnell.csc207.goldstei1.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
				if (expSplit[i].length() == 2 &&
						Character.isDigit(expSplit[i].charAt(1)) &&
						Character.getNumericValue(expSplit[i].charAt(1)) <= 8) {

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
					throw new Exception("Malformed input: " + "\"" + expSplit[i] + "\"");
				}
			}

			else {
				try {
					fracs[i / 2] = new Fraction(expSplit[i]);
				} catch (Exception e) {
					throw new Exception("Malformed input: " + "\"" + expSplit[i] + "\"");
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
	
	public static Fraction[] evaluate(String[] expressions) throws Exception {
		Fraction[] answers = new Fraction[expressions.length];
		for (int i = 0; i < expressions.length; i++) {
			try {
			answers[i] = evaluate(expressions[i]);
			}
			catch (Exception e) {
				throw new Exception("Expression " + i + ": " + e.getMessage());
			}
		}
		return answers;
	}

	public static void main(String[] args) throws Exception {
		PrintWriter pen = new PrintWriter(System.out, true);
		InputStreamReader istream = new java.io.InputStreamReader(System.in);
		BufferedReader eyes = new java.io.BufferedReader(istream);
		String[] expressions;
		boolean on = true;

		pen.println("Welcome to the fraction calculator.\nThis calculater allows you to enter"
				+ " a string or list of strings containing fractions and integers that you want"
				+ " to be solved.\nAvailable operations include +,-,*,/,^\n"
				+ "Lists of expressions must be separated by a ':'.\nEnter quit to exit");

		while (on) {
			pen.println("Please enter an expression: ");
			expressions = eyes.readLine().split(":");
			if (expressions[0].equals("quit")) {
				pen.println("Goodbye");
				on = false;
			} else {
				try {
					for (int i = 0; i < expressions.length; i++) {
						pen.print("{" + evaluate(expressions[i]) + "} ");
					}
					pen.println();
				} catch (Exception e) {
					pen.println(e.getMessage());
				}
			}
		}
	}

}// Calculator
