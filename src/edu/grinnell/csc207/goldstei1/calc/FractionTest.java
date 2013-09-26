package edu.grinnell.csc207.goldstei1.calc;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class FractionTest {

	// Doing these tests also show that compareTo, simplify, and cleanup work correctly
	@Test
	public void testFractionBigIntegerBigInteger() throws Exception {
		try {
		assertEquals("Denom = 0", "Zero is an invalid denominator", new Fraction(new BigInteger("1"), new BigInteger("0")));
		}
		catch (Exception e) {
			assertEquals("denom = 0", "Zero is an invalid denominator", e.getMessage());
		}
		assertEquals("two equivalent big int fractions", 0, new Fraction(BigInteger.ONE, new BigInteger("2")).
				compareTo(new Fraction(new BigInteger("2"), new BigInteger("4"))));
		assertEquals("negative equivalent fractions", 0, new Fraction(BigInteger.ONE, new BigInteger("3")).
				compareTo(new Fraction(new BigInteger("-2"), new BigInteger("-6"))));
	}

	@Test
	public void testFractionIntInt() throws Exception{
		try {
			assertEquals("Denom = 0", "Zero is an invalid denominator", new Fraction(5, 0));
			}
			catch (Exception e) {
				assertEquals("denom = 0", "Zero is an invalid denominator", e.getMessage());
			}
		assertEquals("two equivalent int fractions", 0, new Fraction(3, 8).compareTo(new Fraction(9, 24)));
		assertEquals("negative equivalent fractions", 0, new Fraction(5, 10).compareTo(new Fraction(-1, -2)));
		assertEquals("fractions not equal", 1, new Fraction(1, 2).compareTo(new Fraction(1, 3)));

	}

	@Test
	public void testFractionInt() throws Exception{
		assertEquals("two equal fractions", 0, new Fraction(7).compareTo(new Fraction(7, 1)));
		assertEquals("two non-equal fractions", 1, new Fraction(21).compareTo(new Fraction(21, 2)));
	}

	@Test
	public void testFractionString() throws Exception{
		assertEquals("equal fractions", 0, new Fraction("1/2").compareTo(new Fraction(1, 2)));
		assertEquals("negative fraction", 0, new Fraction("-1/-2").compareTo(new Fraction(1, 2)));
		assertEquals("two negatives", 0, new Fraction("-1/2").compareTo(new Fraction(-1, 2)));
	}

	@Test
	public void testToString() throws Exception{
		assertEquals("fraction 1", "1/2", new Fraction(1, 2).toString());
		assertEquals("fraction 1", "-2/5", new Fraction(2, -5).toString());
	}

	@Test
	public void testEqualsObject() throws Exception{
		assertEquals("equivalent", true, new Fraction("1/2").equals(new Fraction("2/4")));
		assertEquals("not a fraction", false, new Fraction("2/5").equals("hello"));
	}

	@Test
	public void testClone() throws Exception{
		assertEquals("should be equal", true, new Fraction("1/2").clone().equals(new Fraction(1, 2)));
	}

	@Test
	public void testAddFraction() throws Exception {
		assertEquals("adding same denom", true, new Fraction("2/3").add(new Fraction("1/3")).equals(new Fraction("1/1")));
		assertEquals("adding different denom", true, new Fraction("1/8").add(new Fraction("1/4")).equals(new Fraction("3/8")));
		assertEquals("adding negatives", true, new Fraction("1/3").add(new Fraction("-2/3")).equals(new Fraction("-1/3")));
	}

	@Test
	public void testAddInt() throws Exception {
		assertEquals("adding an int", true, new Fraction("7/3").add(7).equals(new Fraction("28/3")));
		assertEquals("adding a negative", true, new Fraction("9/3").add(-3).equals(new Fraction("0/1")));
		assertEquals("add zero", true, new Fraction("11/7").add(0).equals(new Fraction("11/7")));
	}

	@Test
	public void testSubtractFraction() throws Exception {
		assertEquals("subtract fraction", true, new Fraction("7/6").subtract(new Fraction("5/3")).equals(new Fraction("-1/2")));
		assertEquals("subtract a negative", true, new Fraction("11/3").subtract(new Fraction("8/7")).equals(new Fraction("53/21")));
		assertEquals("subtract zero", true, new Fraction("11/7").subtract(new Fraction("0")).equals(new Fraction("11/7")));
	}

	@Test
	public void testSubtractInt() throws Exception {
		assertEquals("subtract an int", true, new Fraction("7/6").subtract(7).equals(new Fraction("-35/6")));
		assertEquals("subtract a negative", true, new Fraction("12/3").subtract(-3).equals(new Fraction("7/1")));
		assertEquals("subtract zero", true, new Fraction("11/7").subtract(0).equals(new Fraction("11/7")));
	}

	@Test
	public void testMultiplyFraction() throws Exception {
		assertEquals("multiply fraction", true, new Fraction("10/3").multiply(new Fraction("7/8")).equals(new Fraction("35/12")));
		assertEquals("multiply a negative", true, new Fraction("7/9").multiply(new Fraction("-3/4")).equals(new Fraction("-7/12")));
		assertEquals("multiply by zero", true, new Fraction("11/7").multiply(new Fraction("0")).equals(new Fraction("0/1")));
	}

	@Test
	public void testMultiplyInt() throws Exception {
		assertEquals("multiply an int", true, new Fraction("7/6").multiply(7).equals(new Fraction("49/6")));
		assertEquals("multiply a negative", true, new Fraction("12/3").multiply(-3).equals(new Fraction("-12/1")));
		assertEquals("multiply zero", true, new Fraction("11/7").multiply(0).equals(new Fraction("0/1")));
	}

	@Test
	public void testDivideFraction() throws Exception {
		assertEquals("divide fraction", true, new Fraction("2/3").divide(new Fraction("1/3")).equals(new Fraction("2/1")));
		assertEquals("divide neg fraction", true, new Fraction("1/8").divide(new Fraction("-1/4")).equals(new Fraction("-1/2")));
		try {
			new Fraction("1/3").divide(new Fraction("-2/3"));
		}
		catch (Exception e) {
			assertEquals("divide zero", "Zero is an invalid denominator", e.getMessage());
		}
	}

	@Test
	public void testDivideInt() throws Exception {
		assertEquals("divide an int", true, new Fraction("7/6").divide(7).equals(new Fraction("1/6")));
		assertEquals("divide a negative", true, new Fraction("12/3").divide(-3).equals(new Fraction("-4/3")));
		try {
			new Fraction("11/7").divide(0);
		}
		catch (Exception e) {
			assertEquals("divide zero", "Zero is an invalid divisor", e.getMessage());
		}
	}

	@Test
	public void testExpt() throws Exception {
		assertEquals("expt int", true, new Fraction("7/6").expt(2).equals(new Fraction("49/36")));
		assertEquals("expt 2 int", true, new Fraction("2/3").expt(3).equals(new Fraction("8/27")));
		assertEquals("expt 0", true, new Fraction("2/3").expt(0).equals(new Fraction("1/1")));
		try {
			new Fraction("2/3").expt(-2);
		}
		catch (Exception e) {
			assertEquals("neg expt", "Negative exponent", e.getMessage());
		}
	}

	@Test
	public void testDoubleValue() throws Exception {
		assertEquals("expt int", 0.8, new Fraction("4/5").doubleValue(), .01);
		assertEquals("expt 2 int", 0.666, new Fraction("2/3").doubleValue(), .01);
		assertEquals("expt 0", 1, new Fraction("5/5").doubleValue(), .01);
	}

}
