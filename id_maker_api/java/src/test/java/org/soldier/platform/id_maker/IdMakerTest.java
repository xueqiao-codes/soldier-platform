package org.soldier.platform.id_maker;

import junit.framework.Assert;

public class IdMakerTest {
	
//	@Test
	public void testGetIdMaker() {
		IdMaker maker = IdMakerFactory.getInstance().getIdMaker(1);
		Assert.assertNotNull(maker);
	}
	
//	@Test
	public void testGetIdMakerNull() {
		IdMaker maker = IdMakerFactory.getInstance().getIdMaker(1000);
		Assert.assertNull(maker);
	}
	
//	@Test
	public void testCreateId() throws IdException {
		IdMaker maker = IdMakerFactory.getInstance().getIdMaker(1);
		Assert.assertTrue(0 != maker.createId());
	}
}
