package test;

import org.junit.Test;

import egovframework.com.cmm.service.EgovProperties;

public class profileTest {

	@Test
	public void profileTest() {
		System.out.println(EgovProperties.getProfile());
		System.out.println(EgovProperties.getProperty("Globals.Url"));
	}

}
