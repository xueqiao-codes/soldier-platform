package org.soldier.platform.id_maker;


import org.junit.Assert;
import org.junit.Test;

public class IdListTest {

	@Test
	public void testAddZoneSize() throws Exception{
		IdList list = new IdList();
		list.addZone(10, 20);
		Assert.assertEquals(11, list.size());
		
		list.addZone(21, 22);
		Assert.assertEquals(13, list.size());
		
		list.addZone(50, 100);
		Assert.assertEquals(64, list.size());
	}
	
	@Test(expected=IdException.class)
	public void testAddZoneParamException() throws IdException {
		IdList list = new IdList();
		list.addZone(20, 10);
	}
	
	@Test(expected=IdException.class) 
	public void testAddZoneUnboundedException1() throws IdException {
		IdList list = new IdList();
		list.addZone(10, 15);
		list.addZone(13, 20);
	}
	
	@Test(expected=IdException.class) 
	public void testAddZoneUnboundedException2() throws IdException {
		IdList list = new IdList();
		list.addZone(10, 15);
		list.addZone(9, 20);
	}
	
	@Test(expected=IdException.class) 
	public void testAddZoneUnboundedException3() throws IdException {
		IdList list = new IdList();
		list.addZone(10, 15);
		list.addZone(11, 13);
	}
	
	@Test
	public void testPopId() throws IdException {
		IdList list = new IdList();
		list.addZone(1, 1);
		list.addZone(3, 10);
		
		Assert.assertEquals(1l, list.popId());
		Assert.assertEquals(3l, list.popId());
		Assert.assertEquals(4l, list.popId());
		
		Assert.assertEquals(6, list.size());
	}
	
	@Test
	public void testPodIds() throws IdException {
		IdList list = new IdList();
		list.addZone(1, 1);
		list.addZone(2, 9);
		list.addZone(500, 2000);
		int oldSize = list.size();
		
		IdList resultList = list.popIds(10);
		Assert.assertNotNull(resultList);
		Assert.assertEquals(10, resultList.size());
		Assert.assertEquals(3, resultList.getZones().size());
		
		long[] resultArray = new long[]{1,2, 3,4,5,6,7,8,9,500};
		for (int index = 0; index < resultArray.length; ++index) {
			Assert.assertEquals(resultArray[index], resultList.popId());
		}
		Assert.assertEquals(0, resultList.size());
		
		Assert.assertEquals(1, list.getZones().size());
		Assert.assertEquals(oldSize-10, list.size());
		Assert.assertEquals(501l, list.getZones().get(0).getBegin());
	}
	
	@Test
	public void testIdListCenterInsert() throws IdException {
		IdList list = new IdList();
		list.addZone(100, 199);
		list.addZone(10, 39);
		
		Assert.assertEquals(2, list.getZones().size());
		Assert.assertEquals(130, list.size());
		
		list.addZone(50, 79);
		Assert.assertEquals(160, list.size());
	}
	
	@Test(expected=IdException.class)
	public void testIdCenterInsertExceptException() throws IdException {
		IdList list = new IdList();
		list.addZone(100, 199);
		list.addZone(10, 39);
		list.addZone(50, 79);
		
		list.addZone(29, 60);
	}

}
