package com.pdf.api.utils;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lang {

	public final static String EMPTY        = "";
	public final static String SPACE        = " ";
	public final static String SLASH 		= "/";
	public final static String COMMA        = ",";
	public final static String QMARK 		= "?";
	public final static String AMPERSAND 	= "&";
	public final static String EQUALS 		= "=";
	public final static String L_PARENTH 	= "(";
	public final static String R_PARENTH 	= ")";
	public final static String PLUS         = "+";
	public final static String DOT 			= ".";
	public final static String DOT_ESCAPED 	= "\\.";
	public final static String SPACE_COMMA  = ", ";
	public final static String DASH 		= "-";
	public final static String UNDERSCORE 	= "_";
	public final static String LINE 		= "\n";

	public static boolean isNullOrEmpty (String text) {
		return text == null || text.trim ().isEmpty ();
	}
	
	public static boolean isNullOrEmpty (Object [] array) {
		return array == null || array.length == 0;
	}
	
	public static boolean isNullOrEmpty (List<?> list) {
		return list == null || list.isEmpty ();
	}
	
	public static boolean isNumeric (String text) {
		try {
			@SuppressWarnings ("unused")
			double d = Double.parseDouble (text);
		}  catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isEmailFormatted (String text) {
		Pattern p = Pattern.compile ("[a-zA-Z0-9][a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{0,255}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
		return p.matcher (text).matches ();
	}

	public static boolean isNameFormatted (String text) {
		//Pattern pattern = Pattern.compile ("^[a-zàâäéêèëîïôöùûüç][a-z0-9'. -àâäéêèëîïôöùûüç]*", Pattern.CASE_INSENSITIVE);
		Pattern pattern = Pattern.compile ("^[a-zàâéêèîôùûç][a-z'. àâéêèîôùûç-]*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher (text);
		return matcher.matches();
	}
	
	public static boolean containsSpecialCharacter (String text) {
		Pattern pattern = Pattern.compile ("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher (text);
		return matcher.find ();
	}
	
	public static boolean containsDigit (String text) {
		Pattern pattern = Pattern.compile ("[0-9]+");
		Matcher matcher = pattern.matcher (text);
		return matcher.find ();
	}
	
	public static boolean containsUpperCaseLetter (String text) {
		return !text.equals (text.toLowerCase (Locale.getDefault ()));
	}
	
	public static boolean containsLowerCaseLetter (String text) {
		return !text.equals (text.toUpperCase (Locale.getDefault ()));
	}
	
	public static boolean isGreaterThanMinLength (String text, int min) {
		return text.length () >= min;
	}
	
	public static boolean isLowerThanMaxLength (String text, int max) {
		return text.length () <= max;
	}
}