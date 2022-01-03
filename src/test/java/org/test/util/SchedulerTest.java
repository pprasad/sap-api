package org.test.util;

import org.sap.api.util.SAPUtilConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerTest {
    
	private static final Logger LOGGER=LoggerFactory.getLogger(SchedulerTest.class);
	
	public void isEmptyTest() {
		String s=null;
		boolean flag=SAPUtilConstant.isEmpty(s);
	}
}
