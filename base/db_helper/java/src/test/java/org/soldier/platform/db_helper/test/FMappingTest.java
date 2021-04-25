package org.soldier.platform.db_helper.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.soldier.platform.db_helper.protocols.FMapping;

public class FMappingTest {
	@Test
	public void testMappingClassName() {
		FMapping mapping = new FMapping();
		
		assertNull(mapping.getClassFieldName(null));
		assertEquals("", mapping.getClassFieldName(""));
		assertEquals("hehe", mapping.getClassFieldName("hehe"));
		assertEquals("F", mapping.getClassFieldName("F"));
		assertEquals("userId", mapping.getClassFieldName("Fuser_id"));
		assertEquals("groupOwnerId", mapping.getClassFieldName("Fgroup_owner_id"));
	}
	
	@Test
	public void testMappingDbName() {
		FMapping mapping = new FMapping();
		
		assertNull(mapping.getDbFieldName(null));
		assertEquals("", mapping.getDbFieldName(""));
		assertEquals("Fhehe", mapping.getDbFieldName("hehe"));
		assertEquals("F_f", mapping.getDbFieldName("F"));
		assertEquals("Fuser_id", mapping.getDbFieldName("userId"));
		assertEquals("Fgroup_owner_id", mapping.getDbFieldName("groupOwnerId"));
	}
}
