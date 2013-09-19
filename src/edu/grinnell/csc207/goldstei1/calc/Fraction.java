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
	public Fraction(BigInteger numerator, BigInteger denominator) throws Exception {
		if (this.denominator.signum() == 0) {
			throw new Exception("Zero is an invalid denominator");
		}
		this.numerator = numerator;
		this.denominator = denominator;
		this.cleanup();
	} // Fraction(BigInteger, BigInteger)

	public Fraction(int numerator, int denominator) {
		this.numerator = BigInteger.valueOf(numerator);
		this.denominator = BigInteger.valueOf(denominator);
		this.cleanup();
	} // Fraction(int, int)

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
			return (this.numerator == fracObj.numerator &&
					this.denominator == fracObj.denominator);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns the hashcode of this fraction
	 */
	public int hashCode() {
		return 0;
	}
	
	/**
	 * Clones this fraction
	 * @throws Exception
	 */
	public Fraction clone() {
		try {
			return new Fraction(this.numerator, this.denominator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Takes a fraction and returns 0 if they are equal,
	 * 1 if this fraction is bigger than comparer, and -1
	 * if it is less than comparer
	 */
	public int compareTo(Fraction comparer) {
		if (this.equals(comparer)) {
			return 0;
		} // if
		else if (this.doubleValue() > comparer.doubleValue()) {
			return 1;
		} // else if
		else {
			return -1;
		} // else
	}

	// +-----------------+----------------------------------------------
	// | Private Methods |
	// +-----------------+

	/**
	 * Cleanup the fraction by eliminating any negative sign from
	 * the denominator and moving it to the numerator. Also calls
	 * simplify so that the fraction is represented in its simplified
	 * form.
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
		this.numerator.divide(gcd);
		this.denominator.divide(gcd);
	} // simplify()

	// +---------+------------------------------------------------------
	// | Methods |
	// +---------+

	/**
	 * Add another fraction to this fraction.
	 * @throws Exception 
	 */
	public Fraction add(Fraction addend) throws Exception {
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
	 * @throws Exception
	 */
	public Fraction add(int addend) throws Exception {
		// Make the int into a BigInteger
		BigInteger toAdd = BigInteger.valueOf(addend);
		
		// Return a new fraction that has the int added to it
		// by multiplying it by the denominator and adding it 
		// to the numerator
		return new Fraction(this.numerator
				.add(this.denominator.multiply(toAdd)), this.denominator);
	} // add(int)
	
	/**
	 * Subtract a fraction from this fraction
	 * @throws Exception
	 */
	public Fraction subtract(Fraction subtractor) throws Exception {
		// Make a negative version of the fraction to subtract
		Fraction neg = new Fraction(subtractor.numerator.multiply(NEGATIVE_ONE),
				subtractor.denominator);
		
		// Add the negative version of subtractor to this fraction
		return this.add(neg);
	} // subtract(Fraction)
	
	/**
	 * Subtract an int from this fraction
	 * @throws Exception
	 */
	public Fraction subtract(int subtractor) throws Exception {
		// Make the int negative and add it to the fraction
		int neg = subtractor * -1;
		return this.add(neg);
	} // subtract(int)
	
	/**
	 * Multiply this fraction by another fraction
	 * @throws Exception 
	 */
	public Fraction multiply(Fraction multiplier) throws Exception {
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
	 * @throws Exception
	 */
	public Fraction multiply(int multiplier) throws Exception {
		// Make multiplier into a BigInteger
		BigInteger toMultiply = BigInteger.valueOf(multiplier);
		
		// Return a new fraction that has been multiplied by multiplier
		// by multiplying the numerator by that number
		return new Fraction(this.numerator.multiply(toMultiply), this.denominator);
	} // multiply(int)
	
	/**
	 * Divide this fraction by another fraction
	 * @throws Exception
	 */
	public Fraction divide(Fraction divider) throws Exception {
		//Make a new fraction that is the reciprocal of the divider
		Fraction recip = new Fraction(divider.denominator, divider.numerator);
		
		// Multiply this fraction by the reciprocal of divider to get
		// the same result as dividing by that fraction
		return this.multiply(recip);
	} // divide(Fraction)
	
	/**
	 * Divide this fraction by another integer
	 * @throws Exception
	 */
	public Fraction divide(int divider) throws Exception {
		// Make divider into a BigInteger
		BigInteger toDivide = BigInteger.valueOf(divider);
		
		// Return the result of dividing this fraction by an integer
		// by multiplying the denominator by the divider
		return new Fraction(this.numerator, this.denominator.multiply(toDivide));
	} // divide(int)
	
	/**
	 * Raise a Fraction to a power
	 * @throws Exception
	 */
	public Fraction Expt(int power) throws Exception {
		// Raise this fraction to a power by raising both the numerator
		// and the denominator to that power respectively
		return new Fraction(this.numerator.pow(power), this.denominator.pow(power));
	} // power(int)

	/**
	 * Approximate this fraction as a double.
	 * Precondition: this.numerator and this.denominator must be small enough to be
	 * 				 represented as doubles.
	 */
	public double doubleValue() {
		return this.numerator.doubleValue() / this.denominator.doubleValue();
	} // doubleValue()

} // class Fraction

