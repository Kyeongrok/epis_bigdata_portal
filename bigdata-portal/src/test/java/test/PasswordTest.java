package test;

import org.junit.Test;

import egovframework.com.utl.sim.service.EgovFileScrty;

public class PasswordTest {


	@Test
	public void test() throws Exception {
		System.out.println(EgovFileScrty.encryptPassword("123456789", "whddbs311"));

	}
}
