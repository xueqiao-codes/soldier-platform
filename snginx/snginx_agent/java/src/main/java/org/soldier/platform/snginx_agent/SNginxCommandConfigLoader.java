package org.soldier.platform.snginx_agent;

public class SNginxCommandConfigLoader implements INginxConfigLoader {
	private String snginxPath;
	
	public SNginxCommandConfigLoader(String snginxPath) {
		this.snginxPath = snginxPath;
	}
	
	@Override
	public void load() throws Exception {
		configTest();
		reload();
	}
	
	private void configTest() throws Exception {
		ProcessBuilder builder = new ProcessBuilder(snginxPath, "configtest");
		
		Process process = builder.start();
		int result = process.waitFor();
		if (result != 0) {
			throw new Exception("configTest failed, result =" + result);
		}
	}
	
	private void reload() throws Exception {
		ProcessBuilder builder = new ProcessBuilder(snginxPath, "reload");
		Process process = builder.start();
		int result = process.waitFor();
		if (result != 0) {
			throw new Exception("reload failed, result=" + result);
		}
	}
}
