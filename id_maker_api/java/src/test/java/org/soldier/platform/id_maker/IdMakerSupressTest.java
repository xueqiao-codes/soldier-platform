package org.soldier.platform.id_maker;

import java.util.HashMap;

import org.soldier.base.logger.AppLog;

public class IdMakerSupressTest {
	public static void main(String[] args) {
		IdMaker maker = IdMakerFactory.getInstance().getIdMaker(3);
		
		HashMap<Long, Boolean> map = new HashMap<Long, Boolean>(100000);
		
		AppLog.init("id_maker_supress_test", "E:");
		long totalTime = 0;
		int totalCount = 100000;
		for (int index = 0; index < totalCount; ++index) {
			try {
				long beginTime = System.currentTimeMillis();
				long id = maker.createId();
				totalTime += (System.currentTimeMillis() - beginTime);
				
				if (map.containsKey(id)) {
					System.err.println("ID Same，, id=" + id);
				}
				map.put(id, true);
				
			} catch (IdException e) {
				e.printStackTrace();
			}
		}
		System.out.println("totalTime=" + totalTime + ", totalCount=" + totalCount);
		System.out.println("time=" + (totalTime /totalCount));
	}
}
