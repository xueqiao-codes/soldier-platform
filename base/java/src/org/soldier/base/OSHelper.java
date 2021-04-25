package org.soldier.base;

public class OSHelper {
	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
			return true;
		}
		return false;
	}
	
	public static int getMachineCpuBit() {
		return Integer.valueOf(System.getProperty("sun.arch.data.model"));
	}
	
	public static boolean isMac() {
		String os = System.getProperty("os.name");
		if (os != null && (os.startsWith("Mac") || os.startsWith("mac"))) {
			return true;
		}
		return false;
	}
	
//	public static void main(String[] args) {
//		System.out.println(OSHelper.isMac());
//		System.out.println(OSHelper.isWindows());
//	}
}
