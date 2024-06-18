package com.thungashoe.util;

public class ArrayJsonConverter {

		public static String converToString(String s) {
			
			String trimmedJsonString = s.substring(11, s.length() - 3);
//		    System.out.println("Trimmed JSON string: " + trimmedJsonString);
		    return trimmedJsonString;
		}
}
