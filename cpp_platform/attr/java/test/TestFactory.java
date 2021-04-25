
import org.soldier.platform.attr.*;
import java.util.HashMap;

public class TestFactory {
	public static void main(String[] args) {
		int key1 = AttrReporterFactory.getDefault().requireKey("test.factory", null);
		int key2 = AttrReporterFactory.thirtySecs().requireKey("test.factory", new HashMap<String, String>(){
			{
				put("tag1", "value1");
			}
		});
		
		System.out.println("key1=" + key1 + ",key2=" + key2);
		AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey("test.keepalive", null), 1);
		
		long start =  System.currentTimeMillis();
		long totalCount = 10000000;
		for (long i = 0; i < totalCount; ++i) {
			AttrReporterFactory.getDefault().inc(key1, 1);
			AttrReporterFactory.thirtySecs().average(key2, i);
		}
		System.out.println("inc operation, every operation escaped=" + (System.currentTimeMillis()-start)*1000000/totalCount + "ns");
	}
}