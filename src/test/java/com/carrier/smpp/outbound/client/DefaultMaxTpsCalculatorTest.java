package com.carrier.smpp.outbound.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultMaxTpsCalculatorTest {
	@Test
	public void testTpsEqualsZero() {
		DefaultMaxTpsCalculator maxTps = new DefaultMaxTpsCalculator();
		int tps =0;
		assertEquals(1,maxTps.calculateTpsByBind(new BindTypes(1,0,0),tps));
	}
	
	@Test
	public void testTpsEqualsZeroAndTotalOfBindsEqualZero() {
		DefaultMaxTpsCalculator maxTps = new DefaultMaxTpsCalculator();
		int tps =0;
		assertEquals(1,maxTps.calculateTpsByBind(new BindTypes(0,0,0),tps));
	}
	
	@Test
	public void testTpsLesserThanTotalOfBinds() {
		DefaultMaxTpsCalculator maxTps = new DefaultMaxTpsCalculator();
		int tps =5;
		assertEquals(1,maxTps.calculateTpsByBind(new BindTypes(0,3,3),tps));
	}
	
	@Test
	public void testTpsGreaterThanTotalOfBinds() {
		DefaultMaxTpsCalculator maxTps = new DefaultMaxTpsCalculator();
		int tps =10;
		assertEquals(5,maxTps.calculateTpsByBind(new BindTypes(0,1,1),tps));
	}

}
