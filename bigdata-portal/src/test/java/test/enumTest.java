package test;

import org.junit.Test;

import bigdata.portal.enums.RetnWghtValEnum;

public class enumTest {

	@Test
	public void test() {
		System.out.println(RetnWghtValEnum.getWghtVal("rtrnFrmhsSportPolicyCoScore", 1));
	}
}
