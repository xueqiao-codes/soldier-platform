
import org.soldier.platform.attr.*;

public class TestMain {
	public static void main(String[] args) {
		
		AttrReporterLibraryLoader.init();
		
		StringMap tags = new StringMap();
		tags.set("tag1", "value1");
		tags.set("tag2", "value2");
		
		int key = AttrReporter.requireKey(ReporterType.REPORTER_TYPE_MINUTE, "java.test", tags);
		int totalCount = 1000000;
		long start =  System.currentTimeMillis();
		for (int i = 0; i < totalCount; ++i) {
			AttrReporter.inc(ReporterType.REPORTER_TYPE_MINUTE, key, 1);
		}
		System.out.println("inc operation, every operation escaped=" + (System.currentTimeMillis()-start)*1000000/totalCount + "ns");
		
		start =  System.currentTimeMillis();
		for (int i = 0; i < totalCount; ++i) {
			key = AttrReporter.requireKey(ReporterType.REPORTER_TYPE_MINUTE, "java.test", tags);
			AttrReporter.inc(ReporterType.REPORTER_TYPE_MINUTE, key, 1);
		}
		System.out.println("inc with key operation, every operation escaped=" + (System.currentTimeMillis()-start)*1000000/totalCount + "ns");
	}
}