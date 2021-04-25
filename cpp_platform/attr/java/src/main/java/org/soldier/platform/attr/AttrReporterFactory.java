package org.soldier.platform.attr;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 完善接口，简化使用
 * 
 * @author wileywang
 *
 */
public class AttrReporterFactory {
	public static interface IAttrReporter {
		public int requireKey(String metric, Map<String, String> tags);
		
		public void inc(int key, long value);
		public void set(int key, long value);
		public void average(int key, long value);
		public void keep(int key, long value);
	}
	
	private static class AttrReporterImpl implements IAttrReporter {
		private ReporterType mType;
		
		public AttrReporterImpl(ReporterType type) {
			this.mType = type;
		}
		
		public int requireKey(String metric, Map<String, String> tags) {
			StringMap tagsStringMap = new StringMap();
			if (tags != null) {
				for (Entry<String, String> pair : tags.entrySet()) {
					tagsStringMap.set(pair.getKey(), pair.getValue());
				}
			}
			return AttrReporter.requireKey(mType, metric, tagsStringMap);
		}
		
		public void inc(int key, long value) {
			AttrReporter.inc(mType, key, value);
		}

		public void set(int key, long value) {
			AttrReporter.set(mType, key, value);
		}

		public void average(int key, long value) {
			AttrReporter.average(mType, key, value);
		}

		public void keep(int key, long value) {
			AttrReporter.keep(mType, key, value);
		}
	}
	
	private static class AttrReporterWin implements IAttrReporter {

		public int requireKey(String metric, Map<String, String> tags) {
			return -1;
		}

		public void inc(int key, long value) {
		}

		public void set(int key, long value) {
		}

		public void average(int key, long value) {
		}

		public void keep(int key, long value) {
		}
		
	}
	
	private static boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
			return true;
		}
		return false;
	}
	
	static {
		if (!isWindows()) {
			AttrReporterLibraryLoader.init();
		}
	}
	
	private static IAttrReporter s30SecsInstance;
	private static IAttrReporter s1MinInstance;
	
	public static IAttrReporter getDefault() {
		return minute();
	}
	
	public static IAttrReporter thirtySecs() {
		if (s30SecsInstance == null) {
			synchronized(AttrReporterFactory.class) {
				if (s30SecsInstance == null) {
					if (isWindows()) {
						s30SecsInstance = new AttrReporterWin();
					} else {
						s30SecsInstance = new AttrReporterImpl(ReporterType.REPORTER_TYPE_THIRTYSECONDS);
					}
				}
			}
		}
		return s30SecsInstance;
	}
	
	public static IAttrReporter minute() {
		if (s1MinInstance == null) {
			synchronized(AttrReporterFactory.class) {
				if (s1MinInstance == null) {
					if (isWindows()) {
						s1MinInstance = new AttrReporterWin();
					} else {
						s1MinInstance = new AttrReporterImpl(ReporterType.REPORTER_TYPE_MINUTE);
					}
				}
			}
		}
		return s1MinInstance;
	}
	
}
