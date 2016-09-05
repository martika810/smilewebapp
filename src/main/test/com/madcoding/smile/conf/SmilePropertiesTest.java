package com.madcoding.smile.conf;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmilePropertiesTest {

	@Test
	public void initTest() {
		String name =SmileProperties.getInstance().getProperty("cloudinary_name");
		assertTrue(name.equals("dwckrucnb"));
	}

}
