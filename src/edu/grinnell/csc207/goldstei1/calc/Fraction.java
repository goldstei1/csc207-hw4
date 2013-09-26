package edu.grinnell.csc207.goldstei1.calc;

import java.math.BigInteger;

/**
 * A simple implementation of fractions.
 * 
 * @author Samuel A. Rebelsky
 * @author Daniel Goldstein
 * @author CSC207 2013F
 */
public class Fraction {

	// +------------------+---------------------------------------------
	// | Design Decisions |
	// +------------------+

	/*
	 * Fractions are immutable. Once you've created one, it stays that way.
	 * 
	 * Fractions are arbitrary precision.
	 * 
	 * Denominators are always positive. Therefore, negative fractions are
	 * represented with a negative numerator. Similarly, if a fraction has a
	 * negative numerator, it is negative.
	 * 
	 * Fractions are stored in simplified form.
	 */

	private static BigInteger NEGATIVE_ONE = BigInteger.valueOf(-1);

	// +--------+-------------------------------------------------------
	// | Fields |
	// +--------+

	/** The numerator of the fraction. Can be positive, zero or negative. */
	BigInteger numerator;

	/** The denominator of the fraction. Must be positive. */
	BigInteger denominator;

	// +--------------+-------------------------------------------------
	// | Constructors |
	// +--------------+

	/**
	 * Create a new fraction equivalent to numerator/denominator.
	 */
	public Fraction(BigInteger numerator, BigInteger denominator)
			throws Exception {
		if (denominator.signum() == 0) {
			throw new Exception("Zero is an invalid denominator");
		}
		this.numerator = numerator;
		this.denominator = denominator;
		this.cleanup();
	} // Fraction(BigInteger, BigInteger)

	/**
	 * Create a new fraction from two integers
	 */
	public Fraction(int numerator, int denominator) throws Exception {
		if (denominator == 0) {
			throw new Exception("Zero is an invalid denominator");
		}
		this.numerator = BigInteger.valueOf(numerator);
		this.denominator = BigInteger.valueOf(denominator);
		this.cleanup();
	} // Fraction(int, int)

	/**
	 * Create a fraction from one integer
	 */
	public Fraction(int num) {
		this.numerator = BigInteger.valueOf(num);
		this.denominator = BigInteger.ONE;
	}

	/**
	 * Create a fraction from a string that is either an integer or a fraction
	 */
	public Fraction(String expression) throws Exception {
		StringBuffer frac = new StringBuffer(expression);
		int i = 0;
		int begOfDenom = 0;
		String Numer = null;

		while (i < frac.length()) {
			if (frac.charAt(i) == '/') {
				Numer = frac.substring(0, i);
				begOfDenom = i + 1;
			} 
			else if (!Character.isDigit(frac.charAt(i))) {
				if (i != 0 || frac.charAt(i) != '-') {
					if (Numer != null && i != begOfDenom) {
						throw new Exception(Integer.toString(i));
						// Throw exception reporting where the string is malformed
					}
				}
			}
			i++;
		}

		if (Numer == null) {
			this.numerator = new BigInteger(frac.substring(0, i));
			this.denominator = BigInteger.ONE;
		}

		else {
			this.numerator = new BigInteger(Numer);
			this.denominator = new BigInteger(frac.substring(begOfDenom, i));
			if (this.denominator.signum() == 0) {
				throw new Exception("Zero is an invalid denominator");
			}
		}
		this.cleanup();
	}

	// +-------------------------+--------------------------------------
	// | Standard Object Methods |
	// +-------------------------+

	/**
	 * Convert this fraction to a string for ease of printing.
	 */
	public String toString() {
		// Lump together the string represention of the numerator,
		// a slash, and the string representation of the denominator
		// and return
		return this.numerator + "/" + this.denominator;
	} // toString()

	/**
	 * Takes an object and returns true if it is equal to this fraction
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Fraction) {
			Fraction fracObj = (Fraction) obj;
			try {
				return this.subtract(fracObj).numerator.equals(BigInteger.ZERO);
			} 
			catch (Exception e) {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Returns the hashcode of this fraction
	 */
	public int hashCode() {
		return this.numerator.hashCode() * this.denominator.hashCode();
	}

