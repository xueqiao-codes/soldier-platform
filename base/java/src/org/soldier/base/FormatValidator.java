package org.soldier.base;

import java.util.regex.Pattern;

public class FormatValidator {
	private static Pattern emailPattern;
	private static Pattern getEmailPattern() {
		if (emailPattern == null) {
			synchronized(FormatValidator.class) {
				if (emailPattern == null) {
					emailPattern = Pattern.compile(
							"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
				}
			}
		}
		return emailPattern;
	}
	
	public static boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		return getEmailPattern().matcher(email).matches();

	}
}
