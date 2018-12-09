package main.com.ts.common;

public class ChangeUtils {

	public static String[] stringToArray(String str) {
		if ("".equals(str) || str == null) {
			return new String[0];
		}
		return str.split("");
	}
}
