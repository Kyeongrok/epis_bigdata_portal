package kr.co.ucsit.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CsDateUtilTest {
	
	@Test
	public void testGetYyyy() {
		assertEquals(CsDateUtil.getYyyy(), "2020");
	}

}
