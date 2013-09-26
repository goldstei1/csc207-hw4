package edu.grinnell.csc207.goldstei1.calc;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void testEvaluateString() throws Exception{
		assertEquals("lots of equals", true, Calculator.evaluate("r0 = r1 = r2 = r3 = 1/2").equals(new Fraction("1/2")));
		assertEquals("storage", true, Calculator.evaluate("r0").equals(new Fraction("1/2")));
		assertEquals("more storage", true, Calculator.evaluate("r1").equals(new Fraction("1/2")));
		assertEquals("adding storage elements", true, Calculator.evaluate("r0 + r1").equals(new Fraction("1/1")));
		assertEquals("multiply/divide", true, Calculator.evaluate("r3 = r1 * r3 - 5/8").equals(new Fraction("-3/8")));
		assertEquals("other storage", true, Calculator.evaluate("r3").equals(new Fraction("-3/8")));
		assertEquals("expt", true, Calculator.evaluate("r0 ^ 3").equals(new Fraction("1/8")));
		assertEquals("all together", true, Calculator.evaluate("r0 = r1 + 1/4 - 2/3 * 3/5 / r2 ^ 2").equals(new Fraction("1/100")));
		try {
			Calculator.evaluate("1 / iejsl + 1/5");
		}
		catch (Exception e) {
			assertEquals("incorrect input", "Malformed input: \"iejsl\"", e.getMessage());
		}
		
		try {
			Calculator.evaluate("r5");
		}
		catch (Exception e) {
			assertEquals("incorrect input", "Malformed input: r5 is not defined", e.getMessage());
		}
		
		try {
			Calculator.evaluate("1/0 / 2 + 1/5");
		}
		catch (Exception e) {
			assertEquals("incorrect input", "Malformed input: \"1/0\"", e.getMessage());
		}

	}

	@Test
	public void testEvaluateStringArray() throws Exception {
		assertArrayEquals(new Fraction[] {new Fraction("1/100"), new Fraction("1/2"), new Fraction("1/100")}, 
				new Fraction[] {Calculator.evaluate("r0 = r1 + 1/4 - 2/3 * 3/5 / r2 ^ 2"), Calculator.evaluate("1/2 + 0"),
				Calculator.evaluate("r0")});
		}
	}

