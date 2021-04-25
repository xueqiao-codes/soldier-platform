package org.soldier.platform.admin.data;

import java.util.regex.Pattern;

public class DomainValidator {
	private static final String domainPatternExpr = "([a-zA-Z0-9_\\-]+\\.)+([a-zA-Z])+";
	private static final Pattern domainPattern = Pattern.compile(domainPatternExpr);
	
	public static String getDomainPatterExpr() {
		return domainPatternExpr;
	}
	
	public static boolean isDomainValid(String domain) {
		return domainPattern.matcher(domain).matches();
	}
}