	/**
	 * Clones this fraction.
	 */
	public Fraction clone() {
		try {
			return new Fraction(this.numerator, this.denominator);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Takes a fraction and returns 0 if they are equal, 1 if this fraction is
	 * bigger than comparer, and -1 if it is less than comparer. Returns 1 if
	 * comparer is an invalid fraction.
	 */
	public int compareTo(Fraction comparer) {
		if (this.equals(comparer)) {
			return 0;
		} // if
		else
			try {
				if (this.subtract(comparer).numerator.signum() == 1) {
					return 1;
				} // else if
				else {
					return -1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} // else
		return 1;
	}

	// +-----------------+----------------------------------------------
	// | Private Methods |
	// +-----------------+

	/**
	 * Cleanup the fraction by eliminating any negative sign from the
	 * denominator and moving it to the numerator. Also calls simplify so that
	 * the fraction is represented in its simplified form.
	 */
	private void cleanup() {
		if (this.denominator.signum() < 0) {
			this.denominator = this.denominator.abs();
			this.numerator = this.numerator.multiply(NEGATIVE_ONE);
		}
		this.simplify();
	} // cleanup()

	/**
	 * Simplifies this fraction
	 */
	private void simplify() {
		BigInteger gcd = this.numerator.gcd(this.denominator);
		this.numerator = this.numerator.divide(gcd);
		this.denominator = this.denominator.divide(gcd);
	} // simplify()

	// +---------+------------------------------------------------------
	// | Methods |
	// +---------+

	/**
	 * Add another fraction to this fraction.
	 */
	public Fraction add(Fraction addend) throws Exception {
		if (addend.denominator.signum() == 0) {
			throw new Exception("Zero is an invalid denominator");
		}
		BigInteger resultNumerator;
		BigInteger resultDenominator;

		// The denominator of the result is the
		// product of this object's denominator
		// and addMe's denominator
		resultDenominator = this.denominator.multiply(addend.denominator);
		// The numerator is more complicated, it is the product
		// of the addend denominator and the original numerator
		// added to the product of the addend numerator and the
		// original denominator
		resultNumerator = (this.numerator.multiply(addend.denominator))
				.add(addend.numerator.multiply(this.denominator));

		// Return the computed value
		return new Fraction(resultNumerator, resultDenominator);
	} // add(Fraction)

	/**
	 * Add another int to this fraction
	 */
	public Fraction add(int addend) throws Exception {
		// Make the int into a BigInteger
		BigInteger toAdd = BigInteger.valueOf(addend);

		// Return a new fraction that has the int added to it
		// by multiplying it by the denominator and adding it
		// to the numerator
		return new Fraction(
				this.numerator.add(this.denominator.multiply(toAdd)),
				this.denominator);
	} // add(int)

	/**
	 * Subtract a fraction from this fraction
	 */
	public Fraction subtract(Fraction subtractor) throws Exception {
		// Make a negative version of the fraction to subtract
		if (subtractor.denominator.signum() == 0) {
			throw new Exception("Zero is an invalid denominator");
		}

		Fraction neg = new Fraction(
				subtractor.numerator.multiply(NEGATIVE_ONE),
				subtractor.denominator);
		return this.add(neg);
		// Add the negative version of subtractor to this fraction
	} // subtract(Fraction)

	/**
	 * Subtract an int from this fraction
	 * 
	 * @throws Exception
	 */
	public Fraction subtract(int subtractor) throws Exception {
		// Make the int negative and add it to the fraction
		int neg = subtractor * -1;
		return this.add(neg);
	} // subtract(int)

	/**
	 * Multiply this fraction by another fraction
	 */
	public Fraction multiply(Fraction multiplier) throws Exception {
		if (multiplier.denominator.signum() == 0) {
			throw new Exception("Zero is an invalid denominator");
		}
		BigInteger resultNumerator;
		BigInteger resultDenominator;

		// The numerator and denominator are equal to multiplying the
		// two numerators and denominators together respectively
		resultDenominator = this.denominator.multiply(multiplier.denominator);
		resultNumerator = this.numerator.multiply(multiplier.numerator);

		// Return the computed value
		return new Fraction(resultNumerator, resultDenominator);
	} // multiply(Fraction)

	/**
	 * Multiply this fraction by another integer
	 */
	public Fraction multiply(int multiplier) throws Exception {
		// Make multiplier into a BigInteger
		BigInteger toMultiply = BigInteger.valueOf(multiplier);

		// Return a new fraction that has been multiplied by multiplier
		// by multiplying the numerator by that number
		return new Fraction(this.numerator.multiply(toMultiply),
				this.denominator);
	} // multiply(int)

	/**
	 * Divide this fraction by another fraction
	 */
	public Fraction divide(Fraction divider) throws Exception {
		if (divider.denominator.signum() == 0) {
			throw new Exception("Zero is an invalid denominator");
		}
		// Make a new fraction that is the reciprocal of the divider
		Fraction recip = new Fraction(divider.denominator, divider.numerator);

		// Multiply this fraction by the reciprocal of divider to get
		// the same result as dividing by that fraction
		return this.multiply(recip);
	} // divide(Fraction)

	/**
	 * Divide this fraction by another integer
	 */
	public Fraction divide(int divider) throws Exception {
		if (divider == 0) {
			throw new Exception("Zero is an invalid divisor");
		}
		// Make divider into a BigInteger
		BigInteger toDivide = BigInteger.valueOf(divider);

		// Return the result of dividing this fraction by an integer
		// by multiplying the denominator by the divider
		return new Fraction(this.numerator, this.denominator.multiply(toDivide));
	} // divide(int)

	/**
	 * Raise a Fraction to a power
	 * 
	 * @throws Exception
	 */
	public Fraction expt(int power) throws Exception {
		// Raise this fraction to a power by raising both the numerator
		// and the denominator to that power respectively
		return new Fraction(this.numerator.pow(power),
				this.denominator.pow(power));
	} // power(int)

	/**
	 * Approximate this fraction as a double. Precondition: this.numerator and
	 * this.denominator must be small enough to be represented as doubles.
	 * @throws Exception 
	 */
	public double doubleValue() throws Exception {
		try {
			return this.numerator.doubleValue() / this.denominator.doubleValue();
		}
		catch (Exception e) {
			throw e;
		}
	} // doubleValue()

} // class Fraction

