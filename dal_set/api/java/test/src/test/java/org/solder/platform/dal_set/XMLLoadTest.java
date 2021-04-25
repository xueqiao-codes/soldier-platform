package org.solder.platform.dal_set;

import java.io.FileInputStream;

import org.junit.Test;
import org.soldier.platform.dal_set.DalSetProxy;

public class XMLLoadTest {
	
	@Test
	public void testLoadSuccess() throws Exception {
		DalSetProxy.getInstance().loadFromXml(this.getClass().getResource("/dal_set2.xml").getFile());
	}
	
	@Test 
	public void testTestConfiguration() throws Exception {
		DalSetProxy.getInstance().testXmlConfiguration(new FileInputStream(
				this.getClass().getResource("/dal_set2.xml").getFile()));
	}
}
